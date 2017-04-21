package Tamaized.AoV.helper;

import java.io.IOException;

import Tamaized.AoV.AoV;
import Tamaized.AoV.network.ClientPacketHandler;
import Tamaized.TamModized.helper.PacketHelper;
import Tamaized.TamModized.helper.PacketHelper.PacketWrapper;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class FloatyTextHelper {

	public static void sendText(EntityPlayer player, String text) {
		if (player == null || player.world == null || player.world.isRemote) {
			Tamaized.AoV.gui.client.AoVOverlay.addFloatyText(text);
		} else {
			try {
				encode(player, text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void encode(EntityPlayer e, String text) throws IOException {
		if (!(e instanceof EntityPlayerMP)) return;
		EntityPlayerMP player = (EntityPlayerMP) e;
		PacketWrapper packet = PacketHelper.createPacket(AoV.channel, AoV.networkChannelName, ClientPacketHandler.getPacketTypeID(ClientPacketHandler.PacketType.FloatyText));
		packet.getStream().writeUTF(text);
		packet.sendPacket(player);
	}

	public static void decode(ByteBufInputStream bbis) throws IOException {
		sendText(null, bbis.readUTF());
	}

}
