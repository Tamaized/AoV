package tamaized.aov.network.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.network.NetworkMessages;

public class ServerPacketHandlerSpellSkill implements NetworkMessages.IMessage<ServerPacketHandlerSpellSkill> {

	public PacketType id;
	public int[] data = {};
	public AbilityBase ability;

	@Override
	public void handle(EntityPlayer player) {
		IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
		IPolymorphCapability poly = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap == null)
			return;
		switch (id) {
			case CAST_SPELL: {
				if (data.length <= 0)
					break;
				Ability ability = cap.getSlot(data[0]);
				if (ability == null)
					break;
				if (data.length > 1) {
					Entity e = player.world.getEntityByID(data[1]);
					if (e instanceof EntityLivingBase) {
						ability.cast(player, (EntityLivingBase) e);
						break;
					}
				}
				ability.cast(player);
			}
			break;
			case SKILLEDIT_CHECK_CANOBTAIN: {
				if (data.length <= 0)
					break;
				AoVSkill skillToCheck = AoVSkills.getSkillFromID(data[0]);
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
				if (data.length > 0)
					cap.removeSlot(data[0]);
			}
			break;
			case SPELLBAR_ADDNEAR: {
				cap.addToNearestSlot(ability);
			}
			break;
			default:
				break;
		}
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(id.ordinal());
		packet.writeInt(data.length);
		for (int dat : data)
			packet.writeInt(dat);
		packet.writeInt(AbilityBase.getID(ability));
	}

	@Override
	public ServerPacketHandlerSpellSkill fromBytes(PacketBuffer packet) {
		PacketType[] types = PacketType.VALUES;
		int i = packet.readInt();
		id = i >= types.length ? null : types[i];
		int size = packet.readInt();
		data = new int[size];
		for (int index = 0; index < size; index++)
			data[index] = packet.readInt();
		ability = AbilityBase.getAbilityFromID(packet.readInt());
		return this;
	}

	public enum PacketType {
		SKILLEDIT_CHECK_CANOBTAIN, RESETSKILLS_FULL, RESETSKILLS_MINOR, SPELLBAR_REMOVE, SPELLBAR_ADDNEAR, CAST_SPELL;

		public static final PacketType[] VALUES = values();
	}
}
