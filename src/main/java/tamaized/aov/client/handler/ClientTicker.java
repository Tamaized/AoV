package tamaized.aov.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tamaized.aov.AoV;
import tamaized.aov.proxy.ClientProxy;

@Mod.EventBusSubscriber(modid = AoV.modid)
public class ClientTicker {

	public static final int dangerBiomeMaxTick = 20 * 10;
	public static TickerDataCharges charges = new TickerDataCharges();
	public static int dangerBiomeTicks;
	public static boolean dangerBiomeTicksFlag;
	public static float frames;

	@SubscribeEvent
	public static void update(TickEvent.ClientTickEvent event) {
		if (Minecraft.getMinecraft().isGamePaused() || event.phase != TickEvent.Phase.START)
			return;
		frames++;
		charges.update();
		if (ClientProxy.getTarget() != null && !ClientProxy.getTarget().isEntityAlive())
			ClientProxy.setTarget(null);
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
