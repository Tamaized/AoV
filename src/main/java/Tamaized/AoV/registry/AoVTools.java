package Tamaized.AoV.registry;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AoVTools extends RegistryBase {
	
	public static Item palceholder;

	@Override
	public void preInit() {
		//voidPickaxe = new VoidPickaxe(voidCraft.materials.voidTools).setCreativeTab(voidCraft.tabs.tabVoid).setUnlocalizedName("voidPickaxe");
	}

	@Override
	public void init() {
		//GameRegistry.addRecipe(new ItemStack(voidPickaxe, 1), "XXX", " S ", " S ", 'X', voidCraft.items.voidcrystal, 'S', Items.stick);
	}

	@Override
	public void postInit() {

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void setupRender() {
		
	}

}
