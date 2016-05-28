package Tamaized.AoV.core.abilities.healer.Healing;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.healer.CureWounds;

public class CureCriticalWounds extends CureWounds{

	public CureCriticalWounds() {
		super(CureCriticalWounds.getStaticName(), 4, 2, 10);
	}

	public static String getStaticName() {
		return "Cure Critical Wounds";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/spells/cureCritWounds.png");
	}
	
	@Override
	public int getCoolDown() {
		return 5;
	}

}
