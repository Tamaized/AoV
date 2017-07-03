package tamaized.aov.common.events;

import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class TickHandler {

	@SubscribeEvent
	public void update(PlayerTickEvent e) {
		if (e.phase == e.phase.START)
			return;
		EntityPlayer player = e.player;
		if (player.getHealth() <= (player.getMaxHealth() / 2) && player.getActivePotionEffect(AoV.potions.stalwartPact) != null) {
			player.removeActivePotionEffect(AoV.potions.stalwartPact);
			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, (20 * (60 * 5)), 2));
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, (20 * (10)), 2));
		}
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap != null) {
			cap.update(player);
		}
	}

}
