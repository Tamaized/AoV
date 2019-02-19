package tamaized.aov;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import tamaized.aov.common.config.ConfigHandler;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.common.entity.EntityAlignmentAoE;
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
import tamaized.aov.common.events.AttackHandler;
import tamaized.aov.common.events.PlayerInteractHandler;
import tamaized.aov.common.events.TickHandler;
import tamaized.aov.common.gui.GuiHandler;
import tamaized.aov.network.NetworkMessages;
import tamaized.aov.proxy.ClientProxy;
import tamaized.aov.proxy.CommonProxy;
import tamaized.aov.registry.AoVAchievements;
import tamaized.aov.registry.AoVArmors;
import tamaized.aov.registry.AoVBlocks;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.AoVItems;
import tamaized.aov.registry.AoVParticles;
import tamaized.aov.registry.AoVPotions;
import tamaized.aov.registry.AoVTabs;

import java.util.function.Function;
import java.util.function.Supplier;

@Mod(value = AoV.MODID)
public class AoV {

	public static final String MODID = "aov";

	public static final Logger LOGGER = LogManager.getLogger("AoV");
	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	public static ConfigHandler config;
	public static SimpleChannel network = NetworkRegistry.ChannelBuilder

			.named(new ResourceLocation(MODID, MODID))

			.clientAcceptedVersions("1"::equals)

			.serverAcceptedVersions("1"::equals)

			.networkProtocolVersion(() -> "1")

			.simpleChannel();

	static {
		new AoVTabs();
		new AoVItems();
		new AoVArmors();
		new AoVBlocks();
		new AoVPotions();
		new AoVAchievements();
		new AoVDamageSource();
		new AoVParticles();
	}

	@SubscribeEvent
	public void init(FMLCommonSetupEvent event) {
		LOGGER.info("Initalizating AoV");

		NetworkMessages.register(network);

		CapabilityManager.INSTANCE.register(IAoVCapability.class, new AoVCapabilityStorage(), AoVCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(IAstroCapability.class, new AstroCapabilityStorage(), AstroCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(IStunCapability.class, new StunCapabilityStorage(), StunCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(ILeapCapability.class, new LeapCapabilityStorage(), LeapCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(IPolymorphCapability.class, new PolymorphCapabilityStorage(), PolymorphCapabilityHandler::new);

		final Pair<ConfigHandler, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigHandler::new);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, specPair.getRight());
		config = specPair.getLeft();

		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiHandler::getGui);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		registerEntity(ProjectileNimbusRay.class, "ProjectileNimbusRay", this, MODID, 256, 1, true);
		registerEntity(ProjectileFlameStrike.class, "ProjectileFlameStrike", this, MODID, 256, 1, true);
		registerEntity(EntitySpellImplosion.class, "EntitySpellImplosion", this, MODID, 256, 1, true);
		registerEntity(EntitySpellBladeBarrier.class, "EntitySpellBladeBarrier", this, MODID, 256, 1, true);
		registerEntity(EntitySpellVanillaParticles.class, "EntitySpellVanillaParticles", this, MODID, 256, 1, true);
		registerEntity(EntitySpellAoVParticles.class, "EntitySpellAoVParticles", this, MODID, 256, 1, true);
		registerEntity(EntityMalefic.class, "EntityMalefic", this, MODID, 256, 1, true);
		registerEntity(EntityCombust.class, "EntityCombust", this, MODID, 256, 1, true);
		registerEntity(EntityGravity.class, "EntityGravity", this, MODID, 256, 1, true);
		registerEntity(EntityCelestialOpposition.class, "EntityCelestialOpposition", this, MODID, 256, 1, true);
		registerEntity(EntitySpellLightningBolt.class, "EntitySpellLightningBolt", this, MODID, 256, 1, true);
		registerEntity(EntityEarthquake.class, "EntityEarthquake", this, MODID, 256, 1, true);
		registerEntity(EntitySpellLightningStorm.class, "EntitySpellLightningStorm", this, MODID, 256, 1, true);
		registerEntity(EntityDruidicWolf.class, "EntityDruidicWolf", this, MODID, 256, 1, true);
		registerEntity(EntityAlignmentAoE.class, "EntityAlignmentAoE", this, MODID, 256, 1, true);
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
