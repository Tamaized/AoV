package tamaized.aov.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nullable;

public class ServerPacketHandlerSpellSkill implements IMessageHandler<ServerPacketHandlerSpellSkill.Packet, IMessage> {

	private static void processPacket(Packet message, EntityPlayerMP player, @SuppressWarnings("unused") World world) {
		if (!player.hasCapability(CapabilityList.AOV, null))
			return;
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		IPolymorphCapability poly = CapabilityHelper.getCap(player, CapabilityList.POLYMORPH, null);
		if (cap == null)
			return;
		switch (message.id) {
			case CAST_SPELL: {
				if (message.data.length <= 0)
					break;
				Ability ability = cap.getSlot(message.data[0]);
				if (ability == null)
					break;
				if (message.data.length > 1) {
					Entity e = world.getEntityByID(message.data[1]);
					if (e instanceof EntityLivingBase) {
						ability.cast(player, (EntityLivingBase) e);
						break;
					}
				}
				ability.cast(player);
			}
			break;
			case SKILLEDIT_CHECK_CANOBTAIN: {
				if (message.data.length <= 0)
					break;
				AoVSkill skillToCheck = AoVSkills.getSkillFromID(message.data[0]);
				if (skillToCheck == null)
					break;
				if (skillToCheck.getParent() == null || cap.hasSkill(skillToCheck.getParent())) {
					if (cap.getSkillPoints() >= skillToCheck.getCost() && cap.getLevel() >= skillToCheck.getLevel() && cap.getSpentSkillPoints() >= skillToCheck.getSpentPoints()) {
						if (!(skillToCheck.isClassCore() && cap.hasCoreSkill())) {
							if (skillToCheck.getParent() == null || cap.hasSkill(skillToCheck.getParent())) {
								cap.addObtainedSkill(skillToCheck);
								cap.setSkillPoints(cap.getSkillPoints() - skillToCheck.getCost());
							}
						}
					}
				}
			}
			break;
			case RESETSKILLS_FULL: {
				cap.reset(true);
				if (poly != null)
					poly.morph(null);
			}
			break;
			case RESETSKILLS_MINOR: {
				cap.reset(false);
				if (poly != null)
					poly.morph(null);
			}
			break;
			case SPELLBAR_REMOVE: {
				if (message.data.length > 0)
					cap.removeSlot(message.data[0]);
			}
			break;
			case SPELLBAR_ADDNEAR: {
				cap.addToNearestSlot(message.ability);
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
		public int[] data = {};
		public AbilityBase ability;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(PacketType type, @Nullable AbilityBase ability, int... data) {
			id = type;
			this.data = data;
			this.ability = ability;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			PacketType[] types = PacketType.values();
			int i = buf.readInt();
			id = i >= types.length ? null : types[i];
			int size = buf.readInt();
			data = new int[size];
			for (int index = 0; index < size; index++)
				data[index] = buf.readInt();
			ability = AbilityBase.getAbilityFromID(buf.readInt());
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(id.ordinal());
			buf.writeInt(data.length);
			for (int dat : data)
				buf.writeInt(dat);
			buf.writeInt(AbilityBase.getID(ability));
		}

		public enum PacketType {
			SKILLEDIT_CHECK_CANOBTAIN, RESETSKILLS_FULL, RESETSKILLS_MINOR, SPELLBAR_REMOVE, SPELLBAR_ADDNEAR, CAST_SPELL
		}
	}
}