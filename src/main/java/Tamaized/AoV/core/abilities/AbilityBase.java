package Tamaized.AoV.core.abilities;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.healer.CureLightWounds;

public abstract class AbilityBase {
	
	private static Map<String, AbilityBase> map = new HashMap<String, AbilityBase>();
	
	public final int cost;
	public final double maxDistance;
	
	public AbilityBase(int c, double d){
		cost = c;
		maxDistance = d;
	}
	
	public void activate(EntityPlayer player, AoVData data, Entity e){
		int trueCost = (int) ((float) cost*(1f+data.getCostReductionPerc())) - data.getCostReductionFlat();
		if(trueCost >= data.getCurrentDivinePower()){
			data.setCurrentDivinePower(data.getCurrentDivinePower()-trueCost);
			doAction(player, e);
			
		}
	}
	
	protected abstract void doAction(EntityPlayer player, Entity e);
	
	public abstract String getName();
	
	public static void register(){
		map = new HashMap<String, AbilityBase>();
		AbilityBase a;
		
		a = new CureLightWounds(4, 2);
		map.put(a.getName(), a);
		
		a = null;
	}

}
