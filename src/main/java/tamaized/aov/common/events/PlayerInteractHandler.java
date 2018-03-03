package tamaized.aov.common.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.config.ConfigHandler;

public class PlayerInteractHandler {

	@SubscribeEvent
	public void onXPGain(PlayerPickupXpEvent e) {
		if (!ConfigHandler.experience)
			return;
		EntityPlayer player = e.getEntityPlayer();
		if (player != null) {
			IAoVCapability cap = player.hasCapability(CapabilityList.AOV, null) ? player.getCapability(CapabilityList.AOV, null) : null;
			if (cap != null)
				cap.addExp(player, e.getOrb().getXpValue(), null);
		}
	}

	@SubscribeEvent
	public void onWakeUp(PlayerWakeUpEvent e){
		EntityPlayer player = e.getEntityPlayer();
		if (player != null && e.shouldSetSpawn()) {
			IAoVCapability cap = player.hasCapability(CapabilityList.AOV, null) ? player.getCapability(CapabilityList.AOV, null) : null;
			if (cap != null)
				cap.resetCharges(player);
		}
	}

}
