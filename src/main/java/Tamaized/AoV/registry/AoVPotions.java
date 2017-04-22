package Tamaized.AoV.registry;

import java.util.ArrayList;

import Tamaized.AoV.AoV;
import Tamaized.AoV.potion.PotionAid;
import Tamaized.AoV.potion.PotionStalwartPact;
import Tamaized.TamModized.registry.ITamModel;
import Tamaized.TamModized.registry.ITamRegistry;
import net.minecraft.potion.Potion;

public class AoVPotions implements ITamRegistry {

	private ArrayList<ITamModel> modelList;

	public static Potion aid;
	public static Potion shieldOfFaith;
	public static Potion zeal;
	public static Potion stalwartPact;

	@Override
	public void preInit() {
		modelList = new ArrayList<ITamModel>();

		aid = new PotionAid("aid");
		shieldOfFaith = new PotionAid("faith");
		zeal = new PotionAid("zeal");
		stalwartPact = new PotionStalwartPact("stalwart");
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

	@Override
	public void clientPreInit() {

	}

	@Override
	public void clientInit() {

	}

	@Override
	public void clientPostInit() {

	}

}
