package Tamaized.AoV.registry;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AoVArmors extends RegistryBase {

	public static Item placeholder;

	@Override
	public void preInit() {
		//voidHelmet = new VoidArmor(voidCraft.materials.voidArmor, 0, 0, "void").setUnlocalizedName("voidHelmet");
		//voidChest = new VoidArmor(voidCraft.materials.voidArmor, 0, 1, "void").setUnlocalizedName("voidChest");
		//voidLegs = new VoidArmor(voidCraft.materials.voidArmor, 0, 2, "void").setUnlocalizedName("voidLegs");
		//voidBoots = new VoidArmor(voidCraft.materials.voidArmor, 0, 3, "void").setUnlocalizedName("voidBoots");
	}

	@Override
	public void init() {
		//GameRegistry.registerItem(voidHelmet, voidHelmet.getUnlocalizedName());
		//GameRegistry.registerItem(voidChest, voidChest.getUnlocalizedName());
		//GameRegistry.registerItem(voidLegs, voidLegs.getUnlocalizedName());
		//GameRegistry.registerItem(voidBoots, voidBoots.getUnlocalizedName());

		//GameRegistry.addRecipe(new ItemStack(voidHelmet, 1), "XXX", "X X", 'X', voidCraft.items.voidcrystal);
		//GameRegistry.addRecipe(new ItemStack(voidChest, 1), "X X", "XXX", "XXX", 'X', voidCraft.items.voidcrystal);
		//GameRegistry.addRecipe(new ItemStack(voidLegs, 1), "XXX", "X X", "X X", 'X', voidCraft.items.voidcrystal);
		//GameRegistry.addRecipe(new ItemStack(voidBoots, 1), "X X", "X X", 'X', voidCraft.items.voidcrystal);
	}

	@Override
	public void postInit() {

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void setupRender() {
		
	}

}
