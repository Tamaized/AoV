package tamaized.aov.network.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.abilities.Aura;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.network.NetworkMessages;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ClientPacketHandlerAoVData implements NetworkMessages.IMessage<ClientPacketHandlerAoVData> {

	private List<AoVSkill> obtainedSkills;
	private int skillPoints;
	private int exp;
	private int maxLevel;
	private boolean invokeMass;
	private Ability[] slots;
	private int currentSlot;
	private IPolymorphCapability.Morph polymorph;
	private byte renderBits;
	private Map<AbilityBase, Integer> cooldowns;
	private List<Aura> auras;

	public ClientPacketHandlerAoVData(IAoVCapability cap, IPolymorphCapability poly) {
		obtainedSkills = cap.getObtainedSkills();
		skillPoints = cap.getSkillPoints();
		exp = cap.getExp();
		maxLevel = cap.getMaxLevel();
		invokeMass = cap.getInvokeMass();
		slots = cap.getSlots();
		currentSlot = cap.getCurrentSlot();
		cooldowns = cap.getCooldowns();
		polymorph = poly.getMorph();
		renderBits = poly.getFlagBits();
		auras = cap.getAuras();
	}

	@Override
	public void handle(EntityPlayer player) {
		IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
		if (cap != null) {
			cap.getObtainedSkills().clear();
			for (AoVSkill skill : obtainedSkills)
				cap.addObtainedSkill(skill);
			cap.setSkillPoints(skillPoints);
			cap.setExp(exp);
			cap.setMaxLevel(maxLevel);
			cap.toggleInvokeMass(invokeMass);
			for (int index = 0; index < 9; index++)
				cap.setSlot(slots[index], index, true);
			cap.setCurrentSlot(currentSlot);
			cap.setCooldowns(Collections.unmodifiableMap(cooldowns));
			cap.clearAuras();
			for (Aura aura : auras)
				cap.addAura(aura);
			cap.markDirty();
			cap.setLoaded();
		}
		IPolymorphCapability poly = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (poly != null) {
			poly.morph(polymorph);
			poly.setFlagBits(renderBits);
		}
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(obtainedSkills.size());
		for (AoVSkill skill : obtainedSkills)
			packet.writeInt(skill == null ? -1 : skill.getID());
		packet.writeInt(skillPoints);
		packet.writeInt(exp);
		packet.writeInt(maxLevel);
		packet.writeBoolean(invokeMass);
		for (int index = 0; index < 9; index++) {
			Ability ability = slots[index];
			if (ability == null) {
				packet.writeBoolean(false);
			} else {
				packet.writeBoolean(true);
				ability.encode(packet);
			}
		}
		packet.writeInt(currentSlot);
		packet.writeInt(cooldowns.size());
		for (Map.Entry<AbilityBase, Integer> entry : cooldowns.entrySet()) {
			packet.writeInt(entry.getKey().getID());
			packet.writeInt(entry.getValue());
		}
		packet.writeInt(polymorph == null ? -1 : polymorph.ordinal());
		packet.writeByte(renderBits);
		packet.writeInt(auras.size());
		for (Aura aura : auras)
			aura.encode(packet);
	}

	@Override
	public ClientPacketHandlerAoVData fromBytes(PacketBuffer packet) {
		obtainedSkills = Lists.newArrayList();
		int size = packet.readInt();
		for (int index = 0; index < size; index++) {
			obtainedSkills.add(AoVSkills.getSkillFromID(packet.readInt()));
		}
		skillPoints = packet.readInt();
		exp = packet.readInt();
		maxLevel = packet.readInt();
		invokeMass = packet.readBoolean();
		slots = new Ability[9];
		for (int index = 0; index < 9; index++) {
			slots[index] = packet.readBoolean() ? Ability.construct(packet) : null;
		}
		currentSlot = packet.readInt();
		cooldowns = Maps.newHashMap();
		size = packet.readInt();
		for (int index = 0; index < size; index++) {
			cooldowns.put(AbilityBase.getAbilityFromID(packet.readInt()), packet.readInt());
		}
		polymorph = IPolymorphCapability.Morph.getMorph(packet.readInt());
		renderBits = packet.readByte();
		auras = Lists.newArrayList();
		size = packet.readInt();
		for (int index = 0; index < size; index++) {
			auras.add(Aura.construct(packet));
		}
		return this;
	}
}
