package tamaized.aov.common.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;

import javax.annotation.Nonnull;

public class PotionEwer extends Effect {

	public PotionEwer() {
		super(EffectType.BENEFICIAL, 0x00FFFF);
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 5 == 0;
	}

	@Override
	public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int p_76394_2_) {
		if (entityLivingBaseIn instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLivingBaseIn;
			IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
			if (cap != null)
				cap.restoreCharges(entityLivingBaseIn, 1);
		}
	}

}
