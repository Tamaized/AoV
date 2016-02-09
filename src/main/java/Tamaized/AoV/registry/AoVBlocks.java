package Tamaized.AoV.registry;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import Tamaized.AoV.AoV;
import Tamaized.AoV.blocks.BlockAngelicBlock;

public class AoVBlocks extends RegistryBase {
	
	private static ArrayList<Block> blockList;

	public static Block angelicBlock;

	@Override
	public void preInit() {

		angelicBlock = new BlockAngelicBlock(Material.rock, "blockAngelic").setHardness(7.0F).setStepSound(Blocks.stone.stepSound).setCreativeTab(AoV.tabs.tabAoV);
		
		//Slabs have to be registered outside of their class
		//GameRegistry.registerBlock(blockVoidBrickHalfSlab, BasicVoidItemBlockSlab.class, "blocks/"+((BasicVoidBlockSlab)blockVoidBrickHalfSlab).getName(), blockVoidBrickHalfSlab, blockVoidBrickDoubleSlab, false);
		//GameRegistry.registerBlock(blockVoidBrickDoubleSlab, BasicVoidItemBlockSlab.class, "blocks/"+((BasicVoidBlockSlab)blockVoidBrickDoubleSlab).getName(), blockVoidBrickHalfSlab, blockVoidBrickDoubleSlab, false);

		blockList = new ArrayList<Block>();
		blockList.add(angelicBlock);
		
	}

	@Override
	public void init() {
		//GameRegistry.addRecipe(new ItemStack(blockVoidcrystal), "XXX", "XXX", "XXX", 'X', voidCraft.items.voidcrystal);
	}

	@Override
	public void postInit() {
		
	}
	
	@SideOnly(Side.CLIENT)
	public void setupRender(){
		ItemModelMesher modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for(Block b : blockList){
			modelMesher.register(Item.getItemFromBlock(b), 0, new Tamaized.AoV.common.client.ScrewModelResourceLocation("blocks/", ((IBasicAoV) b).getName(), "inventory"));
		}
	}

}
