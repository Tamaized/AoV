package Tamaized.AoV.common.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientPacketHandler{
	
	public static final int TYPE_PLACEHOLDER = 0;
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientPacket(ClientCustomPacketEvent event) {
		try {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
			processPacketOnClient(event.packet.payload(), Side.CLIENT);
			bbis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void processPacketOnClient(ByteBuf parBB, Side parSide) throws IOException{
		World theWorld = Minecraft.getMinecraft().theWorld;
		if (parSide == Side.CLIENT){
			ByteBufInputStream bbis = new ByteBufInputStream(parBB);
			int pktType = bbis.readInt();
			if(pktType == TYPE_PLACEHOLDER){
				
			}
			bbis.close();   
		}
	}
}


