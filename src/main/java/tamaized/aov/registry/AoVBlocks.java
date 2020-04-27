package tamaized.aov.registry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tamaized.aov.AoV;
import tamaized.aov.common.blocks.BlockAngelicBlock;

import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = AoV.MODID)
public class AoVBlocks {

	static final DeferredRegister<Block> REGISTRY = new DeferredRegister<>(ForgeRegistries.BLOCKS, AoV.MODID);

	public static final RegistryObject<BlockAngelicBlock> angelicstatue = withItemBlock("angelicstatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.ALL, prop()));
	public static final RegistryObject<BlockAngelicBlock> favoredsoulstatue = withItemBlock("favoredsoulstatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.FAVOREDSOUL, prop()));
	public static final RegistryObject<BlockAngelicBlock> clericstatue = withItemBlock("clericstatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.CLERIC, prop()));
	public static final RegistryObject<BlockAngelicBlock> paladinstatue = withItemBlock("paladinstatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.PALADIN, prop()));
	public static final RegistryObject<BlockAngelicBlock> astrostatue = withItemBlock("astrostatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.ASTRO, prop()));
	public static final RegistryObject<BlockAngelicBlock> druidstatue = withItemBlock("druidstatue", () -> new BlockAngelicBlock(BlockAngelicBlock.ClassType.DRUID, prop()));

	public static void register(IEventBus mod) {
		REGISTRY.register(mod);
	}

	private static Block.Properties prop() {
		return Block.Properties.create(Material.ROCK).hardnessAndResistance(7F).hardnessAndResistance(7F);
	}

	private static <T extends Block> RegistryObject<T> withItemBlock(String name, Supplier<T> supplier) {
		RegistryObject<T> block = REGISTRY.register(name, supplier);
		AoVItems.REGISTRY.register(name, () -> new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().setNoRepair().group(AoVTabs.tabAoV)));
		return block;
	}

	@SubscribeEvent
	public static void registerRenderLayers(FMLClientSetupEvent e) {
		setStatueRenderLayer(angelicstatue);
		setStatueRenderLayer(favoredsoulstatue);
		setStatueRenderLayer(clericstatue);
		setStatueRenderLayer(paladinstatue);
		setStatueRenderLayer(astrostatue);
		setStatueRenderLayer(druidstatue);
	}

	private static void setStatueRenderLayer(RegistryObject<BlockAngelicBlock> statue) {
		RenderTypeLookup.setRenderLayer(statue.get(), RenderType.getTranslucent());
	}

}
