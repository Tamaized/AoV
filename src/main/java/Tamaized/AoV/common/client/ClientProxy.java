package Tamaized.AoV.common.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.handlers.ClientPacketHandler;
import Tamaized.AoV.common.server.CommonProxy;
import Tamaized.AoV.events.KeyHandler;
import Tamaized.AoV.gui.client.AoVOverlay;
import Tamaized.AoV.gui.client.AoVUIBar;
import Tamaized.AoV.render.RenderAngelicBlock;
import Tamaized.AoV.tileentity.TileEntityAngelicBlock;

public class ClientProxy extends CommonProxy {
	
	@SideOnly(Side.CLIENT)
	public static Minecraft mc = Minecraft.getMinecraft();
	
	@SideOnly(Side.CLIENT)
	public static AoVUIBar bar;
	
	public static KeyBinding key;
	public static boolean barToggle = false;

	@Override
	public void registerRenders(){
		
		bar = new AoVUIBar();
	
		//Events
		MinecraftForge.EVENT_BUS.register(new AoVOverlay());
		//FMLCommonHandler.instance().bus().register(new BGMusic()); 
	
		float shadowSize = 0.5F;
		//MOBS
		//RenderingRegistry.registerEntityRenderingHandler(EntityMobWraith.class, new RenderWraith(new ModelWraith(), shadowSize));
	
		//Projectiles
		//RenderingRegistry.registerEntityRenderingHandler(VoidChain.class, new RenderVoidChain(Minecraft.getMinecraft().getRenderManager()));
	
		//Blocks
		//RenderingRegistry.registerBlockHandler(new OreRenderer()); //TODO: Deal with this at a later time
	
		RenderAngelicBlock renderAngelicblock = new RenderAngelicBlock();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAngelicBlock.class, renderAngelicblock);
	}
	
	@Override
	public void registerKeyBinds(){
		key = new KeyBinding("key.aovbar", org.lwjgl.input.Keyboard.KEY_LMENU, "key.categories.aov");
		ClientRegistry.registerKeyBinding(key);
		MinecraftForge.EVENT_BUS.register(new KeyHandler());
	}
	
	@Override
	public void registerMISC(){
		
	}

	@Override
	public void registerNetwork() {
		AoV.channel.register(new ClientPacketHandler());
	}

}
