package tamaized.aov.common.core.abilities;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;

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

	@SuppressWarnings("unused")
	public boolean shouldDisable(IAoVCapability cap, @Nullable IAstroCapability astro) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public final List<String> getDescription() {
		List<String> list = Lists.newArrayList();
		for (TextComponentTranslation s : description) {
			list.add(I18n.format(s.getKey(), s.getFormatArgs()));
		}
		return list;
	}

	@SideOnly(Side.CLIENT)
	public abstract String getName();

	public abstract int getMaxCharges();

	public abstract int getChargeCost();

	public abstract double getMaxDistance();

	public abstract int getCoolDown();

	public abstract boolean usesInvoke();

	public int getCost(IAoVCapability cap) {
		return (usesInvoke() && cap.getInvokeMass()) ? (getChargeCost() * 2) : getChargeCost();
	}

	/**
	 * @return false to not use a charge
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
		public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
			return false;
		}

		@Override
		public ResourceLocation getIcon() {
			return null;
		}

	}

}
