package tamaized.aov.common.core.abilities.cleric.Healing;

import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.cleric.CureWounds;

public class CureLightWounds extends CureWounds {

	public CureLightWounds() {
		super(CureLightWounds.getStaticName(), 10, 2, 4);
	}

	public static String getStaticName() {
		return "aov.spells.curelightwounds.name";
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
