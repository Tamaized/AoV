package Tamaized.AoV.events;

import Tamaized.AoV.entity.EntitySpellBladeBarrier;
import Tamaized.AoV.entity.EntitySpellImplosion;
import Tamaized.AoV.entity.projectile.caster.ProjectileNimbusRay;
import Tamaized.AoV.sound.EntityMovingSound;
import Tamaized.AoV.sound.SoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientSpawnEvent {

	@SubscribeEvent
	public void spawn(EntityJoinWorldEvent e) {
		if (e.getWorld().isRemote) {
			if (e.getEntity() instanceof EntitySpellBladeBarrier) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new EntityMovingSound(SoundEvents.bladebarrier, SoundCategory.NEUTRAL, e.getEntity(), true, 0));
			} else if (e.getEntity() instanceof EntitySpellImplosion) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new EntityMovingSound(SoundEvents.implosion, SoundCategory.NEUTRAL, e.getEntity(), false, 0));
			} else if (e.getEntity() instanceof ProjectileNimbusRay) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new EntityMovingSound(SoundEvents.cast, SoundCategory.NEUTRAL, e.getEntity(), false, 0));
			}
		}
	}

}
