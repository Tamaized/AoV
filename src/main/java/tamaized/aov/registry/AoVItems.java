package tamaized.aov.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tamaized.aov.AoV;
import tamaized.aov.common.items.DebugItem;
import tamaized.aov.common.items.Handwraps;

public class AoVItems {

	static final DeferredRegister<Item> REGISTRY = new DeferredRegister<>(ForgeRegistries.ITEMS, AoV.MODID);

	public static final RegistryObject<Item> debugger = REGISTRY.register("debugger", () -> new DebugItem(prop()));
	public static final RegistryObject<Item> handwraps = REGISTRY.register("handwraps", () -> new Handwraps(ItemTier.DIAMOND, 1, 1, prop()));

	private static Item.Properties prop() {
		return new Item.Properties().group(AoVTabs.tabAoV).maxStackSize(1);
	}

	public static void register(IEventBus mod) {
		REGISTRY.register(mod);
	}

}
