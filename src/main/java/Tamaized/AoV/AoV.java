package Tamaized.AoV;

import java.util.ArrayList;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tamaized.AoV.common.handlers.ServerPacketHandler;
import Tamaized.AoV.common.server.CommonProxy;
import Tamaized.AoV.registry.AoVAchievements;
import Tamaized.AoV.registry.AoVArmors;
import Tamaized.AoV.registry.AoVBiomes;
import Tamaized.AoV.registry.AoVBlocks;
import Tamaized.AoV.registry.AoVFluids;
import Tamaized.AoV.registry.AoVItems;
import Tamaized.AoV.registry.AoVMaterials;
import Tamaized.AoV.registry.AoVTabs;
import Tamaized.AoV.registry.AoVTools;
import Tamaized.AoV.registry.RegistryBase;

public class AoV {
	
	protected final static String version = "0.0.1";
	public static final String modid = "aov";
	
	public static String getVersion(){
		return version;
	}
	
	@Instance(modid) 
	public static AoV instance = new AoV();

	public static FMLEventChannel channel;
	public static final String networkChannelName = "AoV";

	@SidedProxy(clientSide = "Tamaized.AoV.common.client.ClientProxy", serverSide = "Tamaized.AoV.common.server.CommonProxy")
	public static CommonProxy proxy;

	public static final int WILDCARD_VALUE = OreDictionary.WILDCARD_VALUE;

	public static final int guiPlaceholder = 0;

	public static RegistryBase materials = new AoVMaterials();
	public static RegistryBase tabs = new AoVTabs();
	public static RegistryBase tools = new AoVTools();
	public static RegistryBase items = new AoVItems();
	public static RegistryBase armors = new AoVArmors();
	public static RegistryBase fluids = new AoVFluids();
	public static RegistryBase blocks = new AoVBlocks();
	public static RegistryBase biomes = new AoVBiomes();
	public static RegistryBase achievements = new AoVAchievements();
	
	public static ArrayList<RegistryBase> registry = new ArrayList<RegistryBase>();
	
	public static Logger logger;


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = LogManager.getLogger("AoV");
		
		logger.info("Starting AoV PreInit");
	
		channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(networkChannelName);
		
		registry.add(materials);
		registry.add(tabs);
		registry.add(tools);
		registry.add(items);
		registry.add(armors);
		registry.add(fluids);
		registry.add(blocks);
		registry.add(biomes);
		registry.add(achievements);
		
		for(RegistryBase reg : registry) reg.preInit();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event){ 
		logger.info("Starting AoV Init");
					
		//register GUI Handler
		//NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
		//GuiHandler guiHandler = new GuiHandler();
		//NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		//Register Events
		//FMLCommonHandler.instance().bus().register(VoidTickEvent);
		//MinecraftForge.EVENT_BUS.register(new PickUpEvent());
				
		//Tile Entities
		//GameRegistry.registerTileEntity(TileEntityVoidMacerator.class, "tileEntityVoidMacerator");
		

		for(RegistryBase reg : registry) reg.init();
		
		//Projectiles
		//EntityRegistry.registerModEntity(VoidChain.class, "VoidChain", 0, this, 128, 1, true);
		
		proxy.registerInventoryRender();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e){ 
		logger.info("Starting AoV PostInit");
		
		for(RegistryBase reg : registry) reg.postInit();
		
		channel.register(new ServerPacketHandler());
		proxy.registerNetwork();
		proxy.registerRenders();
		proxy.registerBlocks();
		proxy.registerRenderInformation();
		proxy.registerItems();
		proxy.registerMISC();
		proxy.registerAchievements();
	}

}
