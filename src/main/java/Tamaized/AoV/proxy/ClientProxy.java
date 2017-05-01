package Tamaized.AoV.proxy;

import Tamaized.AoV.AoV;
import Tamaized.AoV.client.RenderPlayer;
import Tamaized.AoV.entity.EntitySpellBladeBarrier;
import Tamaized.AoV.entity.EntitySpellImplosion;
import Tamaized.AoV.entity.projectile.caster.ProjectileFlameStrike;
import Tamaized.AoV.entity.projectile.caster.ProjectileNimbusRay;
import Tamaized.AoV.entity.projectile.caster.render.RenderFlameStrike;
import Tamaized.AoV.entity.projectile.caster.render.RenderNimbusRay;
import Tamaized.AoV.entity.render.RenderSpellBladeBarrier;
import Tamaized.AoV.entity.render.RenderSpellImplosion;
import Tamaized.AoV.events.KeyHandler;
import Tamaized.AoV.gui.client.AoVOverlay;
import Tamaized.AoV.gui.client.AoVUIBar;
import Tamaized.AoV.network.ClientPacketHandler;
import Tamaized.TamModized.proxy.AbstractProxy;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends AbstractProxy {

	public ClientProxy() {
		super(Side.CLIENT);
	}

	public static AoVUIBar bar;

	public static KeyBinding key;
	public static boolean barToggle = false;

	@Override
	public void preRegisters() {

	}

	@Override
	public void preInit() {

		bar = new AoVUIBar();

		MinecraftForge.EVENT_BUS.register(new AoVOverlay());
		MinecraftForge.EVENT_BUS.register(new RenderPlayer());

		float shadowSize = 0.5F;

		RenderingRegistry.registerEntityRenderingHandler(ProjectileNimbusRay.class, new IRenderFactory<ProjectileNimbusRay>() {
			@Override
			public Render<? super ProjectileNimbusRay> createRenderFor(RenderManager manager) {
				return new RenderNimbusRay(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(ProjectileFlameStrike.class, new IRenderFactory<ProjectileFlameStrike>() {
			@Override
			public Render<? super ProjectileFlameStrike> createRenderFor(RenderManager manager) {
				return new RenderFlameStrike(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellImplosion.class, new IRenderFactory<EntitySpellImplosion>() {
			@Override
			public Render<? super EntitySpellImplosion> createRenderFor(RenderManager manager) {
				return new RenderSpellImplosion(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellBladeBarrier.class, new IRenderFactory<EntitySpellBladeBarrier>() {
			@Override
			public Render<? super EntitySpellBladeBarrier> createRenderFor(RenderManager manager) {
				return new RenderSpellBladeBarrier(manager);
			}
		});

	}

	@Override
	public void init() {

	}

	@Override
	public void postInit() {
		key = new KeyBinding("key.aovbar", org.lwjgl.input.Keyboard.KEY_LMENU, "key.categories.aov");
		ClientRegistry.registerKeyBinding(key);
		MinecraftForge.EVENT_BUS.register(new KeyHandler());
		AoV.channel.register(new ClientPacketHandler());
	}

}
