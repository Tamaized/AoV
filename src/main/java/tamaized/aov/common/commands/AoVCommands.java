package tamaized.aov.common.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import tamaized.aov.AoV;
import tamaized.aov.common.blocks.BlockAngelicBlock;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.AoVCapabilityHandler;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.common.gui.GuiHandler;

public final class AoVCommands {

	private AoVCommands() {

	}

	public static class Open {
		public static ArgumentBuilder<CommandSource, ?> register() {
			return Commands.literal("open").
					requires(cs -> cs.hasPermissionLevel(2)).
					executes(context -> {
						GuiHandler.openGui(GuiHandler.GUI.SKILLS, BlockAngelicBlock.ClassType.ALL, context.getSource().asPlayer());
						return 0;
					});
		}
	}

	public static class SetLevel {
		public static ArgumentBuilder<CommandSource, ?> register() {
			return Commands.literal("setlevel").
					requires(cs -> cs.hasPermissionLevel(2)).
					then(Commands.argument("level", IntegerArgumentType.integer(0, AoV.config.maxlevel.get())).
							then(Commands.argument("player", EntityArgument.player()).
									executes(context -> run(context.getArgument("player", EntitySelector.class).selectOnePlayer(context.getSource()), context.getArgument("level", int.class)))).
							executes(context -> run(context.getSource().asPlayer(), context.getArgument("level", int.class))));
		}

		private static int run(PlayerEntity target, int level) {
			IAoVCapability cap = CapabilityList.getCap(target, CapabilityList.AOV, null);
			if (cap != null) {
				cap.reset(true);
				cap.setExp(AoVCapabilityHandler.getExpForLevel(level));
			}
			IPolymorphCapability poly = CapabilityList.getCap(target, CapabilityList.POLYMORPH, null);
			if (poly != null)
				poly.morph(null);
			return 0;
		}
	}

	public static class Reset {
		public static ArgumentBuilder<CommandSource, ?> register() {
			return Commands.literal("reset").
					requires(cs -> cs.hasPermissionLevel(2)).
					then(Commands.literal("full").
							then(Commands.argument("player", EntityArgument.player()).
									executes(context -> run(context.getArgument("player", EntitySelector.class).selectOnePlayer(context.getSource()), Type.FULL))).
							executes(context -> run(context.getSource().asPlayer(), Type.FULL))).
					then(Commands.literal("minor").
							then(Commands.argument("player", EntityArgument.player()).
									executes(context -> run(context.getArgument("player", EntitySelector.class).selectOnePlayer(context.getSource()), Type.MINOR))).
							executes(context -> run(context.getSource().asPlayer(), Type.MINOR))).
					then(Commands.literal("cooldown").
							then(Commands.argument("player", EntityArgument.player()).
									executes(context -> run(context.getArgument("player", EntitySelector.class).selectOnePlayer(context.getSource()), Type.COOLDOWN))).
							executes(context -> run(context.getSource().asPlayer(), Type.COOLDOWN)));

		}

		private static int run(PlayerEntity target, Type type) {
			IAoVCapability cap = CapabilityList.getCap(target, CapabilityList.AOV, null);
			if (cap != null) {
				switch (type) {
					case FULL:
					case MINOR:
						cap.reset(type == Type.FULL);
						IPolymorphCapability poly = CapabilityList.getCap(target, CapabilityList.POLYMORPH);
						if (poly != null)
							poly.morph(null);
						break;
					case COOLDOWN:
						cap.resetCharges(target);
						break;
				}
			}
			return 0;
		}

		private enum Type {
			FULL, MINOR, COOLDOWN
		}
	}

	public static class Debug {
		public static ArgumentBuilder<CommandSource, ?> register() {
			return Commands.literal("debug").
					requires(cs -> cs.hasPermissionLevel(2)).
					then(Commands.literal("stun").
							then(Commands.argument("length", IntegerArgumentType.integer(0)).
									then(Commands.argument("target", EntityArgument.entity()).
											executes(context -> run(context.getArgument("target", EntitySelector.class).selectOne(context.getSource()), context.getArgument("length", int.class)))).
									executes(context -> run(context.getSource().asPlayer(), context.getArgument("length", int.class)))));

		}

		private static int run(Entity target, int len) {
			IStunCapability cap = CapabilityList.getCap(target, CapabilityList.STUN);
			if (cap != null)
				cap.setStunTicks(len);
			return 0;
		}
	}

}
