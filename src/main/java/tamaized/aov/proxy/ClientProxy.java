package tamaized.aov.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import tamaized.aov.client.ClientHelpers;
import tamaized.aov.client.RenderPlayer;
import tamaized.aov.client.SizedFontRenderer;
import tamaized.aov.client.events.ClientSpawnEvent;
import tamaized.aov.client.gui.AoVOverlay;
import tamaized.aov.client.particle.ParticleFeather;
import tamaized.aov.client.particle.ParticleHeartColor;

import javax.annotation.Nullable;

public class ClientProxy extends CommonProxy {

	public static boolean barToggle = false;
	private static EntityLivingBase target;
	private static SizedFontRenderer FONT_RENDERER;

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

	public static SizedFontRenderer getFontRenderer() {
		return FONT_RENDERER;
	}

	@Override
	public void preInit() {
		if (!Minecraft.getInstance().getFramebuffer().isStencilEnabled())
			Minecraft.getInstance().getFramebuffer().enableStencil();

		//		KeyHandler.register(); TODO

		MinecraftForge.EVENT_BUS.register(new AoVOverlay());
		MinecraftForge.EVENT_BUS.register(new RenderPlayer());

	}

	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(new ClientSpawnEvent());
	}

	@Override
	public void postInit() {
		FONT_RENDERER = new SizedFontRenderer();
		if (Minecraft.getInstance().gameSettings.language != null) {
			//			FONT_RENDERER.setUnicodeFlag(Minecraft.getInstance().isUnicode());
			FONT_RENDERER.setBidiFlag(Minecraft.getInstance().getLanguageManager().isCurrentLanguageBidirectional());
		}
		/*if (IReloadableResourceManager.class.isAssignableFrom(Minecraft.getInstance().getResourceManager().getClass()))
			IReloadableResourceManager.class.cast(Minecraft.getInstance().getResourceManager()).addReloadListener(FONT_RENDERER);*/
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
		//noinspection ConstantConditions
		if (particle != null)
			Minecraft.getInstance().particles.addEffect(particle);
	}
}
