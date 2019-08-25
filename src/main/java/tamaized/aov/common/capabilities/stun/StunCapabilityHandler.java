package tamaized.aov.common.capabilities.stun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.network.PacketDistributor;
import tamaized.aov.AoV;
import tamaized.aov.network.client.ClientPacketHandlerStunned;

public class StunCapabilityHandler implements IStunCapability {

	private int stunTicks;
	private double mx, my, mz;

	@Override
	public int getStunTicks() {
		return stunTicks;
	}

	@Override
	public void setStunTicks(int ticks) {
		stunTicks = ticks;
	}

	@Override
	public void update(LivingEntity entity) {
		boolean canUpdate = entity.canUpdate();
		if (stunTicks-- > 0) {
			if (canUpdate) {
				entity.canUpdate(false);
				sendPacketUpdates(entity);
			}
			if (!entity.isAlive()) {
				stunTicks = 0;
				entity.canUpdate(true);
			}
			if (entity.hurtTime > 0)
				entity.hurtTime--;
			if (entity.hurtResistantTime > 0)
				entity.hurtResistantTime--;
		} else if (!canUpdate) {
			entity.canUpdate(true);
			sendPacketUpdates(entity);
		}
	}

	private void sendPacketUpdates(Entity e) {
		if (!e.canUpdate()) {
			mx = e.getMotion().x;
			my = e.getMotion().y;
			mz = e.getMotion().z;
			e.setMotion(0, 0, 0);
		} else {
			e.setMotion(mx, my, mz);
		}
		if (!e.world.isRemote)
			AoV.network.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e), new ClientPacketHandlerStunned(e, this));
	}
}
