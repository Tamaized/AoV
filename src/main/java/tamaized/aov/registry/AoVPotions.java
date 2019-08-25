package tamaized.aov.registry;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import tamaized.aov.AoV;
import tamaized.aov.common.potion.PotionAid;
import tamaized.aov.common.potion.PotionBalance;
import tamaized.aov.common.potion.PotionColdChill;
import tamaized.aov.common.potion.PotionEwer;
import tamaized.aov.common.potion.PotionNaturesBounty;
import tamaized.aov.common.potion.PotionSlowFall;
import tamaized.aov.common.potion.PotionSpear;
import tamaized.aov.common.potion.PotionSpire;
import tamaized.aov.common.potion.PotionStalwartPact;

@ObjectHolder(AoV.MODID)
@Mod.EventBusSubscriber(modid = AoV.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AoVPotions {

	public static Effect aid;
	public static Effect shieldOfFaith;
	public static Effect zeal;
	public static Effect stalwartPact;
	public static Effect slowFall;
	public static Effect spear;
	public static Effect ewer;
	public static Effect spire;
	public static Effect balance;
	public static Effect naturesBounty;
	public static Effect coldChill;

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Effect> event) {
		event.getRegistry().registerAll(

				aid = new PotionAid("aid"),

				shieldOfFaith = new PotionAid("shieldoffaith"),

				zeal = new PotionAid("zeal"),

				stalwartPact = new PotionStalwartPact("stalwartpact"),

				slowFall = new PotionSlowFall("slowfall"),

				spear = new PotionSpear("spear"),

				ewer = new PotionEwer("ewer"),

				spire = new PotionSpire("spire"),

				balance = new PotionBalance("balance"),

				naturesBounty = new PotionNaturesBounty("naturesbounty"),

				coldChill = new PotionColdChill("coldchill")

		);
	}

}
