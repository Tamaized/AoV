package tamaized.aov.common.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class PotionBalance extends Effect {

	public PotionBalance() {
		super(EffectType.BENEFICIAL, 0xFF0000);
		addAttributesModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "738D7064-6A60-4F59-8ABE-C2C23A6DD7B8", 0.0D, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
		return 3 * (double) (amplifier + 1);
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return false;
	}

	@Override
	public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int p_76394_2_) {

	}

}
