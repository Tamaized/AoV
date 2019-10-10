package tamaized.aov;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import tamaized.aov.common.commands.AoVCommands;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.network.NetworkMessages;
import tamaized.aov.registry.AoVAchievements;
import tamaized.aov.registry.AoVArmors;
import tamaized.aov.registry.AoVBlocks;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.AoVEntities;
import tamaized.aov.registry.AoVItems;
import tamaized.aov.registry.AoVParticles;
import tamaized.aov.registry.AoVPotions;
import tamaized.aov.registry.AoVTabs;

import javax.annotation.Nonnull;

@Mod(value = AoV.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AoV {

	public static final String MODID = "aov";

	public static final Logger LOGGER = LogManager.getLogger("AoV");
	public static final IDataSerializer<Integer[]> VARINTS = new IDataSerializer<Integer[]>() {
		@Override
		public void write(PacketBuffer buf, Integer[] values) {
			buf.writeVarInt(values.length);
			for (int value : values)
				buf.writeVarInt(value);
		}

		@Override
		public Integer[] read(PacketBuffer buf) {
			int length = buf.readVarInt();
			Integer[] values = new Integer[length];
			for (int i = 0; i < length; i++)
				values[i] = buf.readVarInt();
			return values;
		}

		@Nonnull
		@Override
		public Integer[] copyValue(@Nonnull Integer[] value) {
			return value;
		}
	};
	public static ConfigHandler config;
	public static SimpleChannel network = NetworkRegistry.ChannelBuilder.
			named(new ResourceLocation(MODID, MODID)).
			clientAcceptedVersions(s -> true).
			serverAcceptedVersions(s -> true).
			networkProtocolVersion(() -> "1").
			simpleChannel();

	public AoV() {
		final Pair<ConfigHandler, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigHandler::new);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, specPair.getRight());
		config = specPair.getLeft();

		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);

		IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

		AoVTabs.register(mod);
		AoVItems.register(mod);
		AoVArmors.register(mod);
		AoVBlocks.register(mod);
		AoVPotions.register(mod);
		AoVAchievements.register(mod);
		AoVDamageSource.register(mod);
		AoVParticles.register(mod);
		AoVEntities.register(mod);
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		LOGGER.info("Initalizating AoV");

		ConfigHandler.update();

		NetworkMessages.register(network);
		DataSerializers.registerSerializer(VARINTS);

		CapabilityManager.INSTANCE.register(IAoVCapability.class, new AoVCapabilityStorage(), AoVCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(IAstroCapability.class, new AstroCapabilityStorage(), AstroCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(IStunCapability.class, new StunCapabilityStorage(), StunCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(ILeapCapability.class, new LeapCapabilityStorage(), LeapCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(IPolymorphCapability.class, new PolymorphCapabilityStorage(), PolymorphCapabilityHandler::new);

		Abilities.register();
		AoVSkills.register();

	}

	public void serverStarting(FMLServerStartingEvent evt) {
		evt.getCommandDispatcher().register(

				LiteralArgumentBuilder.<CommandSource>literal("aov").
						then(AoVCommands.Open.register()).
						then(AoVCommands.SetLevel.register()).
						then(AoVCommands.Reset.register()).
						then(AoVCommands.Debug.register())

		);
	}

}
