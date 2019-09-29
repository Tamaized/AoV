package tamaized.aov.common.events;

import com.google.common.collect.Lists;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tamaized.aov.AoV;
import tamaized.aov.client.ParticleHelper;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.registry.AoVPotions;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = AoV.MODID)
public class TickHandler {

	private static final List<UUID> FLYING = Lists.newArrayList();

	private static void spawnSlowfallParticles(LivingEntity living) {
		ILeapCapability cap = CapabilityList.getCap(living, CapabilityList.LEAP);
		if (cap == null || cap.getLeapDuration() <= 0)
			return;
		final float perc = MathHelper.clamp((float) cap.getLeapDuration() / (float) cap.getMaxLeapDuration(), 0, 1);
		final int bound = 100 - ((int) (perc * 100)) + 1;
		Vec3d pos = living.getPositionVector().add(0, living.getHeight() - 1.62F, 0);
		for (int i = 0; i < 3; i++)
			if (living.world.rand.nextInt(bound > 0 ? bound : 1) <= 2) {
				double yaw = Math.toRadians(living.renderYawOffset + 63);
				float range = 1.0F;
				float r = ((living.world.rand.nextFloat() * (1.0F + range)) - (0.5F + range));
				Vec3d vec = new Vec3d(-Math.cos(yaw), 1.7F, -Math.sin(yaw)).rotateYaw(r);
				vec = pos.add(vec).add(0, living.world.rand.nextFloat() * 0.5F - 0.5F, 0);
				ParticleHelper.spawnParticle(ParticleHelper.ParticleType.Feather, living.world, vec, new Vec3d(0, 0, 0), 55, 0.1F, 0.15F, 0xFFFF00FF);
			}
	}

	@SubscribeEvent
	public static void updateLiving(LivingEvent.LivingUpdateEvent e) {
		LivingEntity living = e.getEntityLiving();
		IPolymorphCapability poly = CapabilityList.getCap(living, CapabilityList.POLYMORPH);
		if (poly != null && (poly.getMorph() == IPolymorphCapability.Morph.WaterElemental || poly.getMorph() == IPolymorphCapability.Morph.FireElemental || poly.getMorph() == IPolymorphCapability.Morph.ArchAngel))
			for (Effect potion : IPolymorphCapability.ELEMENTAL_IMMUNITY_EFFECTS)
				living.removePotionEffect(potion);
		if (living.world.isRemote)
			spawnSlowfallParticles(living);
		else {
			PlayerEntity player = living instanceof PlayerEntity ? (PlayerEntity) living : null;
			if (player != null)
				if (poly != null && poly.getMorph() == IPolymorphCapability.Morph.ArchAngel) {
					player.abilities.allowFlying = true;
					player.sendPlayerAbilities();
					if (!FLYING.contains(player.getUniqueID()))
						FLYING.add(player.getUniqueID());
				} else if (FLYING.remove(player.getUniqueID()) && !player.isCreative() && !player.isSpectator()) {
					player.abilities.allowFlying = false;
					player.sendPlayerAbilities();
				} else if (!player.isCreative() && !player.isSpectator() && !player.abilities.allowFlying) { // This will run one tick later so we can detect if other mods enable flight
					player.abilities.disableDamage = false;
					player.abilities.isFlying = false;
					player.sendPlayerAbilities();
				}
			ILeapCapability cap = CapabilityList.getCap(living, CapabilityList.LEAP);
			EffectInstance pot = living.getActivePotionEffect(AoVPotions.slowFall);
			if (pot == null || cap == null)
				return;
			if (living.ticksExisted % 20 == 0)
				cap.setLeapDuration(pot.getDuration());
		}
	}

	@SubscribeEvent
	public static void updateEntity(TickEvent.WorldTickEvent e) {
		if (e.phase == TickEvent.Phase.START)
			return;
		Iterable<Entity> list = null;
		if (e.world instanceof ServerWorld)
			list = ((ServerWorld) e.world).getEntities().collect(Collectors.toList());
		else if (e.world instanceof ClientWorld)
			list = ((ClientWorld) e.world).getAllEntities();
		if (list == null)
			return;
		for (Entity entity : list) {
			if (!(entity instanceof LivingEntity))
				continue;
			if (!entity.isAlive())
				continue;
			IStunCapability cap = CapabilityList.getCap(entity, CapabilityList.STUN);
			if (cap != null)
				cap.update((LivingEntity) entity);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void update(TickEvent.PlayerTickEvent e) {
		if (e.phase == TickEvent.Phase.START)
			return;
		PlayerEntity player = e.player;
		if (player.getHealth() <= (player.getMaxHealth() / 2)) {
			if (player.getActivePotionEffect(AoVPotions.stalwartPact) != null) {
				player.removeActivePotionEffect(AoVPotions.stalwartPact);
				player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, (20 * (60 * 5)), 2));
				player.addPotionEffect(new EffectInstance(Effects.REGENERATION, (20 * (10)), 2));
			}
			if (player.getActivePotionEffect(AoVPotions.naturesBounty) != null) {
				player.removeActivePotionEffect(AoVPotions.naturesBounty);
				player.addPotionEffect(new EffectInstance(Effects.REGENERATION, (20 * (10)), 2));
			}
		}
		{
			IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
			if (cap != null)
				cap.update(player);
		}
		{
			IAstroCapability cap = CapabilityList.getCap(player, CapabilityList.ASTRO);
			if (cap != null)
				cap.update(player);
		}
		{
			ILeapCapability cap = CapabilityList.getCap(player, CapabilityList.LEAP);
			if (cap != null)
				cap.update(player);
		}
		{
			IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
			if (cap != null)
				cap.update(player);
		}
	}

}
