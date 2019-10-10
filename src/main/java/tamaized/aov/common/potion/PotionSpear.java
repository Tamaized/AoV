package tamaized.aov.common.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class PotionSpear extends Effect {

	public PotionSpear() {
		super(EffectType.BENEFICIAL, 0x0000FF);
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int p_76394_2_) {
		entityLivingBaseIn.ticksSinceLastSwing = 9000;
	}

}
