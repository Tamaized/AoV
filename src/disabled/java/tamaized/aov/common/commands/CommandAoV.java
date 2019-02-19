package tamaized.aov.common.commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.blocks.BlockAngelicBlock;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.AoVCapabilityHandler;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.aov.common.gui.GuiHandler;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class CommandAoV extends CommandBase {

	private static IAoVCapability getCap(Entity e) {
		return CapabilityList.getCap(e, CapabilityList.AOV);
	}

	@Nonnull
	@Override
	public String getName() {
		return "aov";
	}

	@Nonnull
	@Override
	public String getUsage(@Nonnull ICommandSender sender) {
		return "commands.aov.usage";
	}

	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
		if (args.length > 0) {
			try {
				SubCommand.valueOf(args[0].toUpperCase(Locale.ROOT)).execute(this, server, sender, args);
			} catch (IllegalArgumentException e) {
				throw new WrongUsageException(getUsage(sender));
			}
		} else {
			throw new CommandException(getUsage(sender));
		}
	}

	@Nonnull
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		if (args.length == 1)
			return getListOfStringsMatchingLastWord(args, SubCommand.COMMAND_LIST);

		if (args.length > 1) {
			try {
				String[] argsMoved = Arrays.copyOfRange(args, 1, args.length);

				return SubCommand.valueOf(args[0].toUpperCase(Locale.ROOT)).tabCompletion(server, sender, argsMoved, targetPos);
			} catch (Throwable e) {
				return Collections.emptyList();
			}
		}

		return Collections.emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		try {
			return SubCommand.valueOf(args[0].toUpperCase(Locale.ROOT)).isUsernameIndex(args, index);
		} catch (Throwable t) {
			return false;
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	private enum SubCommand {
		OPEN {
			@Override
			protected void execute(ICommand command, MinecraftServer server, ICommandSender sender, String[] args) {
				if (sender.getCommandSenderEntity() instanceof EntityPlayer)
					GuiHandler.openGUI(GuiHandler.GUI.SKILLS, BlockAngelicBlock.ClassType.ALL, (EntityPlayer) sender.getCommandSenderEntity(), sender.getEntityWorld());
			}
		}, SETLEVEL {
			@Override
			protected void execute(ICommand command, MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
				if (args.length > 1) {
					int level;
					try {
						level = MathHelper.clamp(Integer.parseInt(args[1]), 0, ConfigHandler.maxlevel);
					} catch (NumberFormatException e) {
						throw new WrongUsageException("commands.aov.error.level", ConfigHandler.maxlevel);
					}
					IAoVCapability cap;
					Entity e;
					if (args.length > 2)
						cap = getCap(e = getEntity(server, sender, args[2]));
					else
						cap = getCap(e = sender.getCommandSenderEntity());
					if (cap != null) {
						cap.reset(true);
						cap.setExp(AoVCapabilityHandler.getExpForLevel(level));
						sender.sendMessage(new TextComponentTranslation("commands.aov.success.level", e.getName(), level));
					}
					return;
				}
				throw new WrongUsageException("commands.aov.usage.setlevel");
			}

			@Override
			protected List<String> tabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
				return args.length == 2 ? getPlayersForTabComplete(server) : super.tabCompletion(server, sender, args, targetPos);
			}

			@Override
			protected boolean isUsernameIndex(String[] args, int index) {
				return index == 2;
			}
		}, RESET {
			private final String[] TAB_LIST = {"minor", "full", "cooldown"};

			@Override
			protected void execute(ICommand command, MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
				Entity entity = args.length > 2 ? getEntity(server, sender, args[2]) : sender.getCommandSenderEntity();
				IAoVCapability cap = getCap(entity);
				ResetType type = null;
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase(TAB_LIST[0])) {
						type = ResetType.MINOR;
					} else if (args[1].equalsIgnoreCase(TAB_LIST[1])) {
						type = ResetType.MAJOR;
					} else if (args[1].equalsIgnoreCase(TAB_LIST[2])) {
						if (cap != null && entity instanceof EntityPlayer)
							cap.resetCharges((EntityPlayer) entity);
						sender.sendMessage(new TextComponentTranslation("commands.aov.success.reset"));
						return;
					}
				}
				if (type != null && cap != null) {
					cap.reset(type == ResetType.MAJOR);
					IPolymorphCapability poly = CapabilityList.getCap(entity, CapabilityList.POLYMORPH);
					if (poly != null)
						poly.morph(null);
					sender.sendMessage(new TextComponentTranslation("commands.aov.success.reset"));
					return;
				}
				throw new WrongUsageException("commands.aov.usage.reset");
			}

			@Override
			protected List<String> tabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
				return args.length == 2 ? getPlayersForTabComplete(server) : args.length == 1 ? getListOfStringsMatchingLastWord(args, TAB_LIST) : Collections.emptyList();
			}

			@Override
			protected boolean isUsernameIndex(String[] args, int index) {
				return index == 2;
			}
		};

		private final static String[] COMMAND_LIST;

		static {
			int length = SubCommand.values().length;

			String[] list = new String[length];

			for (SubCommand action : SubCommand.values())
				list[action.ordinal()] = action.toString().toLowerCase(Locale.ROOT);

			COMMAND_LIST = list;
		}

		protected abstract void execute(ICommand command, MinecraftServer server, ICommandSender sender, String[] args) throws CommandException;

		protected List<String> tabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
			return Collections.emptyList();
		}

		protected boolean isUsernameIndex(String[] args, int index) {
			return false;
		}

		protected final List<String> getPlayersForTabComplete(MinecraftServer server) {
			return Lists.newArrayList(server.getOnlinePlayerNames());
		}
	}

	enum ResetType {
		MINOR, MAJOR
	}
}
