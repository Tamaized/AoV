package tamaized.aov.network.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.abilities.Aura;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.network.NetworkMessages;

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

	}

	@Override
	public void toBytes(PacketBuffer packet) {

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
