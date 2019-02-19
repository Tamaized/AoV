package tamaized.aov.network.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.network.NetworkMessages;

public class ClientPacketHandlerPolymorphDogAttack implements NetworkMessages.IMessage<ClientPacketHandlerPolymorphDogAttack> {

	@Override
	public void handle(EntityPlayer player) {
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null)
			cap.doAttack(player, true);
	}

	@Override
	public void toBytes(PacketBuffer packet) {

	}

	@Override
	public ClientPacketHandlerPolymorphDogAttack fromBytes(PacketBuffer packet) {
		return this;
	}


}
