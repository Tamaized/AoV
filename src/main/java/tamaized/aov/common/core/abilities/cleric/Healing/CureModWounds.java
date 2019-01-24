package tamaized.aov.common.core.abilities.cleric.Healing;

import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.cleric.CureWounds;

public class CureModWounds extends CureWounds {

	public CureModWounds() {
		super(CureModWounds.getStaticName(), 8, 2, 6);
	}

	public static String getStaticName() {
		return "aov.spells.curemodwounds.name";
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
