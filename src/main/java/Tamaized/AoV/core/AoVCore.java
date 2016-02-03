package Tamaized.AoV.core;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

public class AoVCore {
	
	private Map<EntityPlayer, AovData> players;
	
	public AoVCore(){
		players = new HashMap<EntityPlayer, AovData>();
	}
	
	public void addPlayer(EntityPlayer player){
		
	}

}
