package Tamaized.AoV.core.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCapStone;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore1;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore2;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore3;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore4;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S1;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S2;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S3;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S4;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S5;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S1;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S2;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S3;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S4;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S5;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S1;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S2;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S3;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S4;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S5;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S1;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S2;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S3;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S4;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S5;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCapStone;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore1;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore2;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore3;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore4;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S1;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S2;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S3;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S4;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S5;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S1;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S2;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S3;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S4;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S5;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S1;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S2;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S3;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S4;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S5;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S1;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S2;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S3;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S4;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S5;

public abstract class AoVSkill {
	
	private static Map<String, AoVSkill> registry = new HashMap<String, AoVSkill>();
	
	public final String skillName;
	public final AoVSkill parent;
	public final boolean isCore;
	public final int pointCost;
	public final int minLevel;
	public final int minPointsSpent;
	protected Buffs buffs;
	
	public final List<String> abilities;
	
	public final List<String> description;
	
	public AoVSkill(String name, AoVSkill p, int cost, int level, int spentPoints, boolean core, AbilityBase[] spells, String... desc){
		skillName = name;
		parent = p;
		pointCost = cost;
		minLevel = level;
		minPointsSpent = spentPoints;
		isCore = core;
		setupBuffs();
		description = new ArrayList<String>();
		for(String s : desc) description.add(s);
		abilities = new ArrayList<String>();
		registry.put(name, this);
		for(AbilityBase spell : spells) abilities.add(spell.getName());
		
	}
	
	protected abstract void setupBuffs();
	
	public Buffs getBuffs(){
		return buffs;
	}
	
	public static AoVSkill getSkillFromName(String name){
		return registry.get(name);
	}

	public abstract ResourceLocation getIcon();
	
	public static void registerSkills(){
		/*Healer*/{
			new HealerSkillCore1();
			new HealerSkillCore2();
			new HealerSkillCore3();
			new HealerSkillCore4();
			new HealerSkillCapStone();
			
			new HealerSkillT1S1();
			new HealerSkillT1S2();
			new HealerSkillT1S3();
			new HealerSkillT1S4();
			new HealerSkillT1S5();
			
			new HealerSkillT2S1();
			new HealerSkillT2S2();
			new HealerSkillT2S3();
			new HealerSkillT2S4();
			new HealerSkillT2S5();
			
			new HealerSkillT3S1();
			new HealerSkillT3S2();
			new HealerSkillT3S3();
			new HealerSkillT3S4();
			new HealerSkillT3S5();
			
			new HealerSkillT4S1();
			new HealerSkillT4S2();
			new HealerSkillT4S3();
			new HealerSkillT4S4();
			new HealerSkillT4S5();
		}
		
		/*Caster*/{
			new CasterSkillCore1();
			new CasterSkillCore2();
			new CasterSkillCore3();
			new CasterSkillCore4();
			new CasterSkillCapStone();
			
			new CasterSkillT1S1();
			new CasterSkillT1S2();
			new CasterSkillT1S3();
			new CasterSkillT1S4();
			new CasterSkillT1S5();
			
			new CasterSkillT2S1();
			new CasterSkillT2S2();
			new CasterSkillT2S3();
			new CasterSkillT2S4();
			new CasterSkillT2S5();
			
			new CasterSkillT3S1();
			new CasterSkillT3S2();
			new CasterSkillT3S3();
			new CasterSkillT3S4();
			new CasterSkillT3S5();
			
			new CasterSkillT4S1();
			new CasterSkillT4S2();
			new CasterSkillT4S3();
			new CasterSkillT4S4();
			new CasterSkillT4S5();
		}
		
	}
	
	public class Buffs{
		
		public final int divinePower;
		public final int spellPower;
		public final float costReductionPerc;
		public final int costReductionFlat;
		
		public final boolean selectiveFocus;
		
		public Buffs(int dP, int sP, float crP, int crF, boolean sel){
			divinePower = dP;
			spellPower = sP;
			costReductionPerc = crP;
			costReductionFlat = crF;
			selectiveFocus = sel;
		}
		
	}

}
