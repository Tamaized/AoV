package Tamaized.AoV.registry;

import Tamaized.AoV.potion.PotionAid;
import Tamaized.AoV.potion.PotionSlowFall;
import Tamaized.AoV.potion.PotionStalwartPact;
import net.minecraft.potion.Potion;

public class AoVPotions {

	public static Potion aid;
	public static Potion shieldOfFaith;
	public static Potion zeal;
	public static Potion stalwartPact;

	public static Potion slowFall;

	static {
		aid = new PotionAid("aid");
		shieldOfFaith = new PotionAid("faith");
		zeal = new PotionAid("zeal");
		stalwartPact = new PotionStalwartPact("stalwart");

		slowFall = new PotionSlowFall("slowfall");
	}

}
