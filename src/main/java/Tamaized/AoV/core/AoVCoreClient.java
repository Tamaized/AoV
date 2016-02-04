package Tamaized.AoV.core;

import net.minecraft.entity.player.EntityPlayer;


public class AoVCoreClient extends AoVCore{
	
	private AoVData data;
	
	public AoVCoreClient(){
		super();
	}
	
	@Override
	public void setPlayer(EntityPlayer player, AoVData dat){
		data = dat;
	}
	
	@Override
	public void removePlayer(EntityPlayer player){
		data = null;
	}
	
	@Override
	public AoVData getPlayer(EntityPlayer player){
		return data;
	}
	
	@Override
	public void update(){
		data.update();
	}

}
