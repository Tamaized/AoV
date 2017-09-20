package tamaized.aov.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.skills.AoVSkill;

import javax.annotation.Nullable;

public class ServerPacketHandlerSpellSkill implements IMessageHandler<ServerPacketHandlerSpellSkill.Packet, IMessage> {

	private static void processPacket(Packet message, EntityPlayerMP player, @SuppressWarnings("unused") World world) {
		if (!player.hasCapability(CapabilityList.AOV, null))
			return;
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null)
			return;
		switch (message.id) {
			case CAST_SPELL: {
				Ability ability = cap.getSlot(message.object);
				if (ability == null)
					break;
				ability.cast(player);
			}
			break;
			case SKILLEDIT_CHECK_CANOBTAIN: {
				AoVSkill skillToCheck = AoVSkill.getSkillFromID(message.object);
				if (skillToCheck == null)
					break;
				if (skillToCheck.getParent() == null || cap.hasSkill(skillToCheck.getParent())) {
					if (cap.getSkillPoints() >= skillToCheck.getCost() && cap.getLevel() >= skillToCheck.getLevel() && cap.getSpentSkillPoints() >= skillToCheck.getSpentPoints()) {
						cap.addObtainedSkill(skillToCheck);
						cap.setSkillPoints(cap.getSkillPoints() - skillToCheck.getCost());
					}
				}
			}
			break;
			case RESETSKILLS_FULL: {
				cap.reset(true);
			}
			break;
			case RESETSKILLS_MINOR: {
				cap.reset(false);
			}
			break;
			case SPELLBAR_REMOVE: {
				cap.removeSlot(message.object);
				cap.resetCharges();
			}
			break;
			case SPELLBAR_ADDNEAR: {
				cap.addToNearestSlot(message.ability);
				cap.resetCharges();
			}
			break;
			default:
				break;
		}
	}

	@Override
	public IMessage onMessage(Packet message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().player;
		MinecraftServer server = player.getServer();
		if (server != null)
			server.addScheduledTask(() -> processPacket(message, player, player.world));
		return null;
	}

	public static class Packet implements IMessage {

		public PacketType id;
		public int object;
		public Ability ability;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(PacketType type, int data, @Nullable Ability ability) {
			id = type;
			object = data;
			this.ability = ability;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			PacketType[] types = PacketType.values();
			int i = buf.readInt();
			id = i >= types.length ? null : types[i];
			object = buf.readInt();
			if (buf.readBoolean())
				ability = Ability.construct(buf);
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(id.ordinal());
			buf.writeInt(object);
			boolean flag = ability != null;
			buf.writeBoolean(flag);
			if (flag)
				ability.encode(buf);
		}

		public enum PacketType {
			SKILLEDIT_CHECK_CANOBTAIN, RESETSKILLS_FULL, RESETSKILLS_MINOR, SPELLBAR_REMOVE, SPELLBAR_ADDNEAR, CAST_SPELL
		}
	}
}