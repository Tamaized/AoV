package tamaized.aov.registry;

import tamaized.aov.AoV;
import tamaized.aov.common.blocks.BlockAngelicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

@Mod.EventBusSubscriber(modid = AoV.modid)
public class AoVBlocks {

	public static BlockAngelicBlock angelicBlock;
	private static List<ITamRegistry> modelList;

	static {
		modelList = new ArrayList<>();

		modelList.add(angelicBlock = new BlockAngelicBlock(AoVTabs.tabAoV, Material.ROCK, "blockangelic", 7.0F));
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (ITamRegistry b : modelList)
			b.registerBlock(event);
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
