package Tamaized.AoV.core.abilities;

import Tamaized.AoV.capabilities.aov.IAoVCapability;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AuraBase {
	
	public abstract void update(IAoVCapability cap, EntityPlayer player);

	public abstract int getCurrentLife();

}
