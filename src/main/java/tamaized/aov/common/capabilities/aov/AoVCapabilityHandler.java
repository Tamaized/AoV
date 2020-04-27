package tamaized.aov.common.capabilities.aov;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import tamaized.aov.AoV;
import tamaized.aov.client.ClientHelpers;
import tamaized.aov.client.ParticleHelper;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.abilities.Aura;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.common.entity.EntitySpellAoVParticles;
import tamaized.aov.common.helper.FloatyTextHelper;
import tamaized.aov.network.client.ClientPacketHandlerAoVData;
import tamaized.aov.network.client.ClientPacketHandlerAoVSimpleData;
import tamaized.aov.network.server.ServerPacketHandlerSpellSkill;
import tamaized.aov.registry.AoVPotions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AoVCapabilityHandler implements IAoVCapability {

	public static final float xpScale = 2.5F;
	private static final String PALADIN_HEALTH_NAME = "AoV Paladin Health";
	private static final String DRUID_HEALTH_NAME = "AoV Druid Health";
	private static final Set<String> HEALTH_NAMES = ImmutableSet.of(

			"AoV Defender Health", // Legacy; Placed here to ensure it gets removed properly

			PALADIN_HEALTH_NAME,

			DRUID_HEALTH_NAME

	);
	private static final AttributeModifier PALADIN_HEALTH = new AttributeModifier(PALADIN_HEALTH_NAME, 10.0D, AttributeModifier.Operation.ADDITION);
	private int tick = 1;
	private boolean dirty = true;
	private boolean hasLoaded = false;
	private int currentSlot = 0;
	private List<AoVSkill> obtainedSkills = new ArrayList<>();
	private int skillPoints = 1;
	private int exp = 0;
	private int maxLevel = AoV.config.maxlevel.get();
	private boolean invokeMass = false;
	private Ability[] slots = new Ability[]{null, null, null, null, null, null, null, null, null};
	private List<Ability> abilities = new ArrayList<>();
	private float spellpower = 0;
	private int extraCharges = 0;
	private int dodge = 0;
	private int doublestrike = 0;
	private boolean selectiveFocus = false;
	private boolean hasInvoke = false;
	private List<Aura> auras = new ArrayList<>();
	private Map<AbilityBase, DecayWrapper> decay = new HashMap<>();
	private Map<AbilityBase, Integer> cooldowns = new HashMap<>();

	private boolean hasAid = false;
	private boolean hasZeal = false;
	private boolean hasEwer = false;
	private boolean hasBalance = false;
	private boolean hasSpire = false;
	private boolean hasSpear = false;

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
			maxLevel = AoV.config.maxlevel.get();
			decay.clear();
		} else {
			AoVSkill core = getCoreSkill();
			obtainedSkills.clear();
			obtainedSkills.add(core);
			skillPoints = getLevel() - 1;
		}
		abilities.clear();
		auras.clear();
		invokeMass = false;
		slots = new Ability[]{null, null, null, null, null, null, null, null, null};
		dirty = true;
	}

	@Override
	public void update(@Nullable PlayerEntity player) {
		if (!hasLoaded)
			return;
		tick++;
		checkState(player);
		updateAbilities(player);
		updateAuras(player);
		updateDecay();
		if (tick % (20 * 10) == 0)
			dirty = true;
		if (dirty) {
			updateValues(player);
			if (player instanceof ServerPlayerEntity)
				sendPacketUpdates((ServerPlayerEntity) player);
			dirty = false;
		}
		if (tick % 10 == 0)
			updateHealth(player);
		if (tick % (20 * 30) == 0 && hasSkill(AoVSkills.paladin_capstone) && player != null && !player.removed) {
			ItemStack main = player.getHeldItemMainhand();
			ItemStack off = player.getHeldItemOffhand();
			if (!main.isEmpty() && main.getItem().isShield(main, player) && main.getStack().isRepairable() && main.getDamage() > 0) {
				main.setDamage(0);
			}
			if (!off.isEmpty() && off.getItem().isShield(off, player) && off.getStack().isRepairable() && off.getDamage() > 0) {
				off.setDamage(0);
			}
		}
	}

	private void checkState(@Nullable PlayerEntity player) {
		if (!(player instanceof ServerPlayerEntity) || !player.isAlive())
			return;
		final boolean aid = player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.aid.get())) != null;
		final boolean zeal = player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.zeal.get())) != null;
		final boolean ewer = player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.ewer.get())) != null;
		final boolean balance = player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.balance.get())) != null;
		final boolean spire = player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.spire.get())) != null;
		final boolean spear = player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.spear.get())) != null;
		if (hasAid != aid || hasZeal != zeal || hasEwer != ewer || hasBalance != balance || hasSpire != spire || hasSpear != spear) {
			hasAid = aid;
			hasZeal = zeal;
			hasEwer = ewer;
			hasBalance = balance;
			hasSpire = spire;
			hasSpear = spear;
			markDirty();
		}
	}

	private void updateHealth(@Nullable PlayerEntity player) {
		if (player == null || player.world == null || player.world.isRemote)
			return;
		IAttributeInstance hp = player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
		Iterator<AttributeModifier> iter = hp.func_225505_c_().iterator();
		//noinspection WhileLoopReplaceableByForEach
		while (iter.hasNext()) {
			AttributeModifier mod = iter.next();
			if (HEALTH_NAMES.contains(mod.getName())) {
				hp.removeModifier(mod);
			}
		}
		if (hasSkill(AoVSkills.paladin_tier_4_2) && !hp.hasModifier(PALADIN_HEALTH) && player.getHeldItemOffhand().getItem().isShield(player.getHeldItemOffhand(), player))
			hp.applyModifier(PALADIN_HEALTH);
		if (getCoreSkill() == AoVSkills.druid_core_1 && IAoVCapability.isCentered(player, this))
			hp.applyModifier(new AttributeModifier(DRUID_HEALTH_NAME, getLevel(), AttributeModifier.Operation.ADDITION));
	}

	private void updateValues(@Nullable PlayerEntity player) {
		IAstroCapability astro = CapabilityList.getCap(player, CapabilityList.ASTRO);
		maxLevel = AoV.config.maxlevel.get();
		skillPoints = getLevel();
		spellpower = 0;
		extraCharges = 0;
		dodge = 0;
		doublestrike = 0;
		selectiveFocus = false;
		hasInvoke = false;
		List<AbilityBase> list = new ArrayList<>();
		obtainedSkills.removeIf(Objects::isNull);
		for (AoVSkill skill : obtainedSkills) {
			if (skill == null)
				continue;
			skillPoints -= skill.getCost();
			spellpower += skill.getSpellPower();
			extraCharges += skill.getCharges();
			dodge += skill.getDodge();
			doublestrike += skill.getDoubleStrike();
			if (AoVSkills.isSelectiveFocusSkill(skill))
				selectiveFocus = true;
			for (AbilityBase ability : skill.getAbilities()) {
				if (ability == Abilities.invokeMass)
					hasInvoke = true;
				list.add(ability);
			}
		}
		if (getCoreSkill() == AoVSkills.druid_core_1 && IAoVCapability.isCentered(player, this)) {
			int level = getLevel();
			dodge += level;
			doublestrike += level;
			spellpower += (5 * level);
			if (hasSkill(AoVSkills.druid_core_3))
				dodge += 25;
			if (hasSkill(AoVSkills.druid_capstone))
				dodge += 25;
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
			IPolymorphCapability poly = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
			if (poly != null) {
				if (poly.getMorph() == IPolymorphCapability.Morph.Wolf) {
					doublestrike += 15;
					dodge += 10;
				} else if (poly.getMorph() == IPolymorphCapability.Morph.ArchAngel) {
					spellpower += 75;
					dodge += 25;
				} else if (poly.getMorph() == IPolymorphCapability.Morph.FireElemental && player.world.getDayTime() % 24000 < 12000) // Day (6 AM)
					spellpower += 20;
				else if (poly.getMorph() == IPolymorphCapability.Morph.WaterElemental && player.world.getDayTime() % 24000 >= 12000) // Night (6 PM)
					spellpower += 20;
			}
			if (player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.aid.get())) != null)
				dodge += 5;
			if (player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.zeal.get())) != null)
				doublestrike += 25;
			EffectInstance spire = player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.spire.get()));
			if (spire != null)
				dodge += 10 * (spire.getAmplifier() + 1);
			EffectInstance spear = player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.spear.get()));
			if (spear != null)
				doublestrike += 10 * (spear.getAmplifier() + 1);
			EffectInstance balance = player.getActivePotionEffect(Objects.requireNonNull(AoVPotions.balance.get()));
			if (balance != null)
				spellpower += 50 * (balance.getAmplifier() + 1);
		}
	}

	private void updateAbilities(@Nullable PlayerEntity player) {
		for (Ability ability : slots)
			if (ability != null)
				ability.update(player, this);
		if (tick % 20 == 0)
			for (Iterator<Map.Entry<AbilityBase, Integer>> iter = cooldowns.entrySet().iterator(); iter.hasNext(); ) {
				Map.Entry<AbilityBase, Integer> entry = iter.next();
				entry.setValue(entry.getValue() - 1);
				if (entry.getValue() <= 0)
					iter.remove();
			}
	}

	private void updateAuras(@Nullable PlayerEntity player) {
		Iterator<Aura> iter = auras.iterator();
		while (iter.hasNext()) {
			Aura aura = iter.next();
			if ((player != null && !player.world.isRemote) || aura.getSpell().getAbility().runOnClient())
				aura.update(player);
			if (aura.removed())
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
	public void setCooldown(AbilityBase ability, int cd) {
		cooldowns.put(ability, cd);
	}

	@Override
	public int getCooldown(AbilityBase ability) {
		return cooldowns.getOrDefault(ability, 0);
	}

	@Override
	public Map<AbilityBase, Integer> getCooldowns() {
		return Collections.unmodifiableMap(cooldowns);
	}

	@Override
	public void setCooldowns(Map<AbilityBase, Integer> map) {
		cooldowns.clear();
		for (Map.Entry<AbilityBase, Integer> entry : map.entrySet())
			cooldowns.put(entry.getKey(), entry.getValue());
	}

	@Override
	public void resetCharges(PlayerEntity player) {
		cooldowns.clear();
		decay.clear();
		for (Ability ability : slots)
			if (ability != null)
				ability.reset(player, this);
		dirty = true;
	}

	@Override
	public void restoreCharges(LivingEntity caster, int amount) {
		for (Ability ability : slots)
			if (ability != null)
				ability.restoreCharge(caster, this, amount);
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
		for (Ability check : abilities)
			if (check.getAbility() == ability.getAbility())
				return;
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
	public void clearAuras() {
		auras.clear();
	}

	@Override
	public void addAura(Aura aura) {
		auras.removeIf(a -> a.equals(aura));
		auras.add(aura);
		dirty = true;
	}

	@Override
	public <T> boolean isAuraActive(T aura) {
		return auras.stream().anyMatch(a -> a.getAsAura() == aura);
	}

	@Override
	public List<Aura> getAuras() {
		return Collections.unmodifiableList(auras);
	}

	@Override
	public void addExp(Entity player, int amount, AbilityBase spell) {
		if (!hasCoreSkill() || getLevel() >= getMaxLevel())
			return;
		if (spell != null) {
			if (decay.containsKey(spell)) {
				int dec = decay.get(spell).getDecay();
				if (dec > 0)
					amount /= dec;
				decay.get(spell).addDecay();
			} else {
				decay.put(spell, new DecayWrapper());
			}
		}
		int tempLevel = getLevel();
		exp += amount;
		if (player instanceof ServerPlayerEntity) {
			FloatyTextHelper.sendText((ServerPlayerEntity) player, "+" + amount + " Exp");
			if (getLevel() > tempLevel) {
				FloatyTextHelper.sendText((ServerPlayerEntity) player, "Level Up! (" + (getLevel()) + ")");
				player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, player.getSoundCategory(), 0.25F, 0.95F + 0.5F * ((ServerPlayerEntity) player).getRNG().nextFloat());
				player.world.addEntity(new EntitySpellAoVParticles(player.world, player, ParticleHelper.ParticleType.Spark, 1, 0x00FFD8FF, 0x00FF87FF, 0x3FFF6AFF));
			}
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
	public int getExtraCharges(LivingEntity caster, Ability ability) {
		return extraCharges + ((caster == null || ability == null) ? 0 : ability.getAbility().getExtraCharges(caster, this));
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
	@OnlyIn(Dist.CLIENT)
	public void cast(int slotLoc) {
		AoV.network.sendToServer(new ServerPacketHandlerSpellSkill(ServerPacketHandlerSpellSkill.PacketType.CAST_SPELL, null, ClientHelpers.getTarget() != null ? new int[]{slotLoc, ClientHelpers.getTarget().getEntityId()} : new int[]{slotLoc}));
		Ability ability = getSlot(slotLoc);
		if (ability != null && ability.getAbility().runOnClient())
			if (ClientHelpers.getTarget() == null)
				ability.cast(Minecraft.getInstance().player);
			else
				ability.cast(Minecraft.getInstance().player, ClientHelpers.getTarget());
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

	private void sendPacketUpdates(ServerPlayerEntity player) {
		IPolymorphCapability poly = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (poly == null)
			return;
		AoV.network.send(PacketDistributor.PLAYER.with(() -> player), new ClientPacketHandlerAoVData(this, poly));
		AoV.network.send(PacketDistributor.TRACKING_ENTITY.with(() -> player), new ClientPacketHandlerAoVSimpleData(this, CapabilityList.getCap(player, CapabilityList.POLYMORPH), player.getEntityId()));
	}

	@Override
	public void handleClone(IAoVCapability data) {
		obtainedSkills = data.getObtainedSkills();
		skillPoints = data.getSkillPoints();
		exp = data.getExp();
		maxLevel = data.getMaxLevel();
		invokeMass = data.getInvokeMass();
		abilities = data.getAbilities();
		for (int index = 0; index < 9; index++) {
			slots[index] = data.getSlot(index);
		}
		currentSlot = data.getCurrentSlot();
		setDecayMap(data.getDecayMap());
		hasLoaded = true;
		dirty = true;
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
