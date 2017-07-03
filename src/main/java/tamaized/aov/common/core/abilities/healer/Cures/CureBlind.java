package tamaized.aov.common.core.abilities.healer.Cures;

import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.healer.CureEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;

public class CureBlind extends CureEffect {

	public CureBlind() {
		super("Cure Blind", 6, 2, MobEffects.BLINDNESS);
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public int getCoolDown() {
		return 10;
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid, "textures/spells/cureblind.png");
	}

	@Override
	protected int getParticleColor() {
		return 0xFFFFFFFF;
	}

}
