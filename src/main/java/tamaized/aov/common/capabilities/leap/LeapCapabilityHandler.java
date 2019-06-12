package tamaized.aov.common.capabilities.leap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.network.PacketDistributor;
import tamaized.aov.AoV;
import tamaized.aov.network.client.ClientPacketHandlerLeap;

public class LeapCapabilityHandler implements ILeapCapability {

	private boolean dirty = false;
	private int tick;

	private int leapDuration;
	private int maxLeapDuration = 300;

	@Override
	public void update(LivingEntity entity) {
		leapDuration--;
		if (dirty || ++tick % 20 == 0)
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
			AoV.network.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e), new ClientPacketHandlerLeap(e, this));
	}
}
