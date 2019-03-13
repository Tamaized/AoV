package tamaized.aov.common.capabilities.stun;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;

public interface IStunCapability {

	ResourceLocation ID = new ResourceLocation(AoV.MODID, "stuncapabilityhandler");

	int getStunTicks();

	void setStunTicks(int ticks);

	void update(EntityLivingBase entity);
}
