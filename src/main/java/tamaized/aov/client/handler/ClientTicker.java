package tamaized.aov.client.handler;

import tamaized.aov.proxy.ClientProxy;

public class ClientTicker {

	public static TickerDataCharges charges = new TickerDataCharges();
	public static int dangerBiomeTicks;
	public static final int dangerBiomeMaxTick = 20 * 10;
	public static boolean dangerBiomeTicksFlag;

	public static void update() {
		charges.update();
		if (ClientProxy.getTarget() != null && ClientProxy.getTarget().isDead)
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
