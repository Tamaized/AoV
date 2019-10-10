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

public class AoVPotions {

	static final DeferredRegister<Effect> REGISTRY = new DeferredRegister<>(ForgeRegistries.POTIONS, AoV.MODID);

	public static RegistryObject<Effect> aid = REGISTRY.register("aid", PotionAid::new);
	public static RegistryObject<Effect> shieldOfFaith = REGISTRY.register("shieldoffaith", PotionAid::new);
	public static RegistryObject<Effect> zeal = REGISTRY.register("zeal", PotionAid::new);
	public static RegistryObject<Effect> stalwartPact = REGISTRY.register("stalwartpact", PotionStalwartPact::new);
	public static RegistryObject<Effect> slowFall = REGISTRY.register("slowfall", PotionSlowFall::new);
	public static RegistryObject<Effect> spear = REGISTRY.register("spear", PotionSpear::new);
	public static RegistryObject<Effect> ewer = REGISTRY.register("ewer", PotionEwer::new);
	public static RegistryObject<Effect> spire = REGISTRY.register("spire", PotionSpire::new);
	public static RegistryObject<Effect> balance = REGISTRY.register("balance", PotionBalance::new);
	public static RegistryObject<Effect> naturesBounty = REGISTRY.register("naturesbounty", PotionNaturesBounty::new);
	public static RegistryObject<Effect> coldChill = REGISTRY.register("coldchill", PotionColdChill::new);

	public static void register(IEventBus mod) {
		REGISTRY.register(mod);
	}

}
