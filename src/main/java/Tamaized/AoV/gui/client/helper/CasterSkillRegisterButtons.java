package Tamaized.AoV.gui.client.helper;

import Tamaized.AoV.core.skills.caster.cores.CasterSkillCapStone;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore1;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore2;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore3;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore4;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S1;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S3;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S1;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S3;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S1;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S2;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S3;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S5;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S1;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S2;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S3;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S5;
import Tamaized.AoV.gui.buttons.SkillButton;
import Tamaized.AoV.gui.client.AoVSkillsGUI;

public class CasterSkillRegisterButtons {

	public static void register(AoVSkillsGUI instance){
		
		//////////////////////////////////////////////////// Cores ////////////////////////////////////////////////////////////////////////
		SkillButton sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 197, instance.height-48, CasterSkillCore1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 172, instance.height-48, CasterSkillCore2.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 147, instance.height-48, CasterSkillCore3.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 122, instance.height-48, CasterSkillCore4.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 97, instance.height-48, CasterSkillCapStone.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		////////////////////////////////////////////////////////// Tier 1 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 197, instance.height-88, CasterSkillT1S1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		//sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 172, instance.height-88, CasterSkillT1S2.getUnlocalizedName());
		//instance.addButton(sbI);
		//instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 147, instance.height-88, CasterSkillT1S3.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		//sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 122, instance.height-88, CasterSkillT1S4.getUnlocalizedName());
		//instance.addButton(sbI);
		//instance.skillButtonList.add(sbI);

		//sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 97, instance.height-88, CasterSkillT1S5.getUnlocalizedName());
		//instance.addButton(sbI);
		//instance.skillButtonList.add(sbI);

		////////////////////////////////////////////////////////// Tier 2 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 197, instance.height-128, CasterSkillT2S1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		//sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 172, instance.height-128, CasterSkillT2S2.getUnlocalizedName());
		//instance.addButton(sbI);
		//instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 147, instance.height-128, CasterSkillT2S3.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		//sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 122, instance.height-128, CasterSkillT2S4.getUnlocalizedName());
		//instance.addButton(sbI);
		//instance.skillButtonList.add(sbI);

		//sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 97, instance.height-128, CasterSkillT2S5.getUnlocalizedName());
		//instance.addButton(sbI);
		//instance.skillButtonList.add(sbI);

		////////////////////////////////////////////////////////// Tier 3 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 197, instance.height-168, CasterSkillT3S1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 172, instance.height-168, CasterSkillT3S2.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 147, instance.height-168, CasterSkillT3S3.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		//sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 122, instance.height-168, CasterSkillT3S4.getUnlocalizedName());
		//instance.addButton(sbI);
		//instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 97, instance.height-168, CasterSkillT3S5.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		////////////////////////////////////////////////////////// Tier 4 //////////////////////////////////////////////////////////////////////////
		
		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 197, instance.height-208, CasterSkillT4S1.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 172, instance.height-208, CasterSkillT4S2.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 147, instance.height-208, CasterSkillT4S3.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);

		//sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 122, instance.height-208, CasterSkillT4S4.getUnlocalizedName());
		//instance.addButton(sbI);
		//instance.skillButtonList.add(sbI);

		sbI = new SkillButton(instance.BUTTON_SKILL_CHECK, instance.width/2 - 97, instance.height-208, CasterSkillT4S5.getUnlocalizedName());
		instance.addButton(sbI);
		instance.skillButtonList.add(sbI);
		
		
		
	}

}
