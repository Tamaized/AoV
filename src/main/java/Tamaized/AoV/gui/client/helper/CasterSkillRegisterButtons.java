package Tamaized.AoV.gui.client.helper;

import Tamaized.AoV.core.skills.caster.CasterSkillCore;
import Tamaized.AoV.gui.buttons.SkillButton;
import Tamaized.AoV.gui.client.AoVSkillsGUI;

public class CasterSkillRegisterButtons {

	public static void register(AoVSkillsGUI instance){
		
		SkillButton sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, 13, instance.height-48, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
	}

}
