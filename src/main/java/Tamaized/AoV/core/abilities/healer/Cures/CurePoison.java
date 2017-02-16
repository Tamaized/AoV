package Tamaized.AoV.core.abilities.healer.Cures;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.healer.CureEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class CurePoison extends CureEffect {

	public CurePoison() {
		super("Cure Poison", 6, 2, MobEffects.POISON);
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public int getCoolDown() {
		return 10;
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid + ":textures/spells/curepoison.png");
	}

}
