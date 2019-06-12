package tamaized.aov.network.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.aov.network.NetworkMessages;

public class ClientPacketHandlerLeap implements NetworkMessages.IMessage<ClientPacketHandlerLeap> {

	private int entityID;
	private int duration;

	public ClientPacketHandlerLeap(Entity entity, ILeapCapability cap) {
		entityID = entity.getEntityId();
		duration = cap.getLeapDuration();
	}

	@Override
	public void handle(PlayerEntity player) {
		Entity e = player.world.getEntityByID(entityID);
		if (e != null) {
			ILeapCapability cap = CapabilityList.getCap(e, CapabilityList.LEAP);
			if (cap != null)
				cap.setLeapDuration(duration);
		}
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(entityID);
		packet.writeInt(duration);
	}

	@Override
	public ClientPacketHandlerLeap fromBytes(PacketBuffer packet) {
		entityID = packet.readInt();
		duration = packet.readInt();
		return this;
	}
}
