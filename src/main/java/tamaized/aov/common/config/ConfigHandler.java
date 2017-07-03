package tamaized.aov.common.config;

import tamaized.aov.AoV;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Config(modid = AoV.modid)
public class ConfigHandler {

	@Config.Comment("Sets the recharge rate per second, -1 disables this")
	public static int recharge = -1;
	@Config.Comment("Determines whether or not vanilla experience contributes to tamaized.aov.AoV experience gain")
	public static boolean experience = false;
	@Config.Comment("Sets the Posiiton where the Spell Bar will render")
	public static BarPos barPos = BarPos.TOP;

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(AoV.modid)) {
			ConfigManager.sync(AoV.modid, Config.Type.INSTANCE);
		}
	}

	public static enum BarPos {
		TOP, BOTTOM
	}

}
