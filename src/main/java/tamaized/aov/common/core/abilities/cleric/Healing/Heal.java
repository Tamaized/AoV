package tamaized.aov.common.core.abilities.cleric.Healing;

import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.cleric.CureWounds;

public class Heal extends CureWounds {

	public Heal() {
		super(Heal.getStaticName(), 2, 10, 20);
	}

	public static String getStaticName() {
		return "aov.spells.heal.name";
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
