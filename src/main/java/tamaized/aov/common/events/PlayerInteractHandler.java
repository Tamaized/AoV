package tamaized.aov.common.events;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;

@Mod.EventBusSubscriber(modid = AoV.MODID)
public class PlayerInteractHandler {

	@SubscribeEvent
	public static void onXPGain(PlayerXpEvent.PickupXp e) {
		if (!AoV.config.experience.get())
			return;
		PlayerEntity player = e.getPlayer();
		if (player != null) {
			IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
			if (cap != null)
				cap.addExp(player, e.getOrb().getXpValue(), null);
		}
	}

	@SubscribeEvent
	public static void onItemPickup(EntityItemPickupEvent e) {
		IPolymorphCapability cap = CapabilityList.getCap(e.getPlayer(), CapabilityList.POLYMORPH);
		if (cap != null && cap.getMorph() == IPolymorphCapability.Morph.Wolf && e.getPlayer().getFoodStats().needFood() && e.getItem().getItem().isFood()) {
			e.getItem().getItem().getItem().onItemUseFinish(e.getItem().getItem(), e.getPlayer().world, e.getPlayer());
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onWakeUp(PlayerWakeUpEvent e) {
		// ServerWorld#wakeAllPlayers is the only vanilla method that passes (false, false)
		// ForgeEventFactory.fireSleepingTimeCheck passes (false, true), lets ensure the time is day when we check this
		PlayerEntity player = e.getPlayer();
		if (player != null) {
			boolean flag = !e.wakeImmediately() && !e.updateWorld();
			if (!flag) {
				if (!e.wakeImmediately() && e.updateWorld()) {
					flag = player.world.isDaytime();
				}
			}
			if (flag) {
				IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV, null);
				if (cap != null)
					cap.resetCharges(player);
			}
		}
	}

}
