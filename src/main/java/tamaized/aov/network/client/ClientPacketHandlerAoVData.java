package tamaized.aov.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.tammodized.common.helper.CapabilityHelper;

import java.util.ArrayList;
import java.util.List;

public class ClientPacketHandlerAoVData implements IMessageHandler<ClientPacketHandlerAoVData.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerAoVData.Packet message, EntityPlayer player) {
		IAoVCapability cap = CapabilityHelper.getCap(player, CapabilityList.AOV, null);
		if (cap != null) {
			cap.getObtainedSkills().clear();
			for (AoVSkill skill : message.obtainedSkills)
				cap.addObtainedSkill(skill);
			cap.setSkillPoints(message.skillPoints);
			cap.setExp(message.exp);
			cap.setMaxLevel(message.maxLevel);
			cap.toggleInvokeMass(message.invokeMass);
			for (int index = 0; index < 9; index++)
				cap.setSlot(message.slots[index], index, true);
			cap.setCurrentSlot(message.currentSlot);
			cap.markDirty();
			cap.setLoaded();
		}
		IPolymorphCapability poly = CapabilityHelper.getCap(player, CapabilityList.POLYMORPH, null);
		if (poly != null) {
			poly.morph(message.polymorph);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerAoVData.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().player));
		return null;
	}

	public static class Packet implements IMessage {

		private List<AoVSkill> obtainedSkills = new ArrayList<>();
		private int skillPoints;
		private int exp;
		private int maxLevel;
		private boolean invokeMass;
		private Ability[] slots = new Ability[]{null, null, null, null, null, null, null, null, null};
		private int currentSlot;
		private IPolymorphCapability.Morph polymorph;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(IAoVCapability cap, IPolymorphCapability poly) {
			obtainedSkills = cap.getObtainedSkills();
			skillPoints = cap.getSkillPoints();
			exp = cap.getExp();
			maxLevel = cap.getMaxLevel();
			invokeMass = cap.getInvokeMass();
			slots = cap.getSlots();
			currentSlot = cap.getCurrentSlot();
			polymorph = poly.getMorph();
		}

		@Override
		public void fromBytes(ByteBuf stream) {
			int size = stream.readInt();
			obtainedSkills.clear();
			for (int index = 0; index < size; index++) {
				obtainedSkills.add(AoVSkills.getSkillFromID(stream.readInt()));
			}
			skillPoints = stream.readInt();
			exp = stream.readInt();
			maxLevel = stream.readInt();
			invokeMass = stream.readBoolean();
			for (int index = 0; index < 9; index++) {
				slots[index] = stream.readBoolean() ? Ability.construct(stream) : null;
			}
			currentSlot = stream.readInt();
			polymorph = IPolymorphCapability.Morph.getMorph(stream.readInt());
		}

		@Override
		public void toBytes(ByteBuf stream) {
			stream.writeInt(obtainedSkills.size());
			for (AoVSkill skill : obtainedSkills)
				stream.writeInt(skill == null ? -1 : skill.getID());
			stream.writeInt(skillPoints);
			stream.writeInt(exp);
			stream.writeInt(maxLevel);
			stream.writeBoolean(invokeMass);
			for (int index = 0; index < 9; index++) {
				Ability ability = slots[index];
				if (ability == null) {
					stream.writeBoolean(false);
				} else {
					stream.writeBoolean(true);
					ability.encode(stream);
				}
			}
			stream.writeInt(currentSlot);
			stream.writeInt(polymorph == null ? -1 : polymorph.ordinal());
		}
	}
}
