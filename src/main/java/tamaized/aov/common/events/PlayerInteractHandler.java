package tamaized.aov.common.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class PlayerInteractHandler {

	@SubscribeEvent
	public void onXPGain(PlayerPickupXpEvent e) {
		if (!ConfigHandler.experience)
			return;
		EntityPlayer player = e.getEntityPlayer();
		if (player != null) {
			IAoVCapability cap = CapabilityHelper.getCap(player, CapabilityList.AOV, null);
			if (cap != null)
				cap.addExp(player, e.getOrb().getXpValue(), null);
		}
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent e) {
		IPolymorphCapability cap = CapabilityHelper.getCap(e.getEntityPlayer(), CapabilityList.POLYMORPH, null);
		if (cap != null && cap.getMorph() == IPolymorphCapability.Morph.Wolf && e.getEntityPlayer().getFoodStats().needFood() && e.getItem().getItem().getItem() instanceof ItemFood) {
			e.getItem().getItem().getItem().onItemUseFinish(e.getItem().getItem(), e.getEntityPlayer().world, e.getEntityPlayer());
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onWakeUp(PlayerWakeUpEvent e) { // WorldServer#wakeAllPlayers is the only vanilla method that passes (false, false, true)
		EntityPlayer player = e.getEntityPlayer();
		if (player != null && !e.wakeImmediately() && !e.updateWorld() && e.shouldSetSpawn()) {
			IAoVCapability cap = CapabilityHelper.getCap(player, CapabilityList.AOV, null);
			if (cap != null)
				cap.resetCharges(player);
		}
	}

}
