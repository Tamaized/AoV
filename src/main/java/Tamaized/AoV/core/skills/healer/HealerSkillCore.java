package Tamaized.AoV.core.skills.healer;

import com.mojang.realmsclient.gui.ChatFormatting;

import Tamaized.AoV.core.skills.AoVSkill;

public class HealerSkillCore extends AoVSkill{

	public HealerSkillCore() {
		super("HealerSkillCore", "null", true, 
				ChatFormatting.AQUA+"Class Core: Healer",
				ChatFormatting.RED+"Obtaining this Skill", 
				ChatFormatting.RED+"prevents you from taking",
				ChatFormatting.RED+"skills from any other class!",
				"",
				ChatFormatting.GREEN+"+10 Divine Power");
	}

}
