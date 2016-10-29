package Tamaized.AoV.registry;

import java.util.ArrayList;

import Tamaized.AoV.AoV;
import Tamaized.TamModized.registry.ITamModel;
import Tamaized.TamModized.registry.ITamRegistry;

public class AoVTools implements ITamRegistry {

	@Override
	public void preInit() {

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

	@Override
	public void clientPreInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientPostInit() {
		// TODO Auto-generated method stub
		
	}

}
