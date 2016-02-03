package Tamaized.AoV.events;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVCore;
import Tamaized.AoV.core.AoVCoreClient;

public class TickHandler {
	
	@SubscribeEvent
	public void WorldTick(WorldTickEvent e){
		World world = e.world;
		if(world.isRemote){
			if(AoV.clientAoVCore == null) AoV.clientAoVCore = new AoVCoreClient();
			AoV.clientAoVCore.update();
		}else{
			if(AoV.serverAoVCore == null) AoV.serverAoVCore = new AoVCore();
			AoV.serverAoVCore.update();
		}
	}

}
