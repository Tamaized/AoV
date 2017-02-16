package Tamaized.AoV.core.abilities.healer.Healing;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.healer.CureWounds;
import net.minecraft.util.ResourceLocation;

public class CureSeriousWounds extends CureWounds {

	public CureSeriousWounds() {
		super(CureSeriousWounds.getStaticName(), 6, 2, 8);
	}

	public static String getStaticName() {
		return "Cure Serious Wounds";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid + ":textures/spells/cureSeriousWounds.png");
	}

	@Override
	public int getCoolDown() {
		return 4;
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	protected int getParticleColor() {
		return 0xFF3434FF;
	}

}
