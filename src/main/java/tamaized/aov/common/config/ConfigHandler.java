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

	@Config.Name("Recharge Delay")
	@Config.Comment("Sets the recharge rate per tick, -1 disables this")
	public static int recharge = -1;
	@Config.Name("Enable Vanilla Experience gain")
	@Config.Comment("Determines whether or not vanilla experience contributes to AoV experience gain")
	public static boolean experience = false;
	@Config.Name("Render SpellBar Over HotBar")
	@Config.Comment("Sets the Spellbar to render in place of the hotbar while active.")
	public static boolean renderBarOverHotbar = false;
	@Config.Name("Render Astro UI while Empty")
	@Config.Comment("If false, the Astro UI will no longer render while not holding any card, spread, or royal road buff.")
	public static boolean renderAstro = true;
	@Config.Name("Render Royal Road Text")
	@Config.Comment("Enables the text render for the Astro UI Royal Road. If disabled, only the icon will render.")
	public static boolean renderRoyalRoad = true;

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

	}

}
