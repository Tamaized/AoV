package tamaized.aov.common.core.abilities.cleric.Healing;

import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.cleric.CureWounds;

public class CureSeriousWounds extends CureWounds {

	public CureSeriousWounds() {
		super(CureSeriousWounds.getStaticName(), 6, 2, 8);
	}

	public static String getStaticName() {
		return "aov.spells.cureseriouswounds.name";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.MODID, "textures/spells/cureseriouswounds.png");
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
