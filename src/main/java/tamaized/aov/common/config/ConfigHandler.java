package tamaized.aov.common.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.aov.AoV;

@Mod.EventBusSubscriber
@Config(modid = AoV.modid)
public class ConfigHandler {

	@Config.Name("Element Positions")
	@Config.Comment("The XY positions of on screen elements from the mod")
	public static ElementPositions elementPositions = new ElementPositions();

	@Config.Name("Max Level")
	@Config.Comment("Sets the maximum level")
	public static int maxlevel = 15;
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

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(AoV.modid)) {
			ConfigManager.sync(AoV.modid, Config.Type.INSTANCE);
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

}
