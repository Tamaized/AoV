package Tamaized.AoV.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class TickHandler {

	@SubscribeEvent
	public void update(PlayerTickEvent e) {
		if (e.phase == e.phase.START) return;
		IAoVCapability cap = e.player.getCapability(CapabilityList.AOV, null);
		if (cap != null) {
			cap.update(e.player);
		}
	}

}
