package tamaized.aov.common.core.abilities.cleric.Cures;

import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.cleric.CureEffect;

public class CureWither extends CureEffect {

	public CureWither() {
		super("aov.spells.curewither.name", 6, 2, Effects.WITHER);
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public int getCoolDown() {
		return 15;
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.MODID, "textures/spells/curewither.png");
	}

	@Override
	protected int getParticleColor() {
		return 0x555555FF;
	}

}
