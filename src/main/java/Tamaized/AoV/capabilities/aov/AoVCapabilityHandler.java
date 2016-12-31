package Tamaized.AoV.capabilities.aov;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Tamaized.AoV.AoV;
import Tamaized.AoV.common.handlers.ClientPacketHandler;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.AuraBase;
import Tamaized.AoV.core.abilities.IAura;
import Tamaized.AoV.core.skills.AoVSkill;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class AoVCapabilityHandler implements IAoVCapability {

	public static final float xpScale = 2.5F;

	public static int getXpForLevel(IAoVCapability cap, int level) {
		return level > cap.getMaxLevel() ? 0 : getXpForLevel(level);
	}

	public static int getXpForLevel(int level) {
		return level < 1 ? 0 : (int) Math.floor(25 * (xpScale * (level - 1)));
	}

	private int tick = 1;
	private boolean dirty = false;

	private List<AoVSkill> obtainedSkills;
	private List<Ability> abilities = new ArrayList<Ability>();
	private Map<AuraBase, Integer> auras;
	private int maxSkillPoints = 1;
	private int skillPoints = 1;
	private int exp = 0;
	private int level = 1;
	private int maxLevel = 15;
	private boolean invokeMass = false;
	private Ability[] slots = new Ability[] { null, null, null, null, null, null, null, null, null };
	private int currentSlot = 0;

	// Calculate and update these when 'dirty'
	private int spellpower = 0;
	private int extraCharges = 0;
	private boolean selectiveFocus = false;
	private boolean hasInvoke = false;

	@Override
	public void update(EntityPlayer player) {
		updateAbilities();
		updateAuras();
		if (dirty) {
			updateValues();
			if (player instanceof EntityPlayerMP) sendPacketUpdates((EntityPlayerMP) player);
			dirty = false;
		}
		tick++;
	}

	private void updateValues() {
		spellpower = 0;
		extraCharges = 0;
		selectiveFocus = false;
		hasInvoke = false;
		for (AoVSkill skill : obtainedSkills) {
			spellpower += skill.getBuffs().spellPower;
			extraCharges += skill.getBuffs().charges;
			if (skill.getBuffs().selectiveFocus) selectiveFocus = true;
			for (AbilityBase ability : skill.getAbilities()) {
				if (ability == AbilityBase.invokeMass) hasInvoke = true;
			}

		}
	}

	private void updateAbilities() {
		for (Ability ability : abilities)
			ability.update();
	}

	private void updateAuras() {
		Iterator<Entry<AuraBase, Integer>> iter = auras.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<AuraBase, Integer> e = iter.next();
			AuraBase k = e.getKey();
			// k.update(this);
			if (k.getCurrentLife() >= e.getValue()) iter.remove();
		}
	}

	@Override
	public List<Ability> getAbilities() {
		return abilities;
	}

	@Override
	public boolean canUseAbility(Ability ability) {
		return abilities.contains(ability) && ability.canUse(this);
	}

	@Override
	public void addAbility(Ability ability) {
		if (abilities.size() < 9) {
			abilities.add(ability);
			dirty = true;
		}
	}

	@Override
	public void removeAbility(Ability ability) {
		if (abilities.contains(ability)) {
			abilities.remove(ability);
			dirty = true;
		}
	}

	@Override
	public void castAbility(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		if (canUseAbility(ability)) ability.cast(caster, target);
	}

	@Override
	public void addAura(IAura aura) {
		// TODO Auto-generated method stub
		dirty = true;
	}

	@Override
	public void addExp(int amount) {
		exp += amount;
		dirty = true;
	}

	@Override
	public void addObtainedSkill(AoVSkill skill) {
		if (!obtainedSkills.contains(skill)) {
			obtainedSkills.add(skill);
			dirty = true;
		}
	}

	@Override
	public boolean hasSkill(AoVSkill skill) {
		return obtainedSkills.contains(skill);
	}

	@Override
	public boolean hasCoreSkill() {
		for (AoVSkill skill : obtainedSkills)
			if (skill.isCore()) return true;
		return false;
	}

	@Override
	public AoVSkill getCoreSkill() {
		for (AoVSkill skill : obtainedSkills)
			if (skill.isCore()) return skill;
		return null;
	}

	@Override
	public List<AoVSkill> getObtainedSkills() {
		return obtainedSkills;
	}

	@Override
	public void removeSkill(AoVSkill skill) {
		obtainedSkills.remove(skill);
		dirty = true;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public int getXP() {
		return exp;
	}

	@Override
	public int getXpNeededToLevel() {
		return level >= maxLevel ? 0 : getXpForLevel(this, level) - exp;
	}

	@Override
	public int getSkillPoints() {
		return skillPoints;
	}

	@Override
	public int getSpentSkillPoints() {
		return maxSkillPoints - skillPoints;
	}

	@Override
	public int getMaxSkillPoints() {
		return maxSkillPoints;
	}

	@Override
	public float getSpellPower() {
		return spellpower;
	}

	@Override
	public int getExtraCharges() {
		return extraCharges;
	}

	@Override
	public boolean hasSelectiveFocus() {
		return selectiveFocus;
	}

	@Override
	public void toggleInvokeMass(boolean b) {
		invokeMass = hasInvoke ? b : false;
		dirty = true;
	}

	@Override
	public void toggleInvokeMass() {
		invokeMass = hasInvoke ? !invokeMass : false;
		dirty = true;
	}

	@Override
	public boolean hasInvokeMass() {
		return hasInvoke;
	}

	@Override
	public void setSlot(Ability ability, int slot) {
		if (slot >= 0 && slot < slots.length) slots[slot] = ability;
		dirty = true;
	}

	@Override
	public Ability getSlot(int slot) {
		return slot >= 0 && slot < slots.length ? slots[slot] : null;
	}

	@Override
	public int getCurrentSlot() {
		return currentSlot;
	}

	@Override
	public boolean slotsContain(Ability ability) {
		for (Ability a : slots)
			if (a != null && ability.getAbility() == a.getAbility()) return true;
		return false;
	}

	@Override
	public void addToNearestSlot(Ability ability) {
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] == null) {
				slots[i] = ability;
				break;
			}
		}
		dirty = true;
	}

	@Override
	public void removeSlot(int slot) {
		if (slot >= 0 && slot < slots.length) slots[slot] = null;
		dirty = true;
	}

	@Override
	public void clearAllSlots() {
		slots = new Ability[] { null, null, null, null, null, null, null, null, null };
		dirty = true;
	}

	@Override
	public void copyFrom(IAoVCapability cap) {
		// TODO Auto-generated method stub

	}

	private void sendPacketUpdates(EntityPlayerMP player) {
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream stream = new DataOutputStream(bos);
		try {
			stream.writeInt(ClientPacketHandler.getPacketTypeID(ClientPacketHandler.PacketType.AOVDATA));
			stream.writeInt(obtainedSkills.size());
			{
				for (AoVSkill skill : obtainedSkills)
					stream.writeInt(skill.getID());
			}
			stream.writeInt(abilities.size());
			{
				for (Ability ability : abilities)
					ability.encode(stream);
			}
			// TODO: figure out auras
			stream.writeInt(maxSkillPoints);
			stream.writeInt(skillPoints);
			stream.writeInt(exp);
			stream.writeInt(level);
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
			stream.writeInt(spellpower);
			stream.writeInt(extraCharges);
			stream.writeBoolean(selectiveFocus);
			stream.writeBoolean(hasInvoke);
			FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void decodePacket(ByteBufInputStream stream) throws IOException {
		// TODO Auto-generated method stub
	}

}
