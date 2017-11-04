package tamaized.aov.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AoVTabs {

	public static final CreativeTabs tabAoV = new CreativeTabs("aov") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Items.BLAZE_POWDER);
		}
	};

}
