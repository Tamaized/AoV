package tamaized.aov.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;

public class AoVTabs {

	public static final ItemGroup tabAoV = new ItemGroup("aov") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(AoVBlocks.clericstatue.get());
		}
	};

	public static void register(IEventBus mod) {
	}

}
