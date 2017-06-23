package Tamaized.AoV.handler;

import Tamaized.AoV.gui.client.AoVUIBar;
import Tamaized.AoV.proxy.ClientProxy;

public class TickerDataCharges {

	public int id_0 = 0;
	public int id_1 = 0;
	public int id_2 = 0;
	public int id_3 = 0;
	public int id_4 = 0;
	public int id_5 = 0;
	public int id_6 = 0;
	public int id_7 = 0;
	public int id_8 = 0;
	public int id_9 = 0;

	private final int max = 14;

	public int getValue(int id) {
		switch (id) {
			case 0:
				return id_0;
			case 1:
				return id_1;
			case 2:
				return id_2;
			case 3:
				return id_3;
			case 4:
				return id_4;
			case 5:
				return id_5;
			case 6:
				return id_6;
			case 7:
				return id_7;
			case 8:
				return id_8;
			case 9:
				return id_9;
			default:
				return 0;
		}
	}

	public void update() {
		switch (AoVUIBar.slotLoc) {
			case 0:
				if (id_0 < max) id_0++;
				if (id_1 > 0) id_1--;
				if (id_2 > 0) id_2--;
				if (id_3 > 0) id_3--;
				if (id_4 > 0) id_4--;
				if (id_5 > 0) id_5--;
				if (id_6 > 0) id_6--;
				if (id_7 > 0) id_7--;
				if (id_8 > 0) id_8--;
				if (id_9 > 0) id_9--;
				break;
			case 1:
				if (id_0 > 0) id_0--;
				if (id_1 < max) id_1++;
				if (id_2 > 0) id_2--;
				if (id_3 > 0) id_3--;
				if (id_4 > 0) id_4--;
				if (id_5 > 0) id_5--;
				if (id_6 > 0) id_6--;
				if (id_7 > 0) id_7--;
				if (id_8 > 0) id_8--;
				if (id_9 > 0) id_9--;
				break;
			case 2:
				if (id_0 > 0) id_0--;
				if (id_1 > 0) id_1--;
				if (id_2 < max) id_2++;
				if (id_3 > 0) id_3--;
				if (id_4 > 0) id_4--;
				if (id_5 > 0) id_5--;
				if (id_6 > 0) id_6--;
				if (id_7 > 0) id_7--;
				if (id_8 > 0) id_8--;
				if (id_9 > 0) id_9--;
				break;
			case 3:
				if (id_0 > 0) id_0--;
				if (id_1 > 0) id_1--;
				if (id_2 > 0) id_2--;
				if (id_3 < max) id_3++;
				if (id_4 > 0) id_4--;
				if (id_5 > 0) id_5--;
				if (id_6 > 0) id_6--;
				if (id_7 > 0) id_7--;
				if (id_8 > 0) id_8--;
				if (id_9 > 0) id_9--;
				break;
			case 4:
				if (id_0 > 0) id_0--;
				if (id_1 > 0) id_1--;
				if (id_2 > 0) id_2--;
				if (id_3 > 0) id_3--;
				if (id_4 < max) id_4++;
				if (id_5 > 0) id_5--;
				if (id_6 > 0) id_6--;
				if (id_7 > 0) id_7--;
				if (id_8 > 0) id_8--;
				if (id_9 > 0) id_9--;
				break;
			case 5:
				if (id_0 > 0) id_0--;
				if (id_1 > 0) id_1--;
				if (id_2 > 0) id_2--;
				if (id_3 > 0) id_3--;
				if (id_4 > 0) id_4--;
				if (id_5 < max) id_5++;
				if (id_6 > 0) id_6--;
				if (id_7 > 0) id_7--;
				if (id_8 > 0) id_8--;
				if (id_9 > 0) id_9--;
				break;
			case 6:
				if (id_0 > 0) id_0--;
				if (id_1 > 0) id_1--;
				if (id_2 > 0) id_2--;
				if (id_3 > 0) id_3--;
				if (id_4 > 0) id_4--;
				if (id_5 > 0) id_5--;
				if (id_6 < max) id_6++;
				if (id_7 > 0) id_7--;
				if (id_8 > 0) id_8--;
				if (id_9 > 0) id_9--;
				break;
			case 7:
				if (id_0 > 0) id_0--;
				if (id_1 > 0) id_1--;
				if (id_2 > 0) id_2--;
				if (id_3 > 0) id_3--;
				if (id_4 > 0) id_4--;
				if (id_5 > 0) id_5--;
				if (id_6 > 0) id_6--;
				if (id_7 < max) id_7++;
				if (id_8 > 0) id_8--;
				if (id_9 > 0) id_9--;
				break;
			case 8:
				if (id_0 > 0) id_0--;
				if (id_1 > 0) id_1--;
				if (id_2 > 0) id_2--;
				if (id_3 > 0) id_3--;
				if (id_4 > 0) id_4--;
				if (id_5 > 0) id_5--;
				if (id_6 > 0) id_6--;
				if (id_7 > 0) id_7--;
				if (id_8 < max) id_8++;
				if (id_9 > 0) id_9--;
				break;
			case 9:
				if (id_0 > 0) id_0--;
				if (id_1 > 0) id_1--;
				if (id_2 > 0) id_2--;
				if (id_3 > 0) id_3--;
				if (id_4 > 0) id_4--;
				if (id_5 > 0) id_5--;
				if (id_6 > 0) id_6--;
				if (id_7 > 0) id_7--;
				if (id_8 > 0) id_8--;
				if (id_9 < max) id_9++;
				break;
			default:
				if (id_0 > 0) id_0--;
				if (id_1 > 0) id_1--;
				if (id_2 > 0) id_2--;
				if (id_3 > 0) id_3--;
				if (id_4 > 0) id_4--;
				if (id_5 > 0) id_5--;
				if (id_6 > 0) id_6--;
				if (id_7 > 0) id_7--;
				if (id_8 > 0) id_8--;
				if (id_9 > 0) id_9--;
				break;
		}
	}

}
