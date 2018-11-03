package tamaized.aov.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import tamaized.aov.client.ClientHelpers;
import tamaized.aov.client.RenderPlayer;
import tamaized.aov.client.SizedFontRenderer;
import tamaized.aov.client.entity.RenderCelestialOpposition;
import tamaized.aov.client.entity.RenderCombust;
import tamaized.aov.client.entity.RenderDruidicWolf;
import tamaized.aov.client.entity.RenderEarthquake;
import tamaized.aov.client.entity.RenderFlameStrike;
import tamaized.aov.client.entity.RenderGravity;
import tamaized.aov.client.entity.RenderMalefic;
import tamaized.aov.client.entity.RenderNimbusRay;
import tamaized.aov.client.entity.RenderSpellBladeBarrier;
import tamaized.aov.client.entity.RenderSpellEntity;
import tamaized.aov.client.entity.RenderSpellLightingBolt;
import tamaized.aov.client.entity.RenderSpellLightingStorm;
import tamaized.aov.client.events.ClientSpawnEvent;
import tamaized.aov.client.events.KeyHandler;
import tamaized.aov.client.gui.AoVOverlay;
import tamaized.aov.client.particle.ParticleFeather;
import tamaized.aov.client.particle.ParticleHeartColor;
import tamaized.aov.common.entity.EntityCelestialOpposition;
import tamaized.aov.common.entity.EntityCombust;
import tamaized.aov.common.entity.EntityDruidicWolf;
import tamaized.aov.common.entity.EntityEarthquake;
import tamaized.aov.common.entity.EntityGravity;
import tamaized.aov.common.entity.EntityMalefic;
import tamaized.aov.common.entity.EntitySpellAoVParticles;
import tamaized.aov.common.entity.EntitySpellBladeBarrier;
import tamaized.aov.common.entity.EntitySpellImplosion;
import tamaized.aov.common.entity.EntitySpellLightningBolt;
import tamaized.aov.common.entity.EntitySpellLightningStorm;
import tamaized.aov.common.entity.EntitySpellVanillaParticles;
import tamaized.aov.common.entity.ProjectileFlameStrike;
import tamaized.aov.common.entity.ProjectileNimbusRay;
import tamaized.tammodized.client.particles.ParticleFluff;

import javax.annotation.Nullable;

public class ClientProxy extends CommonProxy {

	public static boolean barToggle = false;
	private static EntityLivingBase target;
	private static SizedFontRenderer FONT_RENDERER;

	public ClientProxy() {
		super(Side.CLIENT);
	}

	public static void setTarget() {
		Entity ent = ClientHelpers.getTargetOverMouse(Minecraft.getMinecraft(), 128);
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

	public static SizedFontRenderer getFontRenderer() {
		return FONT_RENDERER;
	}

	@Override
	public void preRegisters() {

	}

	@Override
	public void preInit() {
		if (!Minecraft.getMinecraft().getFramebuffer().isStencilEnabled())
			Minecraft.getMinecraft().getFramebuffer().enableStencil();

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
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellLightningBolt.class, RenderSpellLightingBolt::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEarthquake.class, RenderEarthquake::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellLightningStorm.class, RenderSpellLightingStorm::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDruidicWolf.class, RenderDruidicWolf::new);

	}

	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(new ClientSpawnEvent());
	}

	@Override
	public void postInit() {
		FONT_RENDERER = new SizedFontRenderer();
		if (Minecraft.getMinecraft().gameSettings.language != null) {
			FONT_RENDERER.setUnicodeFlag(Minecraft.getMinecraft().isUnicode());
			FONT_RENDERER.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
		}
		if (IReloadableResourceManager.class.isAssignableFrom(Minecraft.getMinecraft().getResourceManager().getClass()))
			IReloadableResourceManager.class.cast(Minecraft.getMinecraft().getResourceManager()).registerReloadListener(FONT_RENDERER);
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
