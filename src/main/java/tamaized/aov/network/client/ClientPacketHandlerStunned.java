package tamaized.aov.network.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.network.NetworkMessages;

public class ClientPacketHandlerStunned implements NetworkMessages.IMessage<ClientPacketHandlerStunned> {

	private int entityID;
	private int stun;

	public ClientPacketHandlerStunned(Entity entity, IStunCapability cap) {
		entityID = entity.getEntityId();
		stun = cap.getStunTicks();
	}

	@Override
	public void handle(EntityPlayer player) {
		Entity e = player.world.getEntityByID(entityID);
		if (e != null) {
			IStunCapability cap = CapabilityList.getCap(e, CapabilityList.STUN);
			if (cap != null)
				cap.setStunTicks(stun);
		}
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(entityID);
		packet.writeInt(stun);
	}

	@Override
	public ClientPacketHandlerStunned fromBytes(PacketBuffer packet) {
		entityID = packet.readInt();
		stun = packet.readInt();
		return this;
	}
}
