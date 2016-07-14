package Tamaized.AoV.registry;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import Tamaized.AoV.AoV;
import Tamaized.TamModized.registry.ITamModel;
import Tamaized.TamModized.registry.ITamRegistry;

public class AoVTabs implements ITamRegistry {

	public static CreativeTabs tabAoV;

	@Override
	public void preInit() {
		tabAoV = new CreativeTabs("tabAoV") {
			@Override
			public Item getTabIconItem() {
				return Items.BLAZE_POWDER;
			}
		};
	}

	@Override
	public void init() {

	}

	@Override
	public void postInit() {

	}

	@Override
	public ArrayList<ITamModel> getModelList() {
		return new ArrayList<ITamModel>();
	}

	@Override
	public String getModID() {
		return AoV.modid;
	}

}
