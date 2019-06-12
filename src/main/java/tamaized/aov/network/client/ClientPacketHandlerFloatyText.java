package tamaized.aov.network.client;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.helper.FloatyTextHelper;
import tamaized.aov.network.NetworkMessages;

public class ClientPacketHandlerFloatyText implements NetworkMessages.IMessage<ClientPacketHandlerFloatyText> {

	String text;

	public ClientPacketHandlerFloatyText(String text) {
		this.text = text;
	}

	@Override
	public void handle(PlayerEntity player) {
		FloatyTextHelper.sendText(player, text);
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeString(text);
	}

	@Override
	public ClientPacketHandlerFloatyText fromBytes(PacketBuffer packet) {
		text = packet.readString(Short.MAX_VALUE);
		return this;
	}
}
