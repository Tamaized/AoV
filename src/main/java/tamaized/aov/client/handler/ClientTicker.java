package tamaized.aov.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tamaized.aov.AoV;
import tamaized.aov.client.ClientHelpers;

@Mod.EventBusSubscriber(modid = AoV.MODID)
public class ClientTicker {

	public static final int dangerBiomeMaxTick = 20 * 10;
	public static TickerDataCharges charges = new TickerDataCharges();
	public static int dangerBiomeTicks;
	public static boolean dangerBiomeTicksFlag;
	public static float frames;

	@SubscribeEvent
	public static void update(TickEvent.ClientTickEvent event) {
		if (Minecraft.getInstance().isGamePaused() || event.phase != TickEvent.Phase.START)
			return;
		frames++;
		charges.update();
		if (ClientHelpers.getTarget() != null && !ClientHelpers.getTarget().isAlive())
			ClientHelpers.setTarget(null);
		{
			if (dangerBiomeTicksFlag) {
				if (dangerBiomeTicks < dangerBiomeMaxTick)
					dangerBiomeTicks++;
			} else if (dangerBiomeTicks > 0) {
				dangerBiomeTicks--;
			}
		}
	}

}
