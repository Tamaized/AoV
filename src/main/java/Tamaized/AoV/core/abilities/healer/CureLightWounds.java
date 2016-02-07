package Tamaized.AoV.core.abilities.healer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import Tamaized.AoV.core.abilities.AbilityBase;

public class CureLightWounds extends AbilityBase {

	public CureLightWounds(int c, double d) {
		super(c, d);
	}

	@Override
	protected void doAction(EntityPlayer player, Entity e) {
		
	}

	@Override
	public String getName() {
		return "Cure Light Wounds";
	}

}
