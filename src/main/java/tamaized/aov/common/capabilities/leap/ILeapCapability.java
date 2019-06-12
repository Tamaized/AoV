package tamaized.aov.common.capabilities.leap;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;

public interface ILeapCapability {

	ResourceLocation ID = new ResourceLocation(AoV.MODID, "leapcapabilityhandler");

	void update(LivingEntity entity);

	void setLeapDuration(int duration);

	int getLeapDuration();

	int getMaxLeapDuration();

}
