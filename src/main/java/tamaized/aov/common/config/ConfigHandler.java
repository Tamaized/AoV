package tamaized.aov.common.config;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.aov.IAoVCapability.ItemStackWrapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber
@Config(modid = AoV.modid)
public class ConfigHandler {

	@Config.Name("Element Positions")
	@Config.Comment("The XY positions of on screen elements from the mod")
	public static ElementPositions elementPositions = new ElementPositions();

	@Config.Name("Earthquake")
	@Config.Comment("Manages the Earthquake Spell Block Destruction")
	public static Earthquake earthquake = new Earthquake();

	@Config.Name("Max Level")
	@Config.Comment("Sets the maximum level")
	public static int maxlevel = 15;

	@Config.Name("Speedy Handwraps")
	@Config.Comment("Handwraps add +6 to base attack speed")
	public static boolean handwrapsSpeed = true;

	@Config.Name("Recharge Delay")
	@Config.Comment("Sets the recharge rate per tick, -1 disables this")
	public static int recharge = -1;

	@Config.Name("Enable Vanilla Experience gain")
	@Config.Comment("Determines whether or not vanilla experience contributes to AoV experience gain")
	public static boolean experience = false;

	@Config.Name("Render SpellBar Over HotBar")
	@Config.Comment("Sets the Spellbar to render in place of the hotbar while active.")
	public static boolean renderBarOverHotbar = false;

	@Config.Name("Render Charges Above SpellBar")
	@Config.Comment("Renders the Charges left to be above the Spellbar instead of below. This setting has no impact if Render SpellBar Over HotBar is enabled.")
	public static boolean renderChargesAboveSpellbar = false;

	@Config.Name("Render Astro UI while Empty")
	@Config.Comment("If false, the Astro UI will no longer render while not holding any card, spread, or royal road buff.")
	public static boolean renderAstro = true;

	@Config.Name("Render Royal Road Text")
	@Config.Comment("Enables the text render for the Astro UI Royal Road. If disabled, only the icon will render.")
	public static boolean renderRoyalRoad = true;

	@Config.Name("Render Target UI")
	@Config.Comment("Enables the targetting UI renderer.")
	public static boolean renderTarget = true;

	@Config.Name("Target UI Opcaity")
	@Config.Comment("How transparent the target ui is.")
	@Config.RangeDouble(min = 0, max = 1)
	public static float targetOpacity = 0.25F;

	@Config.Name("Centered Wear")
	@Config.Comment("domain:name:meta\ndomain defaults to `minecraft`\nmeta is optional\ndomain is required if meta is specified")
	public static String[] centered = new String[]{

			AoV.modid + ":handwraps",

			getRegName(Items.WOODEN_SWORD),

			getRegName(Items.WOODEN_AXE),

			getRegName(Items.WOODEN_HOE),

			getRegName(Items.WOODEN_PICKAXE),

			getRegName(Items.WOODEN_SHOVEL),

			getRegName(Items.LEATHER_BOOTS),

			getRegName(Items.LEATHER_CHESTPLATE),

			getRegName(Items.LEATHER_HELMET),

			getRegName(Items.LEATHER_LEGGINGS)

	};
	@Config.Ignore
	public static Set<ItemStackWrapper> CENTERED_WEAR = ImmutableSet.of();

	private static String getRegName(Item item) {
		return Objects.requireNonNull(item.getRegistryName()).getPath();
	}

	public static void setupCenteredWear() {
		List<ItemStackWrapper> list = Lists.newArrayList();
		for (String next : centered) {
			String[] split = next.split(":");
			String domain = "minecraft";
			String regname = split[0];
			int meta = 0;
			boolean hasmeta = false;
			if (split.length > 1) {
				domain = split[0];
				regname = split[1];
				if (split.length > 2)
					try {
						meta = Integer.parseInt(split[2]);
						hasmeta = true;
					} catch (NumberFormatException e) {
						hasmeta = false;
					}
			}
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(domain, regname));
			if (item == null || item instanceof ItemAir)
				continue;
			list.add(hasmeta ? new ItemStackWrapper(item, meta) : new ItemStackWrapper(item));
		}
		CENTERED_WEAR = ImmutableSet.copyOf(list);
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(AoV.modid)) {
			ConfigManager.sync(AoV.modid, Config.Type.INSTANCE);
			setupCenteredWear();
		}
	}

	public static class ElementPositions {

		@Config.Name("SpellBar X")
		public int spellbar_x = 0;
		@Config.Name("SpellBar Y")
		public int spellbar_y = 0;

		@Config.Name("Astro X")
		public int astro_x = 0;
		@Config.Name("Astro Y")
		public int astro_y = 0;

		@Config.Name("Target X")
		public int target_x = 0;
		@Config.Name("Target Y")
		public int target_y = 0;

	}

	public static class Earthquake {

		@Config.Name("Enable")
		public boolean enable = true;

		@Config.Name("Enable Screen Shaking")
		public boolean shake = true;

		@Config.Name("Enable Air")
		@Config.Comment("If Disabled, destruction stops at the last block rather than setting to air")
		public boolean air = true;

		@Config.Name("Destruction Ticks")
		@Config.Comment("Amount of Ticks to wait until the next Destruction; Lower = Sooner")
		@Config.RangeInt(min = 1)
		public int ticks = 5;

		@Config.Name("Destruction Chance")
		@Config.Comment("Chance that a Destruction will take place; Lower = Higher Chance")
		@Config.RangeInt(min = 1)
		public int chance = 5;

		@Config.Name("Destruction Order")
		@Config.Comment("domain:name:meta|other\n" +

				"meta is optional\n" +

				"[other] is what CAN be broken down but won't be broken down into.\n\n" +


				"Example: [minecraft:gravel|minecraft:grass] may have Cobble before it, so Cobble breaks down into Gravel, which may have Dirt after it so Gravel breaks down into Dirt.\n" +

				"Grass will also break down into Dirt but Cobble will never break down into Grass.")
		public String[] destruction = new String[]{

				"minecraft:stone",

				"minecraft:cobblestone",

				"minecraft:gravel|minecraft:grass",

				"minecraft:dirt",

				"minecraft:sand"

		};

	}

}
