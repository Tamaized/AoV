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
import Tamaized.AoV.events.ClientSpawnEvent;
import Tamaized.AoV.events.KeyHandler;
import Tamaized.AoV.gui.client.AoVOverlay;
import Tamaized.AoV.network.ClientPacketHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import tamaized.tammodized.proxy.AbstractProxy;

public class ClientProxy extends AbstractProxy {

	public ClientProxy() {
		super(Side.CLIENT);
	}

	public static KeyBinding key;
	public static boolean barToggle = false;

	@Override
	public void preRegisters() {

	}

	@Override
	public void preInit() {

		key = new KeyBinding("key.aovbar", org.lwjgl.input.Keyboard.KEY_LMENU, "key.categories.aov");
		ClientRegistry.registerKeyBinding(key);

		MinecraftForge.EVENT_BUS.register(new AoVOverlay());
		MinecraftForge.EVENT_BUS.register(new RenderPlayer());

		RenderingRegistry.registerEntityRenderingHandler(ProjectileNimbusRay.class, RenderNimbusRay::new);
		RenderingRegistry.registerEntityRenderingHandler(ProjectileFlameStrike.class, RenderFlameStrike::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellImplosion.class, RenderSpellImplosion::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellBladeBarrier.class, RenderSpellBladeBarrier::new);

	}

	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(new ClientSpawnEvent());
	}

	@Override
	public void postInit() {
		MinecraftForge.EVENT_BUS.register(new KeyHandler());
		AoV.channel.register(new ClientPacketHandler());
	}

}
