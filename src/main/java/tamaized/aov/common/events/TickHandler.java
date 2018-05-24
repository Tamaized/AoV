package tamaized.aov.common.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.proxy.CommonProxy;
import tamaized.aov.registry.AoVPotions;

import java.util.List;
import com.google.common.collect.Lists;

public class TickHandler {

	private static void spawnSlowfallParticles(EntityLivingBase living) {
		ILeapCapability cap = living.hasCapability(CapabilityList.LEAP, null) ? living.getCapability(CapabilityList.LEAP, null) : null;
		if (cap == null || cap.getLeapDuration() <= 0)
			return;
		final float perc = MathHelper.clamp((float) cap.getLeapDuration() / (float) cap.getMaxLeapDuration(), 0, 1);
		final int bound = 100 - ((int) (perc * 100)) + 1;
		Vec3d pos = living.getPositionVector();
		for (int i = 0; i < 3; i++)
			if (living.world.rand.nextInt(bound > 0 ? bound : 1) <= 2) {
				double yaw = Math.toRadians(living.renderYawOffset + 63);
				float range = 1.0F;
				float r = ((living.world.rand.nextFloat() * (1.0F + range)) - (0.5F + range));
				Vec3d vec = new Vec3d(-Math.cos(yaw), 1.7F, -Math.sin(yaw)).rotateYaw(r);
				vec = pos.add(vec).addVector(0, living.world.rand.nextFloat() * 0.5F - 0.5F, 0);
				AoV.proxy.spawnParticle(CommonProxy.ParticleType.Feather, living.world, vec, new Vec3d(0, 0, 0), 55, 0.1F, 1.5F, 0xFFFF00FF);
			}
	}

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
		if (player.hasCapability(CapabilityList.LEAP, null)) {
			ILeapCapability cap = player.getCapability(CapabilityList.LEAP, null);
			if (cap != null)
				cap.update(player);
		}
	}

	@SubscribeEvent
	public void updateLiving(LivingEvent.LivingUpdateEvent e) {
		EntityLivingBase living = e.getEntityLiving();
		if (living.world.isRemote)
			spawnSlowfallParticles(living);
		else {
			ILeapCapability cap = living.hasCapability(CapabilityList.LEAP, null) ? living.getCapability(CapabilityList.LEAP, null) : null;
			PotionEffect pot = living.getActivePotionEffect(AoVPotions.slowFall);
			if (pot == null || cap == null)
				return;
			if (living.ticksExisted % 20 == 0)
				cap.setLeapDuration(pot.getDuration());
		}
	}

	@SubscribeEvent
	public void updateEntity(TickEvent.WorldTickEvent e) {
		if (e.phase == TickEvent.Phase.START)
			return;
		List<Entity> list = Lists.newArrayList(e.world.loadedEntityList);
		for (Entity entity : list) {
			if (!(entity instanceof EntityLivingBase))
				continue;
			if(entity.isDead)
				continue;
			IStunCapability cap = entity.hasCapability(CapabilityList.STUN, null) ? entity.getCapability(CapabilityList.STUN, null) : null;
			if (cap != null)
				cap.update((EntityLivingBase) entity);
		}
	}

}
