package tamaized.aov.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tamaized.aov.AoV;
import tamaized.aov.common.blocks.BlockAngelicBlock;

import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = AoV.MODID)
public class AoVBlocks {

	static final DeferredRegister<Block> REGISTRY = new DeferredRegister<>(ForgeRegistries.BLOCKS, AoV.MODID);

	public static final RegistryObject<Block> angelicstatue = withItemBlock("angelicstatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.ALL, prop()));
	public static final RegistryObject<Block> favoredsoulstatue = withItemBlock("favoredsoulstatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.FAVOREDSOUL, prop()));
	public static final RegistryObject<Block> clericstatue = withItemBlock("clericstatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.CLERIC, prop()));
	public static final RegistryObject<Block> paladinstatue = withItemBlock("paladinstatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.PALADIN, prop()));
	public static final RegistryObject<Block> astrostatue = withItemBlock("astrostatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.ASTRO, prop()));
	public static final RegistryObject<Block> druidstatue = withItemBlock("druidstatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.DRUID, prop()));

	public static void register(IEventBus mod) {
		REGISTRY.register(mod);
	}

	private static Block.Properties prop() {
		return Block.Properties.create(Material.ROCK).hardnessAndResistance(7F).hardnessAndResistance(7F);
	}

	private static RegistryObject<Block> withItemBlock(String name, Supplier<Block> supplier) {
		RegistryObject<Block> block = REGISTRY.register(name, supplier);
		AoVItems.REGISTRY.register(name, () -> new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().setNoRepair().group(AoVTabs.tabAoV)));
		return block;
	}

	@SubscribeEvent
	public static void fixBlockMappings(RegistryEvent.MissingMappings<Block> event) {
		for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getMappings())
			if (mapping.key.getNamespace().equals(AoV.MODID) && mapping.key.getPath().equals("blockangelic"))
				mapping.remap(Objects.requireNonNull(angelicstatue.get()));
	}

	@SubscribeEvent
	public static void fixItemMappings(RegistryEvent.MissingMappings<Item> event) {
		for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getMappings())
			if (mapping.key.getNamespace().equals(AoV.MODID) && mapping.key.getPath().equals("blockangelic"))
				mapping.remap(Objects.requireNonNull(angelicstatue.get()).asItem());
	}

}
