package Tamaized.AoV.common.client;

import Tamaized.AoV.common.client.tickerData.TickerDataCharges;

public class ClientTicker {

	public static TickerDataCharges charges = new TickerDataCharges();

	public static void update() {
		charges.update();
	}

}
