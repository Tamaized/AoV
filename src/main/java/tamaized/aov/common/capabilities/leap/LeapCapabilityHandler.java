package tamaized.aov.common.capabilities.leap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tamaized.aov.AoV;
import tamaized.aov.network.client.ClientPacketHandlerLeap;

public class LeapCapabilityHandler implements ILeapCapability {

	private boolean dirty = false;
	private int tick;

	private int leapDuration;
	private int maxLeapDuration = 300;

	@Override
	public void update(EntityLivingBase entity) {
		if (leapDuration-- > 0)
			dirty = true;
		if (dirty && ++tick % 20 == 0)
			sendPacketUpdates(entity);
	}

	@Override
	public int getLeapDuration() {
		return leapDuration;
	}

	@Override
	public void setLeapDuration(int duration) {
		maxLeapDuration = leapDuration <= 0 || maxLeapDuration <= leapDuration ? duration : maxLeapDuration;
		leapDuration = duration;
		dirty = true;
	}

	@Override
	public int getMaxLeapDuration() {
		return maxLeapDuration;
	}

	private void sendPacketUpdates(Entity e) {
		if (!e.world.isRemote)
			AoV.network.sendToAllAround(new ClientPacketHandlerLeap.Packet(e, this), new NetworkRegistry.TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 256));
	}
}
