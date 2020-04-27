package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellVanillaParticles;
import tamaized.aov.registry.AoVPotions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class Draw extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.MODID, "textures/spells/draw.png");

	private static final int charges = -1;
	private static final int distance = 10;

	public Draw() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.range", distance),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.draw.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.draw.name";
	}

	public static void doDrawEffects(LivingEntity caster, @Nonnull IAstroCapability.ICard card, int potency, @Nullable IAstroCapability.ICard burn, boolean fromAoe) {
		int ticks = 300;
		int hardcodedBalancePotency = fromAoe ? potency : 0;
		boolean aoe = false;
		if (burn != null)
			switch (burn) {
				default:
				case Balance:
				case Bole:
					hardcodedBalancePotency = 2;
					potency *= 2.5F;
					if (potency <= 0)
						potency = 2;
					break;
				case Spear:
				case Arrow:
					ticks *= 3;
					break;
				case Ewer:
				case Spire:
					aoe = true;
					hardcodedBalancePotency = 1;
					potency *= 1.5F;
					if (potency <= 0)
						potency = 1;
					break;
			}
		if (aoe) {
			IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
			int range = 16;
			for (LivingEntity e : caster.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(caster.getPosX() - range, caster.getPosY() - range, caster.getPosZ() - range, caster.getPosX() + range, caster.getPosY() + range, caster.getPosZ() + range))) {
				if (cap == null || IAoVCapability.canBenefit(caster, cap, e))
					doDrawEffects(e, card, potency, null, true);
			}
			return;
		}
		switch (card) {
			default:
			case Balance:
				caster.addPotionEffect(new EffectInstance(Objects.requireNonNull(AoVPotions.balance.get()), ticks, hardcodedBalancePotency));
				break;
			case Bole:
				caster.addPotionEffect(new EffectInstance(Effects.RESISTANCE, ticks, potency));
				caster.addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, ticks, (int) Math.floor(potency / 2F)));
				caster.addPotionEffect(new EffectInstance(Effects.SATURATION, 20 * 3, (int) Math.floor(potency / 2F)));
				break;
			case Spear:
				caster.addPotionEffect(new EffectInstance(Objects.requireNonNull(AoVPotions.spear.get()), ticks, potency));
				break;
			case Arrow:
				caster.addPotionEffect(new EffectInstance(Effects.HASTE, ticks, potency));
				break;
			case Ewer:
				caster.addPotionEffect(new EffectInstance(Objects.requireNonNull(AoVPotions.ewer.get()), ticks, 0));
				break;
			case Spire:
				caster.addPotionEffect(new EffectInstance(Objects.requireNonNull(AoVPotions.spire.get()), ticks, potency));
				break;
		}
		caster.world.addEntity(new EntitySpellVanillaParticles(caster.world, caster, ParticleTypes.ENCHANTED_HIT, 5));
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
	}

	@Override
	public int getCoolDown() {
		return 30;
	}

	@Override
	public int getMaxCharges() {
		return charges;
	}

	@Override
	public int getChargeCost() {
		return 0;
	}

	@Override
	public double getMaxDistance() {
		return distance;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target) {
		return IAoVCapability.canBenefit(caster, cap, target);
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity caster, LivingEntity target) {
		IAstroCapability astro = CapabilityList.getCap(caster, CapabilityList.ASTRO);
		IAoVCapability aov = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (astro == null || aov == null)
			return false;
		if (astro.getDraw() == null) {
			astro.drawCard(caster);
			ability.setNextCooldown(1);
			ability.setTimer(30);
		} else {
			IAstroCapability.ICard card = astro.getDraw();
			LivingEntity entity = target == null || caster.getDistance(target) >= getMaxDistance() ? caster : target;
			int potency = (int) Math.floor(aov.getSpellPower() / 10F);
			IAstroCapability.ICard burn = astro.getBurn();
			astro.useDraw(caster);
			doDrawEffects(entity, card, potency, burn, false);
			aov.addExp(caster, 15, this);
			ability.setTimer(-1);
		}
		astro.sendPacketUpdates(caster);
		return true;
	}

}
