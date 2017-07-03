package tamaized.aov.client.events;

import tamaized.aov.common.entity.EntitySpellBladeBarrier;
import tamaized.aov.common.entity.EntitySpellImplosion;
import tamaized.aov.common.entity.ProjectileNimbusRay;
import tamaized.aov.client.sound.EntityMovingSound;
import tamaized.aov.registry.SoundEvents;
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
