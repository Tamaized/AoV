package Tamaized.AoV.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVCore;
import Tamaized.AoV.core.AoVData;

/**
 * 
 * The Client Side and Server Side are two different instances!!!
 *
 */
public class PlayerJoinWorld {
	
	@SubscribeEvent
	public void join(EntityJoinWorldEvent e){
		System.out.println(e.entity);
		if(!e.world.isRemote){ //ServerSide
			if(AoV.serverAoVCore == null) return;
			AoVCore core = AoV.serverAoVCore;
			if(e.entity instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer) e.entity;
				//TODO: file nbt checks for the player, if player has saved data then load it and pass it to their client; else create new data instance
				AoVData dat = new AoVData(player).Construct();
				core.setPlayer(player, dat);
				System.out.println("Test");
			}
		}else{ //ClientSide
			if(AoV.clientAoVCore == null) return;
		}
	}

}
