package tamaized.aov.proxy;

import tamaized.aov.AoV;
import tamaized.aov.client.RenderPlayer;
import tamaized.aov.common.entity.EntitySpellBladeBarrier;
import tamaized.aov.common.entity.EntitySpellImplosion;
import tamaized.aov.common.entity.ProjectileFlameStrike;
import tamaized.aov.common.entity.ProjectileNimbusRay;
import tamaized.aov.client.entity.RenderFlameStrike;
import tamaized.aov.client.entity.RenderNimbusRay;
import tamaized.aov.client.entity.RenderSpellBladeBarrier;
import tamaized.aov.client.entity.RenderSpellImplosion;
import tamaized.aov.client.events.ClientSpawnEvent;
import tamaized.aov.client.events.KeyHandler;
import tamaized.aov.client.gui.AoVOverlay;
import tamaized.aov.network.ClientPacketHandler;
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
