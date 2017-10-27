package tamaized.aov.common.core.abilities.healer.Cures;

import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.healer.CureEffect;

public class CureWither extends CureEffect {

	public CureWither() {
		super("aov.spells.curewither.name", 6, 2, MobEffects.WITHER);
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
		return new ResourceLocation(AoV.modid, "textures/spells/curewither.png");
	}

	@Override
	protected int getParticleColor() {
		return 0x555555FF;
	}

}
