package tamaized.aov.common.config;

import com.electronwill.nightconfig.core.utils.ConfigWrapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import tamaized.aov.AoV;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AoV.MODID)
public class ConfigHandler {

	public static Set<Item> CENTERED_WEAR = ImmutableSet.of();
	public ConfigWrapper file;
	public ElementPositions ELEMENT_POSITIONS = new ElementPositions();
	public Earthquake EARTHQUAKE = new Earthquake();
	public ForgeConfigSpec.IntValue maxlevel;
	public ForgeConfigSpec.ConfigValue<Integer> recharge;
	public ForgeConfigSpec.BooleanValue experience;
	public ForgeConfigSpec.BooleanValue handwrapsSpeed;
	public ForgeConfigSpec.BooleanValue renderBarOverHotbar;
	public ForgeConfigSpec.BooleanValue renderChargesAboveSpellbar;
	public ForgeConfigSpec.BooleanValue renderAstro;
	public ForgeConfigSpec.BooleanValue renderRoyalRoad;
	public ForgeConfigSpec.BooleanValue renderTarget;
	public ForgeConfigSpec.DoubleValue targetOpacity;
	public ForgeConfigSpec.ConfigValue<List<? extends String>> centered;

	public ConfigHandler(ForgeConfigSpec.Builder builder) {
		builder.
				comment("The XY positions of on screen elements from the mod").
				push("Element Positions");
		{
			ELEMENT_POSITIONS.spellbar_x = builder.
					translation("SpellBar X").
					comment("SpellBar X").
					define("spellbar_x", 0);
			ELEMENT_POSITIONS.spellbar_y = builder.
					translation("SpellBar X").
					comment("SpellBar Y").
					define("spellbar_y", 0);
			ELEMENT_POSITIONS.astro_x = builder.
					translation("Astro X").
					comment("Astro X").
					define("astro_x", 0);
			ELEMENT_POSITIONS.astro_y = builder.
					translation("Astro X").
					comment("Astro Y").
					define("astro_y", 0);
			ELEMENT_POSITIONS.target_x = builder.
					translation("Target X").
					comment("Target X").
					define("target_x", 0);
			ELEMENT_POSITIONS.target_y = builder.
					translation("Target X").
					comment("Target Y").
					define("target_y", 0);
		}
		builder.pop().
				comment("Manages the Earthquake Spell Block Destruction").
				push("Earthquake");
		{
			EARTHQUAKE.enable = builder.
					translation("Enable").
					comment("Enable Earthquake Destruction").
					define("enable", true);
			EARTHQUAKE.shake = builder.
					comment("Enable Screen Shaking").
					define("shake", true);
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
					comment("Sets the recharge rate per tick, -1 disables this").
					define("recharge", -1);
			experience = builder.
					translation("Enable Vanilla Experience gain").
					comment("Determines whether or not vanilla experience contributes to AoV experience gain").
					define("experience", false);
			handwrapsSpeed = builder.
					translation("Speedy Handwraps").
					comment("Handwraps add +6 to base attack speed").
					define("handwrapsSpeed", true);
			renderBarOverHotbar = builder.
					translation("Render SpellBar Over HotBar").
					comment("Sets the Spellbar to render in place of the hotbar while active.").
					define("renderBarOverHotbar", false);
			renderChargesAboveSpellbar = builder.
					translation("Render Charges Above SpellBar").
					comment("Renders the Charges left to be above the Spellbar instead of below. This setting has no impact if Render SpellBar Over HotBar is enabled.").
					define("renderChargesAboveSpellbar", false);
			renderAstro = builder.
					translation("Render Astro UI while Empty").
					comment("If false, the Astro UI will no longer render while not holding any card, spread, or royal road buff.").
					define("renderAstro", true);
			renderRoyalRoad = builder.
					translation("Render Royal Road Text").
					comment("Enables the text render for the Astro UI Royal Road. If disabled, only the icon will render.").
					define("renderRoyalRoad", true);
			renderTarget = builder.
					translation("Render Target UI").
					comment("Enables the targetting UI renderer.").
					define("renderTarget", true);
			targetOpacity = builder.
					translation("Target UI Opcaity").
					comment("How transparent the target ui is.").
					defineInRange("targetOpacity", 0.25F, 0, 1);
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

	public static void setupCenteredWear() {
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
			if (item == null || item instanceof ItemAir)
				continue;
			list.add(item);
		}
		CENTERED_WEAR = ImmutableSet.copyOf(list);
	}

	private static void update() {
		setupCenteredWear();
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(AoV.MODID))
			update();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading event) {
		if (event.getConfig().getModId().equals(AoV.MODID)) {
			AoV.config.file = (ConfigWrapper) event.getConfig().getConfigData();
			update();
		}
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfig.ConfigReloading event) {
		if (event.getConfig().getModId().equals(AoV.MODID))
			update();
	}

	public static class ElementPositions {

		public ForgeConfigSpec.ConfigValue<Integer> spellbar_x;
		public ForgeConfigSpec.ConfigValue<Integer> spellbar_y;

		public ForgeConfigSpec.ConfigValue<Integer> astro_x;
		public ForgeConfigSpec.ConfigValue<Integer> astro_y;

		public ForgeConfigSpec.ConfigValue<Integer> target_x;
		public ForgeConfigSpec.ConfigValue<Integer> target_y;

	}

	public static class Earthquake {

		public ForgeConfigSpec.BooleanValue enable;
		public ForgeConfigSpec.BooleanValue shake;
		public ForgeConfigSpec.BooleanValue air;
		public ForgeConfigSpec.IntValue ticks;
		public ForgeConfigSpec.IntValue chance;
		public ForgeConfigSpec.ConfigValue<List<? extends String>> destruction;

	}

}
