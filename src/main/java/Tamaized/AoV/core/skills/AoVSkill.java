package Tamaized.AoV.core.skills;

import java.util.HashMap;
import java.util.Map;

public abstract class AoVSkill {
	
	private static Map<String, AoVSkill> registry = new HashMap<String, AoVSkill>();
	
	public final String skillName;
	public final String parent;
	public final boolean isCore;
	
	public AoVSkill(String name, String p, boolean core){
		skillName = name;
		parent = p;
		isCore = core;
		registry.put(name, this);
	}
	
	public static AoVSkill getSkillFromName(String name){
		return registry.get(name);
	}

}
