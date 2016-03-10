package Tamaized.AoV.core.abilities.healer.Healing;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.healer.CureWounds;

public class CureSeriousWounds extends CureWounds{

	public CureSeriousWounds() {
		super(CureSeriousWounds.getStaticName(), 8, 2, 8);
	}

	public static String getStaticName() {
		return "Cure Serious Wounds";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/spells/cureSeriousWounds.png");
	}

	@Override
	public int getCoolDown() {
		return 4;
	}

}
