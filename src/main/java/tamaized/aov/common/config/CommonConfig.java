package tamaized.aov.common.config;

import com.electronwill.nightconfig.core.utils.ConfigWrapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import tamaized.aov.AoV;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AoV.MODID)
public class CommonConfig {

	public static Set<Item> CENTERED_WEAR = ImmutableSet.of();
	public ConfigWrapper file;
	public Earthquake EARTHQUAKE = new Earthquake();
	public ForgeConfigSpec.IntValue maxlevel;
	public ForgeConfigSpec.ConfigValue<Integer> recharge;
	public ForgeConfigSpec.BooleanValue experience;
	public ForgeConfigSpec.BooleanValue handwrapsSpeed;
	public ForgeConfigSpec.ConfigValue<List<? extends String>> centered;
	public static boolean LOADED = false;

	public CommonConfig(ForgeConfigSpec.Builder builder) {
		builder.
				comment("Manages the Earthquake Spell Block Destruction").
				push("Earthquake");
		{
			EARTHQUAKE.enable = builder.
					translation("Enable").
					comment("Enable Earthquake Destruction").
					define("enable", true);
			EARTHQUAKE.air = builder.
					translation("Enable Air").
					comment("If Disabled, destruction stops at the last block rather than setting to air").
					define("air", true);
			EARTHQUAKE.ticks = builder.
					translation("Destruction Ticks").
					comment("Amount of Ticks to wait until the next Destruction; Lower = Sooner").
					defineInRange("ticks", 5, 1, Integer.MAX_VALUE);
			EARTHQUAKE.chance = builder.
					translation("Destruction Chance").
					comment("Chance that a Destruction will take place; Lower = Higher Chance").
					defineInRange("chance", 5, 1, Integer.MAX_VALUE);
			EARTHQUAKE.destruction = builder.
					translation("Destruction Order").
					comment("domain:name|other\n" +

							"[domain] defaults to `minecraft`\n" +

							"[other] is what CAN be broken down but won't be broken down into.\n\n" +


							"Example: [minecraft:gravel|minecraft:grass] may have Cobble before it, so Cobble breaks down into Gravel, which may have Dirt after it so Gravel breaks down into Dirt.\n" +

							"Grass will also break down into Dirt but Cobble will never break down into Grass.").
					define("destruction", Lists.newArrayList(

							getRegName(Blocks.STONE),

							getRegName(Blocks.COBBLESTONE),

							getRegName(Blocks.GRAVEL) + "|" + getRegName(Blocks.GRASS_BLOCK) + "|" + getRegName(Blocks.GRASS_PATH) + "|" + getRegName(Blocks.COARSE_DIRT),

							getRegName(Blocks.DIRT) + "|" + getRegName(Blocks.SANDSTONE),

							getRegName(Blocks.SAND) + "|" + getRegName(Blocks.RED_SAND)

					), o -> o instanceof String);
		}
		builder.pop();
		{
			maxlevel = builder.
					translation("Max Level").
					comment("Sets the maximum level").
					defineInRange("maxLevel", 15, 1, Integer.MAX_VALUE);
			recharge = builder.
					translation("Recharge Delay").
					comment("Sets the rate, in ticks, at which all abilities will replenish a single charge. -1 disables this").
					define("recharge", -1);
			experience = builder.
					translation("Enable Vanilla Experience gain").
					comment("Determines whether or not vanilla experience contributes to AoV experience gain").
					define("experience", false);
			handwrapsSpeed = builder.
					translation("Speedy Handwraps").
					comment("Handwraps add +6 to base attack speed").
					define("handwrapsSpeed", true);
			centered = builder.
					translation("Centered Wear").
					comment("domain:name\ndomain defaults to `minecraft`").
					defineList("centered", Lists.newArrayList(

							AoV.MODID + ":handwraps",

							getRegName(Items.WOODEN_SWORD),

							getRegName(Items.WOODEN_AXE),

							getRegName(Items.WOODEN_HOE),

							getRegName(Items.WOODEN_PICKAXE),

							getRegName(Items.WOODEN_SHOVEL),

							getRegName(Items.LEATHER_BOOTS),

							getRegName(Items.LEATHER_CHESTPLATE),

							getRegName(Items.LEATHER_HELMET),

							getRegName(Items.LEATHER_LEGGINGS)

					), o -> o instanceof String);
		}
	}

	private static String getRegName(Item item) {
		return Objects.requireNonNull(item.getRegistryName()).getPath();
	}

	private static String getRegName(Block block) {
		return Objects.requireNonNull(block.getRegistryName()).getPath();
	}

	private static void setupCenteredWear() {
		List<Item> list = Lists.newArrayList();
		for (String next : AoV.config.centered.get()) {
			String[] split = next.split(":");
			String domain = "minecraft";
			String regname = split[0];
			if (split.length > 1) {
				domain = split[0];
				regname = split[1];
			}
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(domain, regname));
			if (item == null || item instanceof AirItem)
				continue;
			list.add(item);
		}
		CENTERED_WEAR = ImmutableSet.copyOf(list);
	}

	public static void update() {
		setupCenteredWear();
		LOADED = true;
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading event) {
		if (event.getConfig().getModId().equals(AoV.MODID)) {
			AoV.config.file = (ConfigWrapper) event.getConfig().getConfigData();
			update();
		}
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfig.Reloading event) {
		if (event.getConfig().getModId().equals(AoV.MODID))
			update();
	}

	public static class Earthquake {

		public ForgeConfigSpec.BooleanValue enable;
		public ForgeConfigSpec.BooleanValue air;
		public ForgeConfigSpec.IntValue ticks;
		public ForgeConfigSpec.IntValue chance;
		public ForgeConfigSpec.ConfigValue<List<? extends String>> destruction;

	}

}
