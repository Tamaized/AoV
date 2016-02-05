package Tamaized.AoV.core.skills.healer;

import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillCore extends AoVSkill{

	public HealerSkillCore() {
		super("HealerSkillCore", null, 1, true, 
				ChatFormatting.AQUA+"Class Core: Healer",
				ChatFormatting.RED+"Obtaining this Skill", 
				ChatFormatting.RED+"prevents you from taking",
				ChatFormatting.RED+"skills from any other class!",
				"",
				ChatFormatting.GREEN+"+10 Divine Power",
				"",
				ChatFormatting.YELLOW+"Added Spell: Cure Light Wounds"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(10, 0, 0, 0);
	}

}
