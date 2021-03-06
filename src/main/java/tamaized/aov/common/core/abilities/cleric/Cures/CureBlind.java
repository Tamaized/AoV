package tamaized.aov.common.core.abilities.cleric.Cures;

import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.cleric.CureEffect;

public class CureBlind extends CureEffect {

	public CureBlind() {
		super("aov.spells.cureblind.name", 6, 2, Effects.BLINDNESS);
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
		return new ResourceLocation(AoV.MODID, "textures/spells/cureblind.png");
	}

	@Override
	protected int getParticleColor() {
		return 0xFFFFFFFF;
	}

}
