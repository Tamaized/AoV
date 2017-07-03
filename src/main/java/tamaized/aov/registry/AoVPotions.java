package tamaized.aov.registry;

import tamaized.aov.common.potion.PotionAid;
import tamaized.aov.common.potion.PotionSlowFall;
import tamaized.aov.common.potion.PotionStalwartPact;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class AoVPotions {

	public static Potion aid;
	public static Potion shieldOfFaith;
	public static Potion zeal;
	public static Potion stalwartPact;
	public static Potion slowFall;
	private static List<Potion> potionList;

	static {
		potionList = new ArrayList<>();

		potionList.add(aid = new PotionAid("aid"));
		potionList.add(shieldOfFaith = new PotionAid("faith"));
		potionList.add(zeal = new PotionAid("zeal"));
		potionList.add(stalwartPact = new PotionStalwartPact("stalwart"));

		potionList.add(slowFall = new PotionSlowFall("slowfall"));
	}

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> event) {
		for (Potion p : potionList)
			event.getRegistry().register(p);
	}

}
