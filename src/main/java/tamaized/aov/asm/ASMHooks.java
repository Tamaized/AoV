package tamaized.aov.asm;

import net.minecraft.entity.Entity;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;

public class ASMHooks {

	/**
	 * Injection Point:<br>
	 * {@link net.minecraft.entity.Entity#isImmuneToFire}<br>
	 * [BEFORE] IRETURN
	 */
	public static boolean isImmuneToFire(boolean value, Entity entity) {
		if (value)
			return true;
		IPolymorphCapability cap = CapabilityList.getCap(entity, CapabilityList.POLYMORPH);
		return cap != null && cap.getMorph() == IPolymorphCapability.Morph.FireElemental;
	}

}
