package tamaized.aov.common.capabilities.leap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;

public interface ILeapCapability {

	ResourceLocation ID = new ResourceLocation(AoV.MODID, "LeapCapabilityHandler");

	void update(EntityLivingBase entity);

	void setLeapDuration(int duration);

	int getLeapDuration();

	int getMaxLeapDuration();

}
