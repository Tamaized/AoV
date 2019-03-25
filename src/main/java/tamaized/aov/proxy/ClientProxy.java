package tamaized.aov.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.aov.client.ClientHelpers;
import tamaized.aov.client.events.KeyHandler;
import tamaized.aov.client.particle.ParticleFeather;
import tamaized.aov.client.particle.ParticleHeartColor;

import javax.annotation.Nullable;

public class ClientProxy extends CommonProxy {

	public static boolean barToggle = false;
	private static EntityLivingBase target;

	public static void setTarget() {
		Entity ent = ClientHelpers.getTargetOverMouse(Minecraft.getInstance(), 128);
		if (ent instanceof EntityLivingBase && target != ent)
			target = (EntityLivingBase) ent;
		else
			target = null;
	}

	public static EntityLivingBase getTarget() {
		return target;
	}

	public static void setTarget(@Nullable EntityLivingBase entity) {
		target = entity;
	}

	@Override
	public void init() {
		super.init();
		KeyHandler.register();
		Minecraft.getInstance().addScheduledTask(() -> {
			if (!Minecraft.getInstance().getFramebuffer().isStencilEnabled())
				Minecraft.getInstance().getFramebuffer().enableStencil();
		});
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public void spawnParticle(ParticleType type, World world, Vec3d pos, Vec3d target, int life, float gravity, float scale, int color) {
		Particle particle = null;
		switch (type) {
			case Fluff:
				//				particle = new ParticleFluff(world, pos, target, life, gravity, scale, color); TODO: let's not use this stupid thing anymore
				break;
			case Heart:
				particle = new ParticleHeartColor(world, pos, target, life, gravity, scale, color);
				break;
			case Feather:
				particle = new ParticleFeather(world, pos, target, life, gravity, scale, color);
				break;
		}
		if (particle != null)
			Minecraft.getInstance().particles.addEffect(particle);
	}
}
