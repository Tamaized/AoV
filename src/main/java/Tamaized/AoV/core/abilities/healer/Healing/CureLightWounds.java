package Tamaized.AoV.core.abilities.healer.Healing;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.healer.CureWounds;
import net.minecraft.util.ResourceLocation;

public class CureLightWounds extends CureWounds {

	public CureLightWounds() {
		super(CureLightWounds.getStaticName(), 10, 2, 4);
	}

	public static String getStaticName() {
		return "Cure Light Wounds";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid, "textures/spells/curelightwounds.png");
	}

	@Override
	public int getCoolDown() {
		return 2;
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	protected int getParticleColor() {
		return 0xFF7474FF;
	}

}
