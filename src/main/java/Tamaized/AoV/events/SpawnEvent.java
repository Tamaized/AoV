package Tamaized.AoV.events;

import Tamaized.AoV.entity.EntitySpellBladeBarrier;
import Tamaized.AoV.entity.EntitySpellImplosion;
import Tamaized.AoV.entity.projectile.caster.ProjectileFlameStrike;
import Tamaized.AoV.entity.projectile.caster.ProjectileNimbusRay;
import Tamaized.AoV.sound.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpawnEvent {

	@SubscribeEvent
	public void spawn(EntityJoinWorldEvent e) {
		if (e.getWorld().isRemote) {
			if (e.getEntity() instanceof EntitySpellBladeBarrier) {
				net.minecraft.client.Minecraft.getMinecraft().getSoundHandler().playSound(new Tamaized.AoV.sound.EntityMovingSound(SoundEvents.bladebarrier, SoundCategory.NEUTRAL, e.getEntity(), true, 0));
			}else if (e.getEntity() instanceof EntitySpellImplosion) {
				net.minecraft.client.Minecraft.getMinecraft().getSoundHandler().playSound(new Tamaized.AoV.sound.EntityMovingSound(SoundEvents.implosion, SoundCategory.NEUTRAL, e.getEntity(), false, 0));
			}else if (e.getEntity() instanceof ProjectileNimbusRay) {
				net.minecraft.client.Minecraft.getMinecraft().getSoundHandler().playSound(new Tamaized.AoV.sound.EntityMovingSound(SoundEvents.cast, SoundCategory.NEUTRAL, e.getEntity(), false, 0));
			}
		}
	}

}
