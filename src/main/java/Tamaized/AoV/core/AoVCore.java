package Tamaized.AoV.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

public class AoVCore {
	
	private Map<UUID, AoVData> players;
	private AoVData defaultData;
	
	public AoVCore(){
		players = new HashMap<UUID, AoVData>();
		defaultData = new AoVData().Construct();
	}
	
	public void setPlayer(UUID player, AoVData dat){
		if(dat != null){
			players.put(player, dat);
		}else{
			players.put(player, defaultData);
		}
	}
	
	public void removePlayer(UUID player){
		if(players.containsKey(player)) players.remove(player);
	}
	
	public AoVData getPlayer(UUID player){
		return players.get(player);
	}
	
	public void update(){
		
	}

}
