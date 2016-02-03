package Tamaized.AoV.common.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerPacketHandler {
	
	public static final int TYPE_PLACEHOLDER = 0;
	
	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event) {
		try {
			
		EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
		ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
		
		processPacketOnServer(event.packet.payload(), Side.SERVER, player);
		
		bbis.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void processPacketOnServer(ByteBuf parBB, Side parSide, EntityPlayerMP player){
	  
		if(parSide == Side.SERVER){
			ByteBufInputStream bbis = new ByteBufInputStream(parBB);
			int pktType;
			try {
				pktType = bbis.readInt();
				if(pktType == TYPE_PLACEHOLDER){
					
				}
			 }catch(Exception e){
				 try {
					bbis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				 e.printStackTrace();
			 }
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
