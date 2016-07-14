package Tamaized.AoV.registry;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import Tamaized.AoV.AoV;
import Tamaized.AoV.blocks.BlockAngelicBlock;
import Tamaized.TamModized.registry.ITamModel;
import Tamaized.TamModized.registry.ITamRegistry;

public class AoVBlocks implements ITamRegistry {

	private static ArrayList<ITamModel> modelList;

	public static BlockAngelicBlock angelicBlock;

	@Override
	public void preInit() {
		modelList = new ArrayList<ITamModel>();

		modelList.add(angelicBlock = new BlockAngelicBlock(AoV.tabs.tabAoV, Material.ROCK, "blockAngelic", 7.0F));

	}

	@Override
	public void init() {

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
