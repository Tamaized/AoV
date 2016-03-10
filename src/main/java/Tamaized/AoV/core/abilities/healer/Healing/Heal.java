package Tamaized.AoV.core.abilities.healer.Healing;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.healer.CureWounds;

public class Heal extends CureWounds{

	public Heal() {
		super(Heal.getStaticName(), 20, 10, 20);
	}

	public static String getStaticName() {
		return "Heal";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/spells/Heal.png");
	}
	
	@Override
	public int getCoolDown() {
		return 6;
	}

}
