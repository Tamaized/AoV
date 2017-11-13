package tamaized.aov.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tamaized.aov.client.sound.EntityMovingSound;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.common.entity.EntitySpellBladeBarrier;
import tamaized.aov.common.entity.EntitySpellImplosion;
import tamaized.aov.common.entity.ProjectileNimbusRay;
import tamaized.aov.registry.SoundEvents;

public class ClientSpawnEvent {

	@Deprecated
	@SubscribeEvent
	public void spawn(EntityJoinWorldEvent e) {
		if (e.getWorld().isRemote) {
			if (e.getEntity() instanceof EntitySpellBladeBarrier) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new EntityMovingSound(SoundEvents.bladebarrier, SoundCategory.NEUTRAL, e.getEntity(), true, 0, 1, 1));
			} else if (e.getEntity() instanceof EntitySpellImplosion) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new EntityMovingSound(SoundEvents.implosion, SoundCategory.NEUTRAL, e.getEntity(), false, 0, 1, 1));
			} else if (e.getEntity() instanceof ProjectileNimbusRay) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new EntityMovingSound(SoundEvents.cast, SoundCategory.NEUTRAL, e.getEntity(), false, 0, 1, 1));
			}
		}
	}

	@SubscribeEvent
	public void update(TickEvent.ClientTickEvent e) {
		if (e.phase == TickEvent.Phase.START || Minecraft.getMinecraft().world == null)
			return;
		for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
			if (!(entity instanceof EntityLivingBase))
				continue;
			IStunCapability cap = entity.hasCapability(CapabilityList.STUN, null) ? entity.getCapability(CapabilityList.STUN, null) : null;
			if (cap != null)
				cap.update((EntityLivingBase) entity);
		}
	}

}
