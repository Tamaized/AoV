package Tamaized.AoV.registry;

import java.util.ArrayList;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import Tamaized.AoV.AoV;
import Tamaized.AoV.entity.projectile.caster.ProjectileNimbusRay;
import Tamaized.TamModized.registry.ITamModel;
import Tamaized.TamModized.registry.ITamRegistry;

public class AoVEntities implements ITamRegistry {

	private static ArrayList<ITamModel> modelList;

	@Override
	public void preInit() {
		modelList = new ArrayList<ITamModel>();
	}

	@Override
	public void init() {
		EntityRegistry.registerModEntity(ProjectileNimbusRay.class, "ProjectileNimbusRay", 0, AoV.instance, 128, 1, true);
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