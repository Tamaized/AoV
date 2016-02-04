package Tamaized.AoV.core;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;


public class AoVCoreClient extends AoVCore{
	
	private AoVData data;
	
	public AoVCoreClient(){
		super();
	}
	
	@Override
	public void setPlayer(UUID player, AoVData dat){
		data = dat;
	}
	
	@Override
	public void removePlayer(UUID player){
		data = null;
	}
	
	@Override
	public AoVData getPlayer(UUID player){
		return data;
	}
	
	@Override
	public void update(){
		
	}

}
