package Tamaized.AoV.gui.client.helper;

import Tamaized.AoV.core.skills.healer.cores.HealerSkillCapStone;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore1;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore2;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore3;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore4;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S1;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S1;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S1;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S1;
import Tamaized.AoV.gui.buttons.SkillButton;
import Tamaized.AoV.gui.client.AoVSkillsGUI;

public class HealerSkillRegisterButtons {
	
	public static void register(AoVSkillsGUI instance){

		//////////////////////////////////////////////////// Cores ////////////////////////////////////////////////////////////////////////
		SkillButton sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 63, instance.height-48, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 38, instance.height-48, HealerSkillCore2.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 13, instance.height-48, HealerSkillCore3.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 + 12, instance.height-48, HealerSkillCore4.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 + 37, instance.height-48, HealerSkillCapStone.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		////////////////////////////////////////////////////////// Tier 1 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 63, instance.height-88, HealerSkillT1S1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 38, instance.height-88, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 13, instance.height-88, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 + 12, instance.height-88, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 + 37, instance.height-88, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		////////////////////////////////////////////////////////// Tier 2 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 63, instance.height-128, HealerSkillT2S1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 38, instance.height-128, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 13, instance.height-128, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 + 12, instance.height-128, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 + 37, instance.height-128, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		////////////////////////////////////////////////////////// Tier 3 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 63, instance.height-168, HealerSkillT3S1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 38, instance.height-168, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 13, instance.height-168, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 + 12, instance.height-168, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 + 37, instance.height-168, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		////////////////////////////////////////////////////////// Tier 4 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 63, instance.height-208, HealerSkillT4S1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 38, instance.height-208, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 13, instance.height-208, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 + 12, instance.height-208, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 + 37, instance.height-208, HealerSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
	}

}
