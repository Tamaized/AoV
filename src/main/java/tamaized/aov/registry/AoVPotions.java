package tamaized.aov.registry;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.aov.common.potion.PotionAid;
import tamaized.aov.common.potion.PotionBalance;
import tamaized.aov.common.potion.PotionEwer;
import tamaized.aov.common.potion.PotionSlowFall;
import tamaized.aov.common.potion.PotionSpear;
import tamaized.aov.common.potion.PotionSpire;
import tamaized.aov.common.potion.PotionStalwartPact;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class AoVPotions {

	public static Potion aid;
	public static Potion shieldOfFaith;
	public static Potion zeal;
	public static Potion stalwartPact;
	public static Potion slowFall;
	public static Potion spear;
	public static Potion ewer;
	public static Potion spire;
	public static Potion balance;
	private static List<Potion> potionList;

	static {
		potionList = new ArrayList<>();

		potionList.add(aid = new PotionAid("aid"));
		potionList.add(shieldOfFaith = new PotionAid("faith"));
		potionList.add(zeal = new PotionAid("zeal"));
		potionList.add(stalwartPact = new PotionStalwartPact("stalwart"));

		potionList.add(slowFall = new PotionSlowFall("slowfall"));

		potionList.add(spear = new PotionSpear("spear"));
		potionList.add(ewer = new PotionEwer("ewer"));
		potionList.add(spire = new PotionSpire("spire"));
		potionList.add(balance = new PotionBalance("balance"));
	}

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> event) {
		for (Potion p : potionList)
			event.getRegistry().register(p);
	}

}
