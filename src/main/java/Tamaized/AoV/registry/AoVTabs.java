package Tamaized.AoV.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AoVTabs extends RegistryBase{
	
	public static CreativeTabs tabAoV;

	@Override
	public void preInit() {
		tabAoV = new CreativeTabs("tabAoV") {
			@Override
			public Item getTabIconItem() {
				return Items.blaze_powder;
			}
		};
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void setupRender() {
		
	}

}
