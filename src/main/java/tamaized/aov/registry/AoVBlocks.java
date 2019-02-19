package tamaized.aov.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import tamaized.aov.AoV;
import tamaized.aov.common.blocks.BlockAngelicBlock;

@ObjectHolder(AoV.MODID)
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

				assign(new BlockAngelicBlock(BlockAngelicBlock.ClassType.ALL, Block.Properties.create(Material.ROCK).hardnessAndResistance(7F).hardnessAndResistance(7F)), "angelicstatue"),

				assign(new BlockAngelicBlock(BlockAngelicBlock.ClassType.FAVOREDSOUL, Block.Properties.create(Material.ROCK).hardnessAndResistance(7F)), "favoredsoulstatue"),

				assign(new BlockAngelicBlock(BlockAngelicBlock.ClassType.CLERIC, Block.Properties.create(Material.ROCK).hardnessAndResistance(7F)), "clericstatue"),

				assign(new BlockAngelicBlock(BlockAngelicBlock.ClassType.PALADIN, Block.Properties.create(Material.ROCK).hardnessAndResistance(7F)), "paladinstatue"),

				assign(new BlockAngelicBlock(BlockAngelicBlock.ClassType.ASTRO, Block.Properties.create(Material.ROCK).hardnessAndResistance(7F)), "astrostatue"),

				assign(new BlockAngelicBlock(BlockAngelicBlock.ClassType.DRUID, Block.Properties.create(Material.ROCK).hardnessAndResistance(7F)), "druidstatue")

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

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		registerModel(angelicstatue);
		registerModel(favoredsoulstatue, "favoredsoul", "angelicstatue");
		registerModel(clericstatue, "cleric", "angelicstatue");
		registerModel(paladinstatue, "paladin", "angelicstatue");
		registerModel(astrostatue, "astro", "angelicstatue");
		registerModel(druidstatue, "druid", "angelicstatue");
	}

	private static Block assign(Block block, String name) {
		return block.
				setRegistryName(AoV.MODID, name);
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
				registry.register(new ItemBlock(block, new Item.Properties().setNoRepair().group(AoVTabs.tabAoV)).setRegistryName(block.getRegistryName()));
	}

	private static void registerModel(Block block, String... variant) {
		if (block.getRegistryName() == null)
			return;
		/*ModelLoader.setCustomModelResourceLocation(

				Item.getItemFromBlock(block),

				new ModelResourceLocation(

						new ResourceLocation(block.getRegistryName().getNamespace(), variant.length > 1 ? variant[1] : block.getRegistryName().getPath()),

						variant.length > 0 ? variant[0] : "normal"

				)

		);*/
	}

}
