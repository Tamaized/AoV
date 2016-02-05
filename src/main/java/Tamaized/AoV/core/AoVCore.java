package Tamaized.AoV.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Tamaized.AoV.common.handlers.ServerPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class AoVCore {
	
	private Map<EntityPlayer, AoVData> players;
	private AoVData defaultData;
	
	public AoVCore(){
		players = new HashMap<EntityPlayer, AoVData>();
		defaultData = new AoVData().Construct();
	}
	
	public void setPlayer(EntityPlayer player, AoVData dat){
		if(dat != null){
			players.put(player, dat);
		}else{
			players.put(player, defaultData);
		}
		System.out.println(players);
	}
	
	public void removePlayer(EntityPlayer player){
		if(players.containsKey(player)) players.remove(player);
	}
	
	public AoVData getPlayer(EntityPlayer player){
		return players.get(player);
	}
	
	public void update(){
		Iterator<AoVData> iter = players.values().iterator();
		while(iter.hasNext()){
			AoVData data = iter.next();
			data.update();
			if(data.wasThereAChange() && data.getPlayer() instanceof EntityPlayerMP) ServerPacketHandler.sendAovDataPacket((EntityPlayerMP) data.getPlayer(), data);
		}
	}

}
