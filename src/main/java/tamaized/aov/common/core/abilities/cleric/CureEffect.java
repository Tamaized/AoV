package tamaized.aov.common.core.abilities.cleric;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.client.ParticleHelper;
import tamaized.aov.registry.SoundEvents;

import java.util.List;

public abstract class CureEffect extends AbilityBase {

	private final String name;
	private final int charges;
	private final double range;
	private final Effect effect;

	public CureEffect(String n, int c, double r, Effect effect) {
		super(

				new TranslationTextComponent(n),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", c),

				new TranslationTextComponent("aov.spells.global.range", r),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.cure.desc")

		);
		name = n;
		charges = c;
		range = r;
		this.effect = effect;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(name);
	}

	@Override
	public double getMaxDistance() {
		return range;
	}

	@Override
	public int getMaxCharges() {
		return charges;
	}

	@Override
	public boolean usesInvoke() {
		return true;
	}

	@Override
	public boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target) {
		return IAoVCapability.canBenefit(caster, cap, target);
	}

	protected abstract int getParticleColor();

	@Override
	public boolean cast(Ability ability, PlayerEntity caster, LivingEntity e) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap == null)
			return false;
		if (cap.getInvokeMass())
			castAsMass(caster, cap);
		else if (e == null) {
			caster.removePotionEffect(effect);
			SoundEvents.playMovingSoundOnServer(SoundEvents.restore, caster);
		} else {
			if (IAoVCapability.canBenefit(caster, cap, e)) {
				e.removePotionEffect(effect);
				SoundEvents.playMovingSoundOnServer(SoundEvents.restore, e);
			}
		}
		cap.addExp(caster, 12, this);
		return true;
	}

	private void castAsMass(LivingEntity caster, IAoVCapability cap) {
		int range = (int) (getMaxDistance() * 2);
		ParticleHelper.spawnParticleMesh(ParticleHelper.MeshType.BURST, ParticleHelper.ParticleType.Heart, caster.world, caster.getPositionVector(), range, getParticleColor());
		List<LivingEntity> list = caster.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(caster.getPosition().add(-range, -range, -range), caster.getPosition().add(range, range, range)));
		for (LivingEntity entity : list) {
			if (IAoVCapability.canBenefit(caster, cap, entity)) {
				entity.removePotionEffect(effect);
				SoundEvents.playMovingSoundOnServer(SoundEvents.restore, entity);
				cap.addExp(caster, 12, this);
			}
		}
	}

}
