package tamaized.aov.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import tamaized.aov.AoV;
import tamaized.aov.common.blocks.BlockAngelicBlock;

@GameRegistry.ObjectHolder(AoV.MODID)
@Mod.EventBusSubscriber(modid = AoV.MODID)
public class AoVBlocks {

	public static final Block angelicstatue = Blocks.AIR;
	public static final Block favoredsoulstatue = Blocks.AIR;
	public static final Block clericstatue = Blocks.AIR;
	public static final Block paladinstatue = Blocks.AIR;
	public static final Block astrostatue = Blocks.AIR;
	public static final Block druidstatue = Blocks.AIR;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(

				new BlockAngelicBlock(BlockAngelicBlock.ClassType.ALL, AoVTabs.tabAoV, Material.ROCK, "angelicstatue", 7.0F),

				new BlockAngelicBlock(BlockAngelicBlock.ClassType.FAVOREDSOUL, AoVTabs.tabAoV, Material.ROCK, "favoredsoulstatue", 7.0F),

				new BlockAngelicBlock(BlockAngelicBlock.ClassType.CLERIC, AoVTabs.tabAoV, Material.ROCK, "clericstatue", 7.0F),

				new BlockAngelicBlock(BlockAngelicBlock.ClassType.PALADIN, AoVTabs.tabAoV, Material.ROCK, "paladinstatue", 7.0F),

				new BlockAngelicBlock(BlockAngelicBlock.ClassType.ASTRO, AoVTabs.tabAoV, Material.ROCK, "astrostatue", 7.0F),

				new BlockAngelicBlock(BlockAngelicBlock.ClassType.DRUID, AoVTabs.tabAoV, Material.ROCK, "druidstatue", 7.0F)

		);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		registerItemBlocks(event.getRegistry(),

				angelicstatue,

				favoredsoulstatue,

				clericstatue,

				paladinstatue,

				astrostatue,

				druidstatue

		);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		registerModel(angelicstatue, 0);
		registerModel(favoredsoulstatue, 0, "favoredsoul", "angelicstatue");
		registerModel(clericstatue, 0, "cleric", "angelicstatue");
		registerModel(paladinstatue, 0, "paladin", "angelicstatue");
		registerModel(astrostatue, 0, "astro", "angelicstatue");
		registerModel(druidstatue, 0, "druid", "angelicstatue");
	}

	@SubscribeEvent
	public static void fixBlockMappings(RegistryEvent.MissingMappings<Block> event) {
		for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getMappings())
			if (mapping.key.getNamespace().equals(AoV.MODID) && mapping.key.getPath().equals("blockangelic"))
				mapping.remap(angelicstatue);
	}

	@SubscribeEvent
	public static void fixItemMappings(RegistryEvent.MissingMappings<Item> event) {
		for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getMappings())
			if (mapping.key.getNamespace().equals(AoV.MODID) && mapping.key.getPath().equals("blockangelic"))
				mapping.remap(Item.getItemFromBlock(angelicstatue));
	}

	private static void registerItemBlocks(IForgeRegistry<Item> registry, Block... blocks) {
		for (Block block : blocks)
			if (block.getRegistryName() != null)
				registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	private static void registerModel(Block block, int meta, String... variant) {
		if (block.getRegistryName() == null)
			return;
		ModelLoader.setCustomModelResourceLocation(

				Item.getItemFromBlock(block),

				meta,

				new ModelResourceLocation(

						new ResourceLocation(block.getRegistryName().getNamespace(), variant.length > 1 ? variant[1] : block.getRegistryName().getPath()),

						variant.length > 0 ? variant[0] : "normal"

				)

		);
	}

}
