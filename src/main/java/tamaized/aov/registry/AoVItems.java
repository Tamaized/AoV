package tamaized.aov.registry;

import tamaized.aov.common.items.DebugItem;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.registry.ITamRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class AoVItems {

	public static DebugItem debugger;
	private static List<ITamRegistry> modelList;

	static {
		modelList = new ArrayList<>();

		modelList.add(debugger = new DebugItem("debugger"));
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (ITamRegistry b : modelList)
			b.registerItem(event);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for (ITamRegistry model : modelList)
			model.registerModel(event);
	}

}
