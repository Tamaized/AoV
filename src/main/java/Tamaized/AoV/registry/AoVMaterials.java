package Tamaized.AoV.registry;

import java.util.ArrayList;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import Tamaized.AoV.AoV;
import Tamaized.TamModized.registry.ITamModel;
import Tamaized.TamModized.registry.ITamRegistry;

public class AoVMaterials implements ITamRegistry {

	public static ToolMaterial voidTools;

	public static ArmorMaterial voidArmor;

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

}
