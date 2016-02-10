package Tamaized.AoV.gui.client.helper;

import Tamaized.AoV.core.skills.caster.CasterSkillCore;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCapStone;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore1;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore2;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore3;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore4;
import Tamaized.AoV.gui.buttons.SkillButton;
import Tamaized.AoV.gui.client.AoVSkillsGUI;

public class CasterSkillRegisterButtons {

	public static void register(AoVSkillsGUI instance){
		
		//////////////////////////////////////////////////// Cores ////////////////////////////////////////////////////////////////////////
		SkillButton sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 197, instance.height-48, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 172, instance.height-48, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 147, instance.height-48, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 122, instance.height-48, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 97, instance.height-48, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		////////////////////////////////////////////////////////// Tier 1 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 197, instance.height-88, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 172, instance.height-88, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 147, instance.height-88, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 122, instance.height-88, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 97, instance.height-88, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		////////////////////////////////////////////////////////// Tier 2 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 197, instance.height-128, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 172, instance.height-128, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 147, instance.height-128, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 122, instance.height-128, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 97, instance.height-128, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		////////////////////////////////////////////////////////// Tier 3 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 197, instance.height-168, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 172, instance.height-168, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 147, instance.height-168, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 122, instance.height-168, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 97, instance.height-168, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		////////////////////////////////////////////////////////// Tier 4 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 197, instance.height-208, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 172, instance.height-208, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 147, instance.height-208, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 122, instance.height-208, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 97, instance.height-208, CasterSkillCore.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		
		
	}

}
