package tamaized.aov.registry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import tamaized.aov.AoV;
import tamaized.aov.common.items.DebugItem;
import tamaized.aov.common.items.Handwraps;

@ObjectHolder(AoV.MODID)
@Mod.EventBusSubscriber(modid = AoV.MODID)
public class AoVItems {

	public static final Item debugger = Items.AIR;
	public static final Item handwraps = Items.AIR;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(

				assign(new DebugItem(new Item.Properties().maxStackSize(1).group(AoVTabs.tabAoV)), "debugger"),

				assign(new Handwraps(ItemTier.DIAMOND, 1, 1, new Item.Properties().maxStackSize(1).group(AoVTabs.tabAoV)), "handwraps")

		);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		registerModel(debugger);
		registerModel(handwraps);
	}

	private static void registerModel(Item item, String... variant) {
		if (item.getRegistryName() == null)
			return;
		/*ModelLoader.setCustomModelResourceLocation(

				Item.getItemFromBlock(item),

				new ModelResourceLocation(

						new ResourceLocation(item.getRegistryName().getNamespace(), variant.length > 1 ? variant[1] : item.getRegistryName().getPath()),

						variant.length > 0 ? variant[0] : "normal"

				)

		);*/
	}

	private static Item assign(Item item, String name) {
		return item.
				setRegistryName(AoV.MODID, name);
	}

}
