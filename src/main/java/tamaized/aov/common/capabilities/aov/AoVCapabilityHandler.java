package tamaized.aov.common.capabilities.aov;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.abilities.Aura;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.network.client.ClientPacketHandlerAoVData;
import tamaized.aov.network.server.ServerPacketHandlerSpellSkill;
import tamaized.aov.registry.AoVPotions;
import tamaized.tammodized.common.helper.FloatyTextHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AoVCapabilityHandler implements IAoVCapability {

	public static final float xpScale = 2.5F;
	private static final String defenderHealthName = "AoV Defender Health";
	private static final AttributeModifier defenderHealth = new AttributeModifier(defenderHealthName, 10.0D, 0);
	private int tick = 1;
	private boolean dirty = true;
	private boolean hasLoaded = false;
	// TODO
	private int currentSlot = 0;
	// Calculate and update these when 'dirty'
	private List<AoVSkill> obtainedSkills = new ArrayList<>();
	private int skillPoints = 1;
	private int exp = 0;
	private int maxLevel = 15;
	private boolean invokeMass = false;
	private Ability[] slots = new Ability[]{null, null, null, null, null, null, null, null, null};
	// These can be separate
	private List<Ability> abilities = new ArrayList<>();
	private float spellpower = 0;
	private int extraCharges = 0;
	private int dodge = 0;
	private int doublestrike = 0;
	private boolean selectiveFocus = false;
	private boolean hasInvoke = false;
	// Keep this on the server
	private List<Aura> auras = new ArrayList<>();
	private Map<AbilityBase, DecayWrapper> decay = new HashMap<>();

	public static int getExpForLevel(IAoVCapability cap, int level) {
		return level > cap.getMaxLevel() ? 0 : getExpForLevel(level);
	}

	public static int getExpForLevel(int level) {
		return level < 1 ? 0 : (int) Math.floor(25 * ((xpScale + (level - 2)) * (level - 1)));
	}

	public static int getLevelFromExp(float xp) {
		xp += 0.5F; // We need to offset because of float to int to float casting
		double a = ((-5 * xpScale) + Math.sqrt(25 * Math.pow(xpScale, 2) - 50 * xpScale + 4 * xp + 25) + 15) / 10;
		double b = ((-5 * xpScale) - Math.sqrt(25 * Math.pow(xpScale, 2) - 50 * xpScale + 4 * xp + 25) + 15) / 10;
		return Math.max((int) a, (int) b);
	}

	@Override
	public void markDirty() {
		dirty = true;
	}

	@Override
	public void setLoaded() {
		hasLoaded = true;
	}

	@Override
	public void reset(boolean b) {
		if (b) {
			obtainedSkills.clear();
			skillPoints = 1;
			exp = 0;
			maxLevel = 15;
		} else {
			AoVSkill core = getCoreSkill();
			obtainedSkills.clear();
			obtainedSkills.add(core);
			skillPoints = getLevel() - 1;
		}
		abilities.clear();
		auras.clear();
		decay.clear();
		invokeMass = false;
		slots = new Ability[]{null, null, null, null, null, null, null, null, null};
		dirty = true;
	}

	@Override
	public void update(EntityPlayer player) {
		if (!hasLoaded)
			return;
		tick++;
		if (tick % 20 == 0)
			updateAbilities();
		updateAuras(player);
		updateDecay();
		if (tick % (20 * 10) == 0)
			dirty = true;
		if (dirty) {
			updateValues(player);
			if (player instanceof EntityPlayerMP)
				sendPacketUpdates((EntityPlayerMP) player);
			dirty = false;
		}
		if (tick % 10 == 0)
			updateHealth(player);
		if (tick % (20 * 30) == 0 && hasSkill(AoVSkills.defender_capstone) && player != null) {
			ItemStack main = player.getHeldItemMainhand();
			ItemStack off = player.getHeldItemOffhand();
			if (!main.isEmpty() && main.getItem() instanceof ItemShield && main.getItem().isRepairable() && main.getItemDamage() > 0) {
				main.setItemDamage(0);
			}
			if (!off.isEmpty() && off.getItem() instanceof ItemShield && off.getItem().isRepairable() && off.getItemDamage() > 0) {
				off.setItemDamage(0);
			}
		}
	}

	private void updateHealth(EntityPlayer player) {
		if (player == null || player.world == null || player.world.isRemote)
			return;
		IAttributeInstance hp = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		Iterator<AttributeModifier> iter = hp.getModifiers().iterator();
		//noinspection WhileLoopReplaceableByForEach
		while (iter.hasNext()) {
			AttributeModifier mod = iter.next();
			if (mod.getName().equals(defenderHealthName)) {
				hp.removeModifier(mod);
			}
		}
		if (hasSkill(AoVSkills.defender_tier_4_2) && !hp.hasModifier(defenderHealth) && player.getHeldItemOffhand().getItem() instanceof ItemShield) {
			hp.applyModifier(defenderHealth);
		}
	}

	private void updateValues(EntityPlayer player) {
		IAstroCapability astro = player != null && player.hasCapability(CapabilityList.ASTRO, null) ? player.getCapability(CapabilityList.ASTRO, null) : null;
		skillPoints = getLevel();
		for (AoVSkill s : obtainedSkills)
			skillPoints -= s.getCost();
		spellpower = 0;
		extraCharges = 0;
		dodge = 0;
		doublestrike = 0;
		selectiveFocus = false;
		hasInvoke = false;
		List<AbilityBase> list = new ArrayList<>();
		for (AoVSkill skill : obtainedSkills) {
			if (skill == null)
				continue;
			spellpower += skill.getSpellPower();
			extraCharges += skill.getCharges();
			dodge += skill.getDodge();
			doublestrike += skill.getDoubleStrike();
			if (skill.grantsSelectiveFocus())
				selectiveFocus = true;
			for (AbilityBase ability : skill.getAbilities()) {
				if (ability == Abilities.invokeMass)
					hasInvoke = true;
				list.add(ability);
			}
		}
		Iterator<Ability> iter = abilities.iterator();
		main:
		while (iter.hasNext()) {
			Ability check = iter.next();
			for (AbilityBase ability : list)
				if (check.getAbility() == ability)
					continue main;
			iter.remove();
		}
		for (AbilityBase ability : list) {
			boolean flag = true;
			for (Ability check : abilities)
				if (check.getAbility() == ability)
					flag = false;
			if (flag)
				addAbility(new Ability(ability, this, astro));
		}
		if (player != null && player.world != null && !player.world.isRemote)
			for (int index = 0; index < 9; index++) {
				if (slots[index] != null) {
					boolean flag = true;
					for (Ability check : abilities)
						if (check.getAbility() == slots[index].getAbility()) {
							slots[index] = check;
							flag = false;
							break;
						}
					if (flag)
						slots[index] = null;
				}
			}
		if (player != null) {
			if (player.getActivePotionEffect(AoVPotions.aid) != null)
				dodge += 5;
			if (player.getActivePotionEffect(AoVPotions.zeal) != null)
				doublestrike += 25;
			PotionEffect spire = player.getActivePotionEffect(AoVPotions.spire);
			if (spire != null)
				dodge += 10 * spire.getAmplifier();
			PotionEffect spear = player.getActivePotionEffect(AoVPotions.spear);
			if (spear != null)
				doublestrike += 10 * spear.getAmplifier();
		}
	}

	private void updateAbilities() {
		for (Ability ability : slots)
			if (ability != null)
				ability.update(this);
	}

	private void updateAuras(EntityPlayer player) {
		Iterator<Aura> iter = auras.iterator();
		while (iter.hasNext()) {
			Aura aura = iter.next();
			aura.update(player);
			if (aura.isDead())
				iter.remove();
		}
	}

	private void updateDecay() {
		Iterator<DecayWrapper> iter = decay.values().iterator();
		while (iter.hasNext()) {
			DecayWrapper wrapper = iter.next();
			wrapper.update();
			if (wrapper.getDecay() <= 0)
				iter.remove();
		}
	}

	@Override
	public void resetCharges(EntityPlayer player) {
		for (Ability ability : slots)
			if (ability != null)
				ability.reset(this, player.hasCapability(CapabilityList.ASTRO, null) ? player.getCapability(CapabilityList.ASTRO, null) : null);
		dirty = true;
	}

	@Override
	public void restoreCharges(int amount) {
		for (Ability ability : slots)
			if (ability != null)
				ability.restoreCharge(this, amount);
		dirty = true;
	}

	@Override
	public List<Ability> getAbilities() {
		return abilities;
	}

	@Override
	public boolean canUseAbility(Ability ability) {
		dirty = true;
		boolean flag = false;
		for (Ability a : abilities)
			if (a.compare(ability) && ability.canUse(this))
				flag = true;
		return flag;
	}

	@Override
	public void addAbility(Ability ability) {
		abilities.add(ability);
		dirty = true;
	}

	@Override
	public void removeAbility(Ability ability) {
		Iterator<Ability> iter = abilities.iterator();
		while (iter.hasNext()) {
			Ability a = iter.next();
			if (a.compare(ability)) {
				iter.remove();
				dirty = true;
			}
		}
	}

	@Override
	public void addAura(Aura aura) {
		auras.removeIf(a -> a.equals(aura));
		auras.add(aura);
		dirty = true;
	}

	@Override
	public void addExp(Entity player, int amount, AbilityBase spell) {
		if (!hasCoreSkill() || getLevel() >= getMaxLevel())
			return;
		if (spell != null) {
			if (decay.containsKey(spell)) {
				amount /= decay.get(spell).getDecay();
				decay.get(spell).addDecay();
			} else {
				decay.put(spell, new DecayWrapper());
			}
		}
		int tempLevel = getLevel();
		exp += amount;
		if (player instanceof EntityPlayerMP) {
			FloatyTextHelper.sendText((EntityPlayerMP) player, "+" + amount + " Exp");
			if (getLevel() > tempLevel)
				FloatyTextHelper.sendText((EntityPlayerMP) player, "Level Up! (" + (getLevel()) + ")");
		}
		dirty = true;
	}

	@Override
	public void addObtainedSkill(AoVSkill skill) {
		if (skill != null && !obtainedSkills.contains(skill) && !(skill.isClassCore() && hasCoreSkill())) {
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
			if (skill.isClassCore())
				return true;
		return false;
	}

	@Override
	public AoVSkill getCoreSkill() {
		for (AoVSkill skill : obtainedSkills)
			if (skill.isClassCore())
				return skill;
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
		return getLevelFromExp(exp);
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public void setMaxLevel(int amount) {
		maxLevel = amount;
		dirty = true;
	}

	@Override
	public int getExp() {
		return exp;
	}

	@Override
	public void setExp(int amount) {
		exp = amount;
		dirty = true;
	}

	@Override
	public int getExpNeededToLevel() {
		return getLevel() >= maxLevel ? 0 : getExpForLevel(this, getLevel() + 1) - exp;
	}

	@Override
	public int getSkillPoints() {
		return skillPoints;
	}

	@Override
	public void setSkillPoints(int amount) {
		skillPoints = amount;
		dirty = true;
	}

	@Override
	public int getSpentSkillPoints() {
		return getLevel() - skillPoints;
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
	public int getDodge() {
		return dodge;
	}

	@Override
	public int getDoubleStrike() {
		return doublestrike;
	}

	@Override
	public boolean hasSelectiveFocus() {
		return selectiveFocus;
	}

	@Override
	public void toggleInvokeMass(boolean b) {
		invokeMass = hasInvoke && b;
		dirty = true;
	}

	@Override
	public void toggleInvokeMass() {
		invokeMass = hasInvoke && !invokeMass;
		dirty = true;
	}

	@Override
	public boolean getInvokeMass() {
		return invokeMass;
	}

	@Override
	public boolean hasInvokeMass() {
		return hasInvoke;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void cast(int slotLoc) {
		AoV.network.sendToServer(new ServerPacketHandlerSpellSkill.Packet(ServerPacketHandlerSpellSkill.Packet.PacketType.CAST_SPELL, slotLoc, null));
	}

	@Override
	public void setSlot(Ability ability, int slot, boolean force) {
		if (force) {
			slots[slot] = ability;
			dirty = true;
			return;
		}
		for (Ability check : getAbilities()) {
			if (ability == null || check.compare(ability))
				if (slot >= 0 && slot < slots.length) {
					slots[slot] = ability == null ? null : check;
					break;
				}
		}
		dirty = true;
	}

	@Override
	public Ability getSlot(int slot) {
		return slot >= 0 && slot < slots.length ? slots[slot] : null;
	}

	@Override
	public int getSlotFromAbility(AbilityBase ability) {
		int index = 0;
		for (Ability a : slots) {
			if (a != null && ability == a.getAbility())
				return index;
			index++;
		}
		return -1;
	}

	@Override
	public int getCurrentSlot() {
		return currentSlot;
	}

	@Override
	public void setCurrentSlot(int index) {
		currentSlot = index;
		dirty = true;
	}

	@Override
	public boolean slotsContain(AbilityBase ability) {
		for (Ability a : slots)
			if (a != null && ability == a.getAbility())
				return true;
		return false;
	}

	@Override
	public void addToNearestSlot(AbilityBase ability) {
		if (slotsContain(ability))
			return;
		Ability a = null;
		if (ability != null)
			for (Ability check : abilities)
				if (check.getAbility() == ability) {
					a = check;
					break;
				}
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] == null) {
				setSlot(a, i, false);
				break;
			}
		}
		dirty = true;
	}

	@Override
	public void removeSlot(int slot) {
		if (slot >= 0 && slot < slots.length)
			slots[slot] = null;
		dirty = true;
	}

	@Override
	public void clearAllSlots() {
		slots = new Ability[]{null, null, null, null, null, null, null, null, null};
		dirty = true;
	}

	@Override
	public void copyFrom(IAoVCapability cap) {
		obtainedSkills = cap.getObtainedSkills();
		skillPoints = cap.getSkillPoints();
		exp = cap.getExp();
		maxLevel = cap.getMaxLevel();
		invokeMass = cap.getInvokeMass();
		abilities = cap.getAbilities();
		for (int index = 0; index < 9; index++) {
			slots[index] = cap.getSlot(index);
		}
		currentSlot = cap.getCurrentSlot();
		setDecayMap(cap.getDecayMap());
		hasLoaded = true;
		dirty = true;
	}

	@Override
	public Map<AbilityBase, DecayWrapper> getDecayMap() {
		return Collections.unmodifiableMap(decay);
	}

	@Override
	public void setDecayMap(Map<AbilityBase, DecayWrapper> map) {
		decay.clear();
		decay.putAll(map);
	}

	@Override
	public Ability[] getSlots() {
		return slots;
	}

	private void sendPacketUpdates(EntityPlayerMP player) {
		AoV.network.sendTo(new ClientPacketHandlerAoVData.Packet(this), player);
	}

	protected class DecayWrapper {
		private int amount;
		private int tick = 0;
		private int decayTick = 20 * 30;

		public DecayWrapper(int a) {
			amount = a;
		}

		public DecayWrapper() {
			this(1);
		}

		public void addDecay() {
			amount++;
		}

		public void update() {
			tick++;
			if (tick % decayTick == 0) {
				amount--;
			}
		}

		public int getDecay() {
			return amount;
		}
	}

}
