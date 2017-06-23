package Tamaized.AoV.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AoVTabs {

	public static CreativeTabs tabAoV = new CreativeTabs("tabAoV") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Items.BLAZE_POWDER);
		}
	};

}
