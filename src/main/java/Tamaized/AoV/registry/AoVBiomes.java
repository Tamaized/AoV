package Tamaized.AoV.registry;

import java.util.ArrayList;

import Tamaized.AoV.AoV;
import Tamaized.TamModized.registry.ITamModel;
import Tamaized.TamModized.registry.ITamRegistry;

public class AoVBiomes implements ITamRegistry {

	private static ArrayList<ITamModel> modelList;

	@Override
	public void preInit() {
		modelList = new ArrayList<ITamModel>();
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