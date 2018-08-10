package tamaized.aov.client.handler;

import tamaized.aov.proxy.ClientProxy;

public class ClientTicker {

	public static TickerDataCharges charges = new TickerDataCharges();

	public static void update() {
		charges.update();
		if (ClientProxy.getTarget() != null && ClientProxy.getTarget().isDead)
			ClientProxy.setTarget(null);
	}

}
