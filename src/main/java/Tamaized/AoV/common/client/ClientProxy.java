package Tamaized.AoV.common.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.handlers.ClientPacketHandler;
import Tamaized.AoV.common.server.CommonProxy;
import Tamaized.AoV.gui.client.AoVOverlay;
import Tamaized.AoV.gui.client.AoVUIBar;
import Tamaized.AoV.registry.RegistryBase;

public class ClientProxy extends CommonProxy {
	
	@SideOnly(Side.CLIENT)
	public static Minecraft mc = Minecraft.getMinecraft();
	
	@SideOnly(Side.CLIENT)
	public static AoVUIBar bar;

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
	
		//RenderHeimdall renderHeimdall = new RenderHeimdall();
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeimdall.class, renderHeimdall);
		//MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(voidCraft.blocks.Heimdall), new ItemRenderHeimdall(renderHeimdall, new TileEntityHeimdall()));
	}
	
	@Override
	public void registerInventoryRender() {
		for(RegistryBase reg : AoV.registry) reg.setupRender();
	}

	@Override
	public void registerItems(){
				
	}

	@Override
	public void registerBlocks(){
		
	}
	
	@Override
	public void registerAchievements(){
		
	}

	@Override
	public void registerMISC(){
		
	}

	@Override
	public void registerNetwork() {
		AoV.channel.register(new ClientPacketHandler());
	}

}
