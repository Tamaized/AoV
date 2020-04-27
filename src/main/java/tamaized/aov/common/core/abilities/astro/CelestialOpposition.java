package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntityCelestialOpposition;
import tamaized.aov.registry.SoundEvents;

public class CelestialOpposition extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.MODID, "textures/spells/celestialopposition.png");

	private static final int charges = -1;
	private static final int distance = 8;

	public CelestialOpposition() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", "aov.gui.infinite"),

				new TranslationTextComponent("aov.spells.global.range", distance),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.celestialopposition.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.celestialopposition.name";
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
		return 120;
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
		return false;
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity caster, LivingEntity target) {
		IAoVCapability aov = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (!caster.world.isRemote && aov != null) {
			for (LivingEntity e : caster.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(caster.getPosX() - distance, caster.getPosY() - distance, caster.getPosZ() - distance, caster.getPosX() + distance, caster.getPosY() + distance, caster.getPosZ() + distance))) {
				if (e != caster && IAoVCapability.selectiveTarget(caster, aov, e)) {
					IStunCapability stun = CapabilityList.getCap(e, CapabilityList.STUN);
					if (stun != null) {
						stun.setStunTicks(20 * 8);
						aov.addExp(caster, 25, ability.getAbility());
					}
				}
				if (e == caster || IAoVCapability.canBenefit(caster, aov, e)) {
					for (EffectInstance effect : e.getActivePotionEffects())
						if (effect.getPotion().getEffectType() != EffectType.HARMFUL)
							e.addPotionEffect(new EffectInstance(effect.getPotion(), effect.getDuration() + 400, effect.getAmplifier(), effect.isAmbient(), effect.doesShowParticles()));
				}
			}
			EntityCelestialOpposition spell = new EntityCelestialOpposition(caster.world);
			spell.setPosition(caster.getPosX(), caster.getPosY(), caster.getPosZ());
			caster.world.addEntity(spell);
			SoundEvents.playMovingSoundOnServer(SoundEvents.celestialopposition, spell, 0.5F, 1F);
			return true;
		}
		return false;
	}

}
