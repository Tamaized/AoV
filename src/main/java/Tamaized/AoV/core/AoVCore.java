package Tamaized.AoV.core;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

public class AoVCore {
	
	private Map<EntityPlayer, AoVData> players;
	private AoVData defaultData;
	
	public AoVCore(){
		players = new HashMap<EntityPlayer, AoVData>();
		defaultData = new AoVData();
	}
	
	public void setPlayer(EntityPlayer player, AoVData dat){
		if(dat != null){
			players.put(player, dat);
		}else{
			players.put(player, defaultData);
		}
	}
	
	public void removePlayer(EntityPlayer player){
		if(players.containsKey(player)) players.remove(player);
	}
	
	public AoVData getPlayer(EntityPlayer player){
		return players.get(player);
	}

}
