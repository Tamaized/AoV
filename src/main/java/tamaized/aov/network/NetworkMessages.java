package tamaized.aov.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import tamaized.aov.network.client.ClientPacketHandlerAoVData;
import tamaized.aov.network.client.ClientPacketHandlerAstroAnimation;
import tamaized.aov.network.client.ClientPacketHandlerAstroData;
import tamaized.aov.network.client.ClientPacketHandlerMovingSound;
import tamaized.aov.network.server.ServerPacketHandlerSpellSkill;

public class NetworkMessages {

	private static int index = 0;

	public static void register(SimpleNetworkWrapper network) {
		registerMessage(network, ServerPacketHandlerSpellSkill.class, ServerPacketHandlerSpellSkill.Packet.class, Side.SERVER);

		registerMessage(network, ClientPacketHandlerAoVData.class, ClientPacketHandlerAoVData.Packet.class, Side.CLIENT);
		registerMessage(network, ClientPacketHandlerAstroData.class, ClientPacketHandlerAstroData.Packet.class, Side.CLIENT);
		registerMessage(network, ClientPacketHandlerAstroAnimation.class, ClientPacketHandlerAstroAnimation.Packet.class, Side.CLIENT);
		registerMessage(network, ClientPacketHandlerMovingSound.class, ClientPacketHandlerMovingSound.Packet.class, Side.CLIENT);
	}

	private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(SimpleNetworkWrapper network, Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
		network.registerMessage(messageHandler, requestMessageType, index, side);
		index++;
	}
}
