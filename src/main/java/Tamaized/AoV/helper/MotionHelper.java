package Tamaized.AoV.helper;

import java.io.DataOutputStream;
import java.io.IOException;

import Tamaized.AoV.AoV;
import Tamaized.AoV.network.ClientPacketHandler;
import Tamaized.TamModized.helper.PacketHelper;
import Tamaized.TamModized.helper.PacketHelper.PacketWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MotionHelper { //TODO: this goes into TamModized

	public static void addMotion(Entity e, double x, double y, double z) {
		if (e.world.isRemote) return;
		if (e instanceof EntityPlayerMP) sendPacketToPlayer((EntityPlayerMP) e, x, y, z);
		else e.addVelocity(x, y, z);
	}

	private static void sendPacketToPlayer(EntityPlayerMP e, double x, double y, double z) {
		try {
			PacketWrapper packet = PacketHelper.createPacket(AoV.channel, AoV.networkChannelName, ClientPacketHandler.getPacketTypeID(ClientPacketHandler.PacketType.PLAYER_MOTION));
			DataOutputStream stream = packet.getStream();
			stream.writeDouble(x);
			stream.writeDouble(y);
			stream.writeDouble(z);
			packet.sendPacket(e);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void updatePlayerMotion(double x, double y, double z) {
		net.minecraft.client.Minecraft.getMinecraft().player.addVelocity(x, y, z);
	}

}