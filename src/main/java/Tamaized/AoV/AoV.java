package Tamaized.AoV;

import org.apache.logging.log4j.LogManager;

import Tamaized.AoV.common.handlers.ServerPacketHandler;
import Tamaized.AoV.common.server.CommonProxy;
import Tamaized.AoV.core.AoVCore;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.entity.projectile.caster.ProjectileNimbusRay;
import Tamaized.AoV.events.PlayerInteractHandler;
import Tamaized.AoV.events.PlayerJoinLeaveEvent;
import Tamaized.AoV.events.TickHandler;
import Tamaized.AoV.gui.GuiHandler;
import Tamaized.AoV.registry.AoVAchievements;
import Tamaized.AoV.registry.AoVArmors;
import Tamaized.AoV.registry.AoVBiomes;
import Tamaized.AoV.registry.AoVBlocks;
import Tamaized.AoV.registry.AoVDamageSource;
import Tamaized.AoV.registry.AoVFluids;
import Tamaized.AoV.registry.AoVItems;
import Tamaized.AoV.registry.AoVMaterials;
import Tamaized.AoV.registry.AoVTabs;
import Tamaized.AoV.registry.AoVTools;
import Tamaized.TamModized.TamModBase;
import Tamaized.TamModized.TamModized;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = AoV.modid, name = "Angel of Vengeance", version = AoV.version, dependencies = "required-before:" + TamModized.modid + "@[" + TamModized.version + ",)")
public class AoV extends TamModBase {

	protected final static String version = "${version}";
	public static final String modid = "aov";

	public static String getVersion() {
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

	public static AoVMaterials materials = new AoVMaterials();
	public static AoVTabs tabs = new AoVTabs();
	public static AoVTools tools = new AoVTools();
	public static AoVItems items = new AoVItems();
	public static AoVArmors armors = new AoVArmors();
	public static AoVFluids fluids = new AoVFluids();
	public static AoVBlocks blocks = new AoVBlocks();
	public static AoVBiomes biomes = new AoVBiomes();
	public static AoVAchievements achievements = new AoVAchievements();
	public static AoVDamageSource damageSources = new AoVDamageSource();

	public static AoVCore serverAoVCore;
	public static AoVCore clientAoVCore;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = LogManager.getLogger("AoV");

		logger.info("Starting AoV PreInit");

		channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(networkChannelName);

		register(materials);
		register(tabs);
		register(tools);
		register(items);
		register(armors);
		register(fluids);
		register(blocks);
		register(biomes);
		register(achievements);
		register(damageSources);

		super.preInit(event);

	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		logger.info("Starting AoV Init");

		super.init(event);

		MinecraftForge.EVENT_BUS.register(new PlayerJoinLeaveEvent());
		MinecraftForge.EVENT_BUS.register(new TickHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerInteractHandler());

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		// Projectiles
		registerEntity(ProjectileNimbusRay.class, "ProjectileNimbusRay", AoV.instance, modid, 128, 1, true);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		logger.info("Starting AoV PostInit");

		super.postInit(e);

		channel.register(new ServerPacketHandler());

		AbilityBase.register();
		AoVSkill.registerSkills();

		proxy.registerKeyBinds();
		proxy.registerNetwork();
		proxy.registerRenders();
		proxy.registerMISC();
	}

}
