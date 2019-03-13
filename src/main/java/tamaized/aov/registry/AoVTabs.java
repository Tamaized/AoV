package tamaized.aov.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AoVTabs {

	public static final ItemGroup tabAoV = new ItemGroup("aov") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(AoVBlocks.clericstatue);
		}
	};

}
