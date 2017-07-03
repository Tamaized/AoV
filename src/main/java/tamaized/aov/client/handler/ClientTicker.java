package tamaized.aov.client.handler;

public class ClientTicker {

	public static TickerDataCharges charges = new TickerDataCharges();

	public static void update() {
		charges.update();
	}

}
