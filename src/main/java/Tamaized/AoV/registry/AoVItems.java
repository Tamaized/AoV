package Tamaized.AoV.registry;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AoVItems extends RegistryBase {
	
	private static ArrayList<Item> itemList;

	public static Item placeholder;
	
	@Override
	public void preInit() {
		//voidBurner = new VoidBurner("voidBurner").setCreativeTab(voidCraft.tabs.tabVoid);
		
		itemList = new ArrayList<Item>();
		//itemList.add(placeholder);
	}

	@Override
	public void init() {

		//OreDictionary.registerOre("dustIron", ironDust);
		
		//GameRegistry.addShapelessRecipe(new ItemStack(voidBurner), voidcrystal, new ItemStack(Items.flint_and_steel, 1, voidCraft.WILDCARD_VALUE));
		//GameRegistry.addRecipe(new ItemStack(MoltenvoidChain), "XYX", "YXY", "XYX", 'Y', MoltenvoidChainPart, 'X', burnBone);

		//GameRegistry.addSmelting(voidCraft.blocks.oreVoidcrystal, new ItemStack(voidcrystal), 0.1F);
	}

	@Override
	public void postInit() {
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setupRender() {
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		for(Item i : itemList){
			renderItem.getItemModelMesher().register(i, 0, new Tamaized.AoV.common.client.ScrewModelResourceLocation("items/", ((IBasicAoV)i).getName(), "inventory"));
		}
	}

}
