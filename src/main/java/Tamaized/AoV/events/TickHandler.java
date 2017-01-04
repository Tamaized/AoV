package Tamaized.AoV.events;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class TickHandler {

	private int lastLevel = -1;
	private int lastExp = -1;

	@SubscribeEvent
	public void update(PlayerTickEvent e) {
		if (e.phase == e.phase.START) return;
		IAoVCapability cap = e.player.getCapability(CapabilityList.AOV, null);
		if (cap != null) {
			cap.update(e.player);
			if (e.player.world.isRemote) {
				if (lastLevel < 0) lastLevel = cap.getLevel();
				if (lastExp < 0) lastExp = cap.getExp();
				if (lastExp < cap.getExp()) {
					int amount = cap.getExp() - lastExp;
					Tamaized.AoV.gui.client.AoVOverlay.addFloatyText("+" + amount + " Exp");
					lastExp += amount;
				}
				if (lastLevel < cap.getLevel()) {
					lastLevel++;
					Tamaized.AoV.gui.client.AoVOverlay.addFloatyText("Level Up! (" + lastLevel + ")");
				}
				if (lastExp > cap.getExp()) lastExp = cap.getExp();
				if (lastLevel > cap.getLevel()) lastLevel = cap.getLevel();
			}
		}
	}

}
