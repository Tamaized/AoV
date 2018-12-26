package tamaized.aov.registry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tamaized.aov.AoV;
import tamaized.aov.common.items.DebugItem;
import tamaized.aov.common.items.Handwraps;
import tamaized.tammodized.registry.ITamRegistry;

@GameRegistry.ObjectHolder(AoV.modid)
@Mod.EventBusSubscriber(modid = AoV.modid)
public class AoVItems {

	public static final Item debugger = Items.AIR;
	public static final Item handwraps = Items.AIR;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(

				new DebugItem("debugger"),

				new Handwraps("handwraps")

		);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		registerModels(event,

				debugger,

				handwraps

		);
	}

	private static void registerModels(ModelRegistryEvent event, Item... items) {
		for (Item item : items)
			if (item instanceof ITamRegistry)
				((ITamRegistry) item).registerModel(event);
	}

}
