package Tamaized.AoV.core.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Tamaized.AoV.core.skills.healer.HealerSkillCore;

public abstract class AoVSkill {
	
	private static Map<String, AoVSkill> registry = new HashMap<String, AoVSkill>();
	
	public final String skillName;
	public final AoVSkill parent;
	public final boolean isCore;
	public final int pointCost;
	protected Buffs buffs;
	
	public final List<String> description;
	
	public AoVSkill(String name, AoVSkill p, int cost, boolean core, String... desc){
		skillName = name;
		parent = p;
		pointCost = cost;
		isCore = core;
		setupBuffs();
		description = new ArrayList<String>();
		for(String s : desc) description.add(s);
		registry.put(name, this);
	}
	
	protected abstract void setupBuffs();
	
	public Buffs getBuffs(){
		return buffs;
	}
	
	public static AoVSkill getSkillFromName(String name){
		return registry.get(name);
	}
	
	public static void registerSkills(){
		new HealerSkillCore();
	}
	
	public class Buffs{
		
		public final int DivinePower;
		
		public Buffs(int divinePower){
			DivinePower = divinePower;
		}
		
	}

}
