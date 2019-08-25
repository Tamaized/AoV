package tamaized.aov.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tamaized.aov.AoV;
import tamaized.aov.client.sound.EntityMovingSound;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.common.entity.EntitySpellBladeBarrier;
import tamaized.aov.common.entity.EntitySpellImplosion;
import tamaized.aov.common.entity.ProjectileNimbusRay;
import tamaized.aov.registry.SoundEvents;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Dist.CLIENT)
public class ClientSpawnEvent {

	@Deprecated
	@SubscribeEvent
	public static void spawn(EntityJoinWorldEvent e) {
		if (e.getWorld().isRemote) {
			if (e.getEntity() instanceof EntitySpellBladeBarrier) {
				Minecraft.getInstance().getSoundHandler().play(new EntityMovingSound(SoundEvents.bladebarrier, SoundCategory.NEUTRAL, e.getEntity(), true, 0, 1, 1));
			} else if (e.getEntity() instanceof EntitySpellImplosion) {
				Minecraft.getInstance().getSoundHandler().play(new EntityMovingSound(SoundEvents.implosion, SoundCategory.NEUTRAL, e.getEntity(), false, 0, 1, 1));
			} else if (e.getEntity() instanceof ProjectileNimbusRay) {
				Minecraft.getInstance().getSoundHandler().play(new EntityMovingSound(SoundEvents.cast, SoundCategory.NEUTRAL, e.getEntity(), false, 0, 1, 1));
			}
		}
	}

	@SubscribeEvent
	public static void update(TickEvent.ClientTickEvent e) {
		if (e.phase == TickEvent.Phase.START || Minecraft.getInstance().world == null)
			return;
		for (Entity entity : Minecraft.getInstance().world.getAllEntities()) {
			if (!(entity instanceof LivingEntity))
				continue;
			IStunCapability cap = CapabilityList.getCap(entity, CapabilityList.STUN);
			if (cap != null)
				cap.update((LivingEntity) entity);
		}
	}

}
