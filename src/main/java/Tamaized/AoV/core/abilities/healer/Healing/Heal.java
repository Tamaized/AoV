package Tamaized.AoV.core.abilities.healer.Healing;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.healer.CureWounds;
import net.minecraft.util.ResourceLocation;

public class Heal extends CureWounds {

	public Heal() {
		super(Heal.getStaticName(), 2, 10, 20);
	}

	public static String getStaticName() {
		return "Heal";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid, "textures/spells/heal.png");
	}

	@Override
	public int getCoolDown() {
		return 6;
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	protected int getParticleColor() {
		return 0xFF8484FF;
	}

}
