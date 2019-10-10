package tamaized.aov.common.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;

public class PotionColdChill extends Effect {

	public PotionColdChill() {
		super(EffectType.HARMFUL, 0xFFFFFF);
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % (20 * 3) == 0;
	}

	@Override
	public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amp) {
		entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1F + (amp * 1F));
		entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20 * 5, amp));
		entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 20 * 5, amp));
	}

}
