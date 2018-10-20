package tamaized.aov.common.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.AoVCapabilityHandler;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.aov.common.gui.GuiHandler;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nonnull;

public class CommandAoV extends CommandBase {

	private static IAoVCapability getCap(Entity e) {
		return CapabilityHelper.getCap(e, CapabilityList.AOV, null);
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
			switch (args[0].toLowerCase()) {
				case "open":
					if (sender.getCommandSenderEntity() instanceof EntityPlayer)
						GuiHandler.openGUI(GuiHandler.GUI_SKILLS, (EntityPlayer) sender.getCommandSenderEntity(), sender.getEntityWorld());
					return;
				case "setlevel":
					if (args.length > 1) {
						int level;
						try {
							level = MathHelper.clamp(Integer.parseInt(args[1]), 0, ConfigHandler.maxlevel);
						} catch (NumberFormatException e) {
							throw new WrongUsageException("commands.aov.error.level");
						}
						IAoVCapability cap;
						Entity e;
						if (args.length > 2)
							cap = getCap(e = getEntity(server, sender, args[2]));
						else
							cap = getCap(e = sender.getCommandSenderEntity());
						if (cap != null){
							cap.reset(true);
							cap.setExp(AoVCapabilityHandler.getExpForLevel(level));
							sender.sendMessage(new TextComponentTranslation("commands.aov.success.level", e.getName(), level));
						}
						return;
					}
					throw new WrongUsageException("commands.aov.usage.setlevel");
				case "reset":
					ResetType type = null;
					if (args.length > 1) {
						switch (args[1].toLowerCase()) {
							case "minor":
								type = ResetType.MINOR;
								break;
							case "full":
								type = ResetType.MAJOR;
								break;
						}
					}
					IAoVCapability cap = getCap(args.length > 2 ? getEntity(server, sender, args[2]) : sender.getCommandSenderEntity());
					if (type != null && cap != null) {
						cap.reset(type == ResetType.MAJOR);
						sender.sendMessage(new TextComponentTranslation("commands.aov.success.reset"));
						return;
					}
					throw new WrongUsageException("commands.aov.usage.reset");
			}
		}
		throw new WrongUsageException(getUsage(sender));
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	enum ResetType {
		MINOR, MAJOR
	}
}
