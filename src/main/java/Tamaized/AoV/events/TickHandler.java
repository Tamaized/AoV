package Tamaized.AoV.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.client.ClientProxy;
import Tamaized.AoV.common.client.ClientTicker;
import Tamaized.AoV.core.AoVCore;
import Tamaized.AoV.core.AoVCoreClient;

public class TickHandler {
	
	@SubscribeEvent
	public void ServerTickEvent(ServerTickEvent e){
		if(e.phase == e.phase.START) return;
		if(AoV.serverAoVCore == null) AoV.serverAoVCore = new AoVCore();
		AoV.serverAoVCore.update();
	}
	
	@SubscribeEvent
	public void ClientTickEvent(ClientTickEvent e){
		if(e.phase == e.phase.START || Minecraft.getMinecraft().isGamePaused()) return;
		if(AoV.clientAoVCore == null) AoV.clientAoVCore = new AoVCoreClient();
		AoV.clientAoVCore.update();
		if(AoV.clientAoVCore.getPlayer(null) == null) ClientProxy.barToggle = false;
		ClientTicker.update();
	}

}
