package Tamaized.AoV.core.abilities.healer.Healing;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.healer.CureWounds;

public class CureModWounds extends CureWounds{

	public CureModWounds() {
		super(getStaticName(), 6, 2, 6);
	}

	public static String getStaticName() {
		return "Cure Moderate Wounds";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/spells/cureModWounds.png");
	}

}
