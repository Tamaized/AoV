package tamaized.aov.common.core.abilities.healer.Healing;

import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.healer.CureWounds;
import net.minecraft.util.ResourceLocation;

public class CureModWounds extends CureWounds {

	public CureModWounds() {
		super(CureModWounds.getStaticName(), 8, 2, 6);
	}

	public static String getStaticName() {
		return "Cure Moderate Wounds";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid, "textures/spells/curemodwounds.png");
	}

	@Override
	public int getCoolDown() {
		return 3;
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	protected int getParticleColor() {
		return 0xFF5454FF;
	}

}
