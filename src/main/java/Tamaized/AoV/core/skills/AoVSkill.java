package Tamaized.AoV.core.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Tamaized.AoV.core.skills.healer.HealerSkillCore;

public abstract class AoVSkill {
	
	private static Map<String, AoVSkill> registry = new HashMap<String, AoVSkill>();
	
	public final String skillName;
	public final String parent;
	public final boolean isCore;
	
	public final List<String> description;
	
	public AoVSkill(String name, String p, boolean core, String... desc){
		skillName = name;
		parent = p;
		isCore = core;
		description = new ArrayList<String>();
		for(String s : desc) description.add(s);
		registry.put(name, this);
	}
	
	public static AoVSkill getSkillFromName(String name){
		return registry.get(name);
	}
	
	public static void registerSkills(){
		new HealerSkillCore();
	}

}
