package Tamaized.AoV.core.abilities.healer.Cures;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.healer.CureEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;

public class CureWither extends CureEffect {

	public CureWither() {
		super("Cure Wither", 6, 2, MobEffects.WITHER);
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
