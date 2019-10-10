package tamaized.aov.common.core.abilities;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbilityBase {

	private static final List<AbilityBase> registry = Lists.newArrayList();

	private final List<TranslationTextComponent> description;
	private ITextComponent descriptionCache;

	public AbilityBase(TranslationTextComponent... desc) {
		description = Lists.newArrayList();
		for (TranslationTextComponent component : desc) {
			description.add(component);
			description.add(new TranslationTextComponent("\n"));
		}
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

	public boolean shouldDisable(@Nullable PlayerEntity caster, IAoVCapability cap) {
		IPolymorphCapability poly = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		return poly != null && poly.getMorph() == IPolymorphCapability.Morph.Wolf;
	}

	public boolean runOnClient() {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	public final List<String> getDescription() {
		List<String> list = Lists.newArrayList();
		for (TranslationTextComponent s : description) {
			Object[] args = new Object[s.getFormatArgs().length];
			for (int index = 0; index < s.getFormatArgs().length; index++)
				args[index] = I18n.format(s.getFormatArgs()[index].toString());
			list.add(I18n.format(s.getKey(), args));
		}
		return list;
	}

	@OnlyIn(Dist.CLIENT)
	public final ITextComponent getDescriptionAsTextComponent() {
		if (descriptionCache == null && !description.isEmpty()) {
			TranslationTextComponent component = new TranslationTextComponent(description.get(0).getKey(), description.get(0).getFormatArgs());
			for (int i = 1; i < description.size(); i++)
				component.appendSibling(new TranslationTextComponent(description.get(i).getKey(), description.get(i).getFormatArgs()));
			descriptionCache = component.setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component)));
		}
		return descriptionCache;
	}

	@OnlyIn(Dist.CLIENT)
	public abstract String getName();

	public abstract int getMaxCharges();

	public int getExtraCharges(LivingEntity entity, IAoVCapability cap) {
		return 0;
	}

	public abstract int getChargeCost();

	public abstract double getMaxDistance();

	public abstract int getCoolDown();

	public abstract boolean usesInvoke();

	public abstract boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target);

	public int getCost(IAoVCapability cap) {
		return (usesInvoke() && cap.getInvokeMass()) ? (getChargeCost() * 2) : getChargeCost();
	}

	public boolean canUseOnCooldown(IAoVCapability cap, PlayerEntity caster) {
		return false;
	}

	public void onCooldownCast(Ability ability, PlayerEntity caster, LivingEntity target, int cooldown) {

	}

	/**
	 * @return false to not use a charge or set the cooldown
	 */
	public abstract boolean cast(Ability ability, PlayerEntity caster, LivingEntity target);

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
		public boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target) {
			return false;
		}

		@Override
		public boolean cast(Ability ability, PlayerEntity caster, LivingEntity target) {
			return false;
		}

		@Override
		public ResourceLocation getIcon() {
			return null;
		}

	}

}
