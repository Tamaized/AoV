package tamaized.aov.network.server;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.network.NetworkMessages;

public class ServerPacketHandlerPolymorphDogAttack implements NetworkMessages.IMessage<ServerPacketHandlerPolymorphDogAttack> {

	@Override
	public void handle(PlayerEntity player) {
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null)
			cap.doAttack(player, false);
	}

	@Override
	public void toBytes(PacketBuffer packet) {

	}

	@Override
	public ServerPacketHandlerPolymorphDogAttack fromBytes(PacketBuffer packet) {
		return this;
	}
}
