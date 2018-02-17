package tamaized.aov.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import tamaized.aov.client.RenderPlayer;
import tamaized.aov.client.entity.RenderCelestialOpposition;
import tamaized.aov.client.entity.RenderCombust;
import tamaized.aov.client.entity.RenderFlameStrike;
import tamaized.aov.client.entity.RenderGravity;
import tamaized.aov.client.entity.RenderMalefic;
import tamaized.aov.client.entity.RenderNimbusRay;
import tamaized.aov.client.entity.RenderSpellBladeBarrier;
import tamaized.aov.client.entity.RenderSpellEntity;
import tamaized.aov.client.events.ClientSpawnEvent;
import tamaized.aov.client.events.KeyHandler;
import tamaized.aov.client.gui.AoVOverlay;
import tamaized.aov.client.particle.ParticleFeather;
import tamaized.aov.client.particle.ParticleHeartColor;
import tamaized.aov.common.entity.EntityCelestialOpposition;
import tamaized.aov.common.entity.EntityCombust;
import tamaized.aov.common.entity.EntityGravity;
import tamaized.aov.common.entity.EntityMalefic;
import tamaized.aov.common.entity.EntitySpellAoVParticles;
import tamaized.aov.common.entity.EntitySpellBladeBarrier;
import tamaized.aov.common.entity.EntitySpellImplosion;
import tamaized.aov.common.entity.EntitySpellVanillaParticles;
import tamaized.aov.common.entity.ProjectileFlameStrike;
import tamaized.aov.common.entity.ProjectileNimbusRay;
import tamaized.tammodized.client.particles.ParticleFluff;

public class ClientProxy extends CommonProxy {

	public static boolean barToggle = false;

	public ClientProxy() {
		super(Side.CLIENT);
	}

	@Override
	public void preRegisters() {

	}

	@Override
	public void preInit() {

		KeyHandler.register();

		MinecraftForge.EVENT_BUS.register(new AoVOverlay());
		MinecraftForge.EVENT_BUS.register(new RenderPlayer());

		RenderingRegistry.registerEntityRenderingHandler(ProjectileNimbusRay.class, RenderNimbusRay::new);
		RenderingRegistry.registerEntityRenderingHandler(ProjectileFlameStrike.class, RenderFlameStrike::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellImplosion.class, RenderSpellEntity::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellBladeBarrier.class, RenderSpellBladeBarrier::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellAoVParticles.class, RenderSpellEntity::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellVanillaParticles.class, RenderSpellEntity::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMalefic.class, RenderMalefic::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCombust.class, RenderCombust::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGravity.class, RenderGravity::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCelestialOpposition.class, RenderCelestialOpposition::new);

	}

	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(new ClientSpawnEvent());
	}

	@Override
	public void postInit() {

	}

	@Override
	public void spawnParticle(ParticleType type, World world, Vec3d pos, Vec3d target, int life, float gravity, float scale, int color) {
		Particle particle = null;
		switch (type) {
			case Fluff:
				particle = new ParticleFluff(world, pos, target, life, gravity, scale, color);
				break;
			case Heart:
				particle = new ParticleHeartColor(world, pos, target, life, gravity, scale, color);
				break;
			case Feather:
				particle = new ParticleFeather(world, pos, target, life, gravity, scale, color);
				break;
		}
		//noinspection ConstantConditions
		if (particle != null)
			Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
}
