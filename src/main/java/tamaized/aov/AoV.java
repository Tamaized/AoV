package tamaized.aov;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.AoVCapabilityHandler;
import tamaized.aov.common.capabilities.aov.AoVCapabilityStorage;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.AstroCapabilityHandler;
import tamaized.aov.common.capabilities.astro.AstroCapabilityStorage;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.aov.common.capabilities.leap.LeapCapabilityHandler;
import tamaized.aov.common.capabilities.leap.LeapCapabilityStorage;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.capabilities.polymorph.PolymorphCapabilityHandler;
import tamaized.aov.common.capabilities.polymorph.PolymorphCapabilityStorage;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.common.capabilities.stun.StunCapabilityHandler;
import tamaized.aov.common.capabilities.stun.StunCapabilityStorage;
import tamaized.aov.common.commands.CommandAoV;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.common.entity.EntityCelestialOpposition;
import tamaized.aov.common.entity.EntityCombust;
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
import tamaized.aov.common.events.AttackHandler;
import tamaized.aov.common.events.PlayerInteractHandler;
import tamaized.aov.common.events.TickHandler;
import tamaized.aov.common.gui.GuiHandler;
import tamaized.aov.network.NetworkMessages;
import tamaized.aov.proxy.CommonProxy;
import tamaized.aov.registry.AoVAchievements;
import tamaized.aov.registry.AoVArmors;
import tamaized.aov.registry.AoVBlocks;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.AoVItems;
import tamaized.aov.registry.AoVPotions;
import tamaized.aov.registry.AoVTabs;
import tamaized.tammodized.TamModBase;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.proxy.AbstractProxy;

@Mod(modid = AoV.modid, name = "Angel of Vengeance", version = AoV.version, acceptedMinecraftVersions = "[1.12,)", dependencies = "required-before:" + TamModized.modid + "@[${tamversion},)")
public class AoV extends TamModBase {

	public static final String modid = "aov";
	protected final static String version = "${version}";
	@Instance(modid)
	public static AoV instance = new AoV();
	@SidedProxy(clientSide = "tamaized.aov.proxy.ClientProxy", serverSide = "tamaized.aov.proxy.ServerProxy")
	public static CommonProxy proxy;
	public static SimpleNetworkWrapper network;

	static {
		new AoVTabs();
		new AoVItems();
		new AoVArmors();
		new AoVBlocks();
		new AoVPotions();
		new AoVAchievements();
		new AoVDamageSource();
	}

	public static String getVersion() {
		return version;
	}

	@Override
	protected AbstractProxy getProxy() {
		return proxy;
	}

	@Override
	public String getModID() {
		return modid;
	}

	@Override
	@EventHandler
	public void FMLpreInit(FMLPreInitializationEvent event) {
		super.FMLpreInit(event);
	}

	@Override
	@EventHandler
	public void FMLinit(FMLInitializationEvent event) {
		super.FMLinit(event);
	}

	@Override
	@EventHandler
	public void FMLpostInit(FMLPostInitializationEvent event) {
		super.FMLpostInit(event);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		logger = LogManager.getLogger("AoV");

		logger.info("Starting AoV PreInit");

		NetworkMessages.register(network = NetworkRegistry.INSTANCE.newSimpleChannel(modid));

		CapabilityManager.INSTANCE.register(IAoVCapability.class, new AoVCapabilityStorage(), AoVCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(IAstroCapability.class, new AstroCapabilityStorage(), AstroCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(IStunCapability.class, new StunCapabilityStorage(), StunCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(ILeapCapability.class, new LeapCapabilityStorage(), LeapCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(IPolymorphCapability.class, new PolymorphCapabilityStorage(), PolymorphCapabilityHandler::new);
		MinecraftForge.EVENT_BUS.register(new CapabilityList());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		logger.info("Starting AoV Init");

		MinecraftForge.EVENT_BUS.register(new TickHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerInteractHandler());
		MinecraftForge.EVENT_BUS.register(new AttackHandler());

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		registerEntity(ProjectileNimbusRay.class, "ProjectileNimbusRay", this, modid, 256, 1, true);
		registerEntity(ProjectileFlameStrike.class, "ProjectileFlameStrike", this, modid, 256, 1, true);
		registerEntity(EntitySpellImplosion.class, "EntitySpellImplosion", this, modid, 256, 1, true);
		registerEntity(EntitySpellBladeBarrier.class, "EntitySpellBladeBarrier", this, modid, 256, 1, true);
		registerEntity(EntitySpellVanillaParticles.class, "EntitySpellVanillaParticles", this, modid, 256, 1, true);
		registerEntity(EntitySpellAoVParticles.class, "EntitySpellAoVParticles", this, modid, 256, 1, true);
		registerEntity(EntityMalefic.class, "EntityMalefic", this, modid, 256, 1, true);
		registerEntity(EntityCombust.class, "EntityCombust", this, modid, 256, 1, true);
		registerEntity(EntityGravity.class, "EntityGravity", this, modid, 256, 1, true);
		registerEntity(EntityCelestialOpposition.class, "EntityCelestialOpposition", this, modid, 256, 1, true);
		registerEntity(EntitySpellLightningBolt.class, "EntitySpellLightningBolt", this, modid, 256, 1, true);
		registerEntity(EntityEarthquake.class, "EntityEarthquake", this, modid, 256, 1, true);
		registerEntity(EntitySpellLightningStorm.class, "EntitySpellLightningStorm", this, modid, 256, 1, true);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		logger.info("Starting AoV PostInit");
		Abilities.register();
		AoVSkills.register();
	}

	@EventHandler
	public void startServer(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandAoV());
	}

}
