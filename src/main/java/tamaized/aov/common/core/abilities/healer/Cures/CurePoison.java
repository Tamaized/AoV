package tamaized.aov.common.core.abilities.healer.Cures;

import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.healer.CureEffect;

public class CurePoison extends CureEffect {

	public CurePoison() {
		super("aov.spells.curepoison.name", 6, 2, MobEffects.POISON);
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
		return new ResourceLocation(AoV.modid, "textures/spells/curepoison.png");
	}

	@Override
	protected int getParticleColor() {
		return 0x68FF77FF;
	}

}
