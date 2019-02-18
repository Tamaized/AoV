package tamaized.aov.common.capabilities.aov;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.aov.AoVCapabilityHandler.DecayWrapper;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.abilities.Aura;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IAoVCapability {

	ResourceLocation ID = new ResourceLocation(AoV.MODID, "AoVCapabilityHandler");

	/**
	 * @return true if can damage
	 */
	static boolean selectiveTarget(@Nullable Entity caster, @Nullable IAoVCapability cap, EntityLivingBase entity) {
		return caster != entity && (cap == null || !cap.hasSelectiveFocus() || // We don't have selective focus, just return true
				caster == null || // Caster is null, what's the point, return true
				(caster.getTeam() != null && // We are on a team
						!caster.isOnSameTeam(entity)) || // Not the same team, return true
				(!(entity instanceof IEntityOwnable && // Target is a possible pet
						((IEntityOwnable) entity).getOwner() == caster) && // If this is our pet, dont do the final player check, let it return false
						(!(entity instanceof EntityPlayer) && (!(entity instanceof IAnimals) || entity instanceof IMob)))); // Wasn't our pet, we're not on a team, is target a player or passive mob? if not return true
		// If all the above fails, it'll return false.
	}

	static boolean canBenefit(Entity caster, IAoVCapability cap, EntityLivingBase entity) {
		return !cap.hasSelectiveFocus() || !selectiveTarget(caster, cap, entity);
	}

	static boolean isCentered(EntityLivingBase entity, IAoVCapability cap) {
		if (entity != null && cap != null && cap.hasSkill(AoVSkills.druid_core_1)) {
			for (ItemStack stack : entity.getArmorInventoryList())
				if (!stack.isEmpty() && !ItemStackWrapper.compare(ConfigHandler.CENTERED_WEAR, stack))
					return false;
			if (!entity.getHeldItemMainhand().isEmpty() && !ItemStackWrapper.compare(ConfigHandler.CENTERED_WEAR, entity.getHeldItemMainhand()))
				return !entity.getHeldItemMainhand().getAttributeModifiers(EntityEquipmentSlot.MAINHAND).containsKey(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
			return entity.getHeldItemOffhand().isEmpty() || ItemStackWrapper.compare(ConfigHandler.CENTERED_WEAR, entity.getHeldItemOffhand()) || !entity.getHeldItemOffhand().getAttributeModifiers(EntityEquipmentSlot.OFFHAND).containsKey(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
		}
		return false;
	}

	static boolean isImprovedCentered(EntityLivingBase entity, IAoVCapability cap) {
		return cap != null && cap.hasSkill(AoVSkills.druid_core_4) && isCentered(entity, cap);
	}

	void markDirty();

	void setLoaded();

	void reset(boolean b);

	void update(EntityPlayer player);

	void setCooldown(AbilityBase ability, int cd);

	int getCooldown(AbilityBase ability);

	Map<AbilityBase, Integer> getCooldowns();

	void setCooldowns(Map<AbilityBase, Integer> map);

	void resetCharges(EntityPlayer player);

	void restoreCharges(EntityLivingBase caster, int amount);

	List<Ability> getAbilities();

	boolean canUseAbility(Ability ability);

	void addAbility(Ability ability);

	void removeAbility(Ability ability);

	void clearAuras();

	void addAura(Aura aura);

	<T> boolean isAuraActive(T aura);

	List<Aura> getAuras();

	void addExp(Entity player, int amount, AbilityBase spell);

	void addObtainedSkill(AoVSkill skill);

	boolean hasSkill(AoVSkill skill);

	boolean hasCoreSkill();

	AoVSkill getCoreSkill();

	List<AoVSkill> getObtainedSkills();

	void removeSkill(AoVSkill skill);

	int getLevel();

	int getMaxLevel();

	void setMaxLevel(int amount);

	int getExp();

	void setExp(int amount);

	int getExpNeededToLevel();

	int getSkillPoints();

	void setSkillPoints(int amount);

	int getSpentSkillPoints();

	float getSpellPower();

	int getExtraCharges(@Nullable EntityLivingBase caster, @Nullable Ability ability);

	int getDodge();

	default int getDodgeForRand() {
		return Math.round(100F / ((float) getDodge()));
	}

	int getDoubleStrike();

	default int getDoubleStrikeForRand() {
		return Math.round(100F / ((float) getDoubleStrike()));
	}

	boolean hasSelectiveFocus();

	void toggleInvokeMass(boolean b);

	void toggleInvokeMass();

	boolean getInvokeMass();

	boolean hasInvokeMass();

	void cast(int slotLoc);

	void setSlot(Ability ability, int slot, boolean force);

	Ability getSlot(int slot);

	/**
	 * Returns -1 if slots dont contain ability
	 */
	int getSlotFromAbility(AbilityBase ability);

	default Ability getAbilityFromSlots(AbilityBase ability) {
		return getSlot(getSlotFromAbility(ability));
	}

	int getCurrentSlot();

	void setCurrentSlot(int index);

	boolean slotsContain(AbilityBase ability);

	void addToNearestSlot(AbilityBase ability);

	void removeSlot(int slot);

	void clearAllSlots();

	Map<AbilityBase, DecayWrapper> getDecayMap();

	void setDecayMap(Map<AbilityBase, DecayWrapper> map);

	void copyFrom(IAoVCapability cap);

	Ability[] getSlots();

	class ItemStackWrapper {

		boolean ignoreMeta;
		boolean ignoreNBT;
		ItemStack stack;

		public ItemStackWrapper(Item item) {
			this(item, 0);
			ignoreMeta = true;
		}

		public ItemStackWrapper(Item item, int meta) {
			this(new ItemStack(item, 1, meta), false, true);
		}

		public ItemStackWrapper(NBTTagCompound tag, boolean ignoreMeta, boolean ignoreNBT) {
			this(new ItemStack(tag), ignoreMeta, ignoreNBT);
		}

		public ItemStackWrapper(ItemStack stack, boolean ignoreMeta, boolean ignoreNBT) {
			this.stack = stack;
			this.ignoreMeta = ignoreMeta;
			this.ignoreNBT = ignoreNBT;
		}

		public static boolean compare(Set<ItemStackWrapper> set, ItemStack stack) {
			boolean flag;
			for (ItemStackWrapper wrapper : set) {
				if (wrapper.ignoreMeta && wrapper.ignoreNBT)
					flag = wrapper.stack.getItem() == stack.getItem();
				else if (wrapper.ignoreNBT)
					flag = wrapper.stack.isItemEqual(stack);
				else if (wrapper.ignoreMeta)
					flag = wrapper.stack.getItem() == stack.getItem() && ItemStack.areItemStackTagsEqual(wrapper.stack, stack);
				else
					flag = ItemStack.areItemStacksEqual(wrapper.stack, stack);
				if (flag)
					return true;
			}
			return false;
		}

		public ItemStackWrapper attachNBT(NBTTagCompound tag) {
			ignoreNBT = false;
			stack.setTagCompound(tag);
			return this;
		}

	}
}
