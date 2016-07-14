package Tamaized.AoV.registry;

import java.util.ArrayList;

import Tamaized.AoV.AoV;
import Tamaized.AoV.items.DebugItem;
import Tamaized.TamModized.registry.ITamModel;
import Tamaized.TamModized.registry.ITamRegistry;

public class AoVItems implements ITamRegistry {

	private static ArrayList<ITamModel> modelList;

	public static DebugItem debugger;

	@Override
	public void preInit() {
		modelList = new ArrayList<ITamModel>();

		modelList.add(debugger = new DebugItem("debugger"));
	}

	@Override
	public void init() {

		// OreDictionary.registerOre("dustIron", ironDust);

		// GameRegistry.addShapelessRecipe(new ItemStack(voidBurner), voidcrystal, new ItemStack(Items.flint_and_steel, 1, voidCraft.WILDCARD_VALUE));
		// GameRegistry.addRecipe(new ItemStack(MoltenvoidChain), "XYX", "YXY", "XYX", 'Y', MoltenvoidChainPart, 'X', burnBone);

		// GameRegistry.addSmelting(voidCraft.blocks.oreVoidcrystal, new ItemStack(voidcrystal), 0.1F);
	}

	@Override
	public void postInit() {

	}

	@Override
	public ArrayList<ITamModel> getModelList() {
		return modelList;
	}

	@Override
	public String getModID() {
		return AoV.modid;
	}

}
