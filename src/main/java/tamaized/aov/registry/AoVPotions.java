package tamaized.aov.registry;

import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
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

import java.lang.reflect.InvocationTargetException;

public class AoVPotions {

	static final DeferredRegister<Effect> REGISTRY = new DeferredRegister<>(ForgeRegistries.POTIONS, AoV.MODID);

	public static RegistryObject<Effect> aid = make("aid", PotionAid.class);
	public static RegistryObject<Effect> shieldOfFaith = make("shieldoffaith", PotionAid.class);
	public static RegistryObject<Effect> zeal = make("zeal", PotionAid.class);
	public static RegistryObject<Effect> stalwartPact = make("stalwartpact", PotionStalwartPact.class);
	public static RegistryObject<Effect> slowFall = make("slowfall", PotionSlowFall.class);
	public static RegistryObject<Effect> spear = make("spear", PotionSpear.class);
	public static RegistryObject<Effect> ewer = make("ewer", PotionEwer.class);
	public static RegistryObject<Effect> spire = make("spire", PotionSpire.class);
	public static RegistryObject<Effect> balance = make("balance", PotionBalance.class);
	public static RegistryObject<Effect> naturesBounty = make("naturesbounty", PotionNaturesBounty.class);
	public static RegistryObject<Effect> coldChill = make("coldchill", PotionColdChill.class);

	public static void register(IEventBus mod) {
		REGISTRY.register(mod);
	}

	private static RegistryObject<Effect> make(String name, Class<? extends Effect> type) {
		return REGISTRY.register(name, () -> {
			try {
				return type.getConstructor(String.class).newInstance(name);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
				return null;
			}
		});
	}

}
