package tamaized.aov.common.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.registry.AoVPotions;

import java.util.Iterator;

public class TickHandler {

	@SubscribeEvent
	public void update(PlayerTickEvent e) {
		if (e.phase == TickEvent.Phase.START)
			return;
		EntityPlayer player = e.player;
		if (player.getHealth() <= (player.getMaxHealth() / 2) && player.getActivePotionEffect(AoVPotions.stalwartPact) != null) {
			player.removeActivePotionEffect(AoVPotions.stalwartPact);
			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, (20 * (60 * 5)), 2));
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, (20 * (10)), 2));
		}
		if (player.hasCapability(CapabilityList.AOV, null)) {
			IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
			if (cap != null)
				cap.update(player);
		}
		if (player.hasCapability(CapabilityList.ASTRO, null)) {
			IAstroCapability cap = player.getCapability(CapabilityList.ASTRO, null);
			if (cap != null)
				cap.update(player);
		}
	}

	@SubscribeEvent
	@SuppressWarnings("ForLoopReplaceableByForEach")
	public void updateEntity(TickEvent.WorldTickEvent e) {
		if (e.phase == TickEvent.Phase.START)
			return;
		for (Iterator<Entity> iter = e.world.loadedEntityList.iterator(); iter.hasNext();) {
			Entity entity = iter.next();
			if (!(entity instanceof EntityLivingBase))
				continue;
			IStunCapability cap = entity.hasCapability(CapabilityList.STUN, null) ? entity.getCapability(CapabilityList.STUN, null) : null;
			if (cap != null)
				cap.update((EntityLivingBase) entity);
		}
	}

}
