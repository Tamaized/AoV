package Tamaized.AoV.capabilities;

import Tamaized.AoV.capabilities.aov.IAoVCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityList {
	
	@CapabilityInject(IAoVCapability.class)
	public static final Capability<IAoVCapability> AOV = null;

}
