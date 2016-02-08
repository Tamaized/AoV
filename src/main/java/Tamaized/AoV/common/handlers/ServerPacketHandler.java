package Tamaized.AoV.common.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVCore;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;

public class ServerPacketHandler {
	
	public static final int TYPE_SKILLEDIT_CHECK_CANOBTAIN = 0;
	public static final int TYPE_RESETSKILLS_FULL = 1;
	public static final int TYPE_RESETSKILLS_MINOR = 2;
	public static final int TYPE_SPELLCAST_SELF = 3;
	public static final int TYPE_SPELLCAST_TARGET = 4;
	
	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event) {
		try{
			EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
			ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
			
			processPacketOnServer(event.packet.payload(), Side.SERVER, player);
			
			bbis.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void processPacketOnServer(ByteBuf parBB, Side parSide, EntityPlayerMP player) throws IOException{
	  
		if(parSide == Side.SERVER){
			ByteBufInputStream bbis = new ByteBufInputStream(parBB);
			int pktType = bbis.readInt();
			switch(pktType){
				
				case TYPE_SKILLEDIT_CHECK_CANOBTAIN:
				{
					String theName = bbis.readUTF();
					AoVCore core = AoV.serverAoVCore;
					AoVData data = core.getPlayer(player);
					AoVSkill skillToCheck = AoVSkill.getSkillFromName(theName);
					boolean flag = false;
					if(skillToCheck == null) break;
					if(skillToCheck.parent == null) if(data.getCurrentSkillPoints() >= skillToCheck.pointCost) flag = true;
					else if(data.hasSkill(skillToCheck.parent) && data.getCurrentSkillPoints() >= skillToCheck.pointCost) flag = true;
					if(flag){
						System.out.println("Server passed on canObtain, sending data to client");
						data.addObtainedSkill(skillToCheck);
						data.setCurrentSkillPoints(data.getCurrentSkillPoints()-skillToCheck.pointCost);
						data.updateVariables();
						sendAovDataPacket(player, data);
					}
				}
					break;
					
				case TYPE_RESETSKILLS_FULL:
				{
					AoVCore core = AoV.serverAoVCore;
					AoVData data = new AoVData(player).Construct();
					core.removePlayer(player);
					core.setPlayer(player, data);
					sendAovDataPacket(player, data);
				}
					break;
					
				case TYPE_RESETSKILLS_MINOR:
				{
					
				}
					break;
					
				case TYPE_SPELLCAST_SELF:
				{
					AoVCore core = AoV.serverAoVCore;
					AoVData data = core.getPlayer(player);
					AbilityBase spell = AbilityBase.fromName(bbis.readUTF());
					if(spell == null) break;
					spell.activate(player, data, null);
				}
					break;
					
				case TYPE_SPELLCAST_TARGET:
				{
					AoVCore core = AoV.serverAoVCore;
					AoVData data = core.getPlayer(player);
					AbilityBase spell = AbilityBase.fromName(bbis.readUTF());
					Entity entity = player.worldObj.getEntityByID(bbis.readInt());
					if(spell == null || !(entity instanceof EntityLivingBase)) break;
					spell.activate(player, data, (EntityLivingBase) entity);
				}
					break;
					
				default:
					break;
			}
			bbis.close();
		}
	}
	
	public static void sendAovDataPacket(EntityPlayerMP player, AoVData packet){
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ClientPacketHandler.TYPE_COREDATA);
			outputStream.writeUTF(packet.toPacket());
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			if(AoV.channel != null && pkt != null) AoV.channel.sendTo(pkt, player);
			bos.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
}
