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
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import tamaized.aov.AoV;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AoV.MODID)
public class ClientConfig {

	public ConfigWrapper file;
	public ElementPositions ELEMENT_POSITIONS = new ElementPositions();
	public Earthquake EARTHQUAKE = new Earthquake();
	public ForgeConfigSpec.BooleanValue renderBarOverHotbar;
	public ForgeConfigSpec.BooleanValue renderChargesAboveSpellbar;
	public ForgeConfigSpec.BooleanValue renderAstro;
	public ForgeConfigSpec.BooleanValue renderRoyalRoad;
	public ForgeConfigSpec.BooleanValue renderTarget;
	public ForgeConfigSpec.IntValue stencil;
	public ForgeConfigSpec.DoubleValue targetOpacity;
	public static boolean LOADED = false;

	public ClientConfig(ForgeConfigSpec.Builder builder) {
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
			EARTHQUAKE.shake = builder.
					comment("Enable Screen Shaking").
					define("shake", true);
		}
		builder.pop();
		{
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
			stencil = builder.
					translation("Starting Stencil Buffer ref Value").
					comment("Increase this if you experience weird render issues with this mod and other mods.").
					defineInRange("stencil", 100, 0, Integer.MAX_VALUE);
		}
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading event) {
		if (event.getConfig().getModId().equals(AoV.MODID)) {
			AoV.config.file = (ConfigWrapper) event.getConfig().getConfigData();
		}
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

		public ForgeConfigSpec.BooleanValue shake;

	}

}
