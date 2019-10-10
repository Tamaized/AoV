package tamaized.aov.common.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class PotionSlowFall extends Effect {

	public PotionSlowFall() {
		super(EffectType.BENEFICIAL, 0xFFFFFF);
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
		if (entityLivingBaseIn.getMotion().y < -0.25F)
			entityLivingBaseIn.setMotion(entityLivingBaseIn.getMotion().x, -0.25F, entityLivingBaseIn.getMotion().z);
	}

	@Override
	public void applyAttributesModifiersToEntity(LivingEntity entityLivingBaseIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier) {
		super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
	}

}
