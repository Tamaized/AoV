package Tamaized.AoV.events;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.handlers.ClientPacketHandler;
import Tamaized.AoV.core.AoVCore;
import Tamaized.AoV.core.AoVData;

/**
 * 
 * The Client Side and Server Side are two different instances!!!
 *
 */
public class PlayerJoinLeaveEvent {
	
	@SubscribeEvent
	public void join(PlayerLoggedInEvent e){
		System.out.println("LOGGED IN");
		if(AoV.serverAoVCore == null) return;
		System.out.println("serverAoVCore NOT NULL; Starting setup");
		AoVCore core = AoV.serverAoVCore;
		//TODO: file nbt checks for the player, if player has saved data then load it and pass it to their client; else create new data instance
		AoVData dat = new AoVData(e.player.getPersistentID()).Construct();
		core.setPlayer(e.player.getPersistentID(), dat);	
		sendPacketToClient((EntityPlayerMP) e.player, dat.toPacket());
	}
	
	@SubscribeEvent
	public void leave(PlayerLoggedOutEvent e){
		if(AoV.serverAoVCore == null) return;
		AoVCore core = AoV.serverAoVCore;
		//TODO: save data to NBT file
		AoVData dat = core.getPlayer(e.player.getPersistentID());
		core.removePlayer(e.player.getPersistentID());
		System.out.println("LOGGED OUT");	
	}
	
	private void sendPacketToClient(EntityPlayerMP player, String packet){
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ClientPacketHandler.TYPE_COREDATA);
			outputStream.writeUTF(packet);
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			if(AoV.channel != null && pkt != null) AoV.channel.sendTo(pkt, player);
			bos.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
