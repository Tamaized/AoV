package tamaized.aov.common.core.abilities;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public abstract class AbilityBase {

	private static final List<AbilityBase> registry = Lists.newArrayList();

	private final List<TextComponentTranslation> description;

	public AbilityBase(TextComponentTranslation... desc) {
		description = Lists.newArrayList();
		description.addAll(Arrays.asList(desc));
		registry.add(this);
	}

	public static int getID(AbilityBase skill) {
		return registry.contains(skill) ? registry.indexOf(skill) : -1;
	}

	public static AbilityBase getAbilityFromID(int id) {
		return id >= 0 && id < registry.size() ? registry.get(id) : null;
	}

	public final int getID() {
		return getID(this);
	}

	public boolean shouldDisable(@Nullable EntityPlayer caster, IAoVCapability cap) {
		IPolymorphCapability poly = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		return poly != null && poly.getMorph() == IPolymorphCapability.Morph.Wolf;
	}

	public boolean runOnClient() {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	public final List<String> getDescription() {
		List<String> list = Lists.newArrayList();
		for (TextComponentTranslation s : description) {
			Object[] args = new Object[s.getFormatArgs().length];
			for (int index = 0; index < s.getFormatArgs().length; index++)
				args[index] = I18n.format(s.getFormatArgs()[index].toString());
			list.add(I18n.format(s.getKey(), args));
		}
		return list;
	}

	@OnlyIn(Dist.CLIENT)
	public abstract String getName();

	public abstract int getMaxCharges();

	public int getExtraCharges(EntityLivingBase entity, IAoVCapability cap) {
		return 0;
	}

	public abstract int getChargeCost();

	public abstract double getMaxDistance();

	public abstract int getCoolDown();

	public abstract boolean usesInvoke();

	public abstract boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target);

	public int getCost(IAoVCapability cap) {
		return (usesInvoke() && cap.getInvokeMass()) ? (getChargeCost() * 2) : getChargeCost();
	}

	public boolean canUseOnCooldown(IAoVCapability cap, EntityPlayer caster) {
		return false;
	}

	public void onCooldownCast(Ability ability, EntityPlayer caster, EntityLivingBase target, int cooldown) {

	}

	/**
	 * @return false to not use a charge or set the cooldown
	 */
	public abstract boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target);

	public abstract ResourceLocation getIcon();

	static final class NullAbility extends AbilityBase {

		private final String name;

		public NullAbility(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getMaxCharges() {
			return 0;
		}

		@Override
		public int getChargeCost() {
			return 0;
		}

		@Override
		public double getMaxDistance() {
			return 0;
		}

		@Override
		public int getCoolDown() {
			return 0;
		}

		@Override
		public boolean usesInvoke() {
			return false;
		}

		@Override
		public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
			return false;
		}

		@Override
		public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
			return false;
		}

		@Override
		public ResourceLocation getIcon() {
			return null;
		}

	}

}
