package Tamaized.AoV.gui.client.buttonList;

import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.gui.buttons.SkillButton;
import Tamaized.AoV.gui.client.AoVSkillsGUI;

public class DefenderSkillRegisterButtons {

	public static void register(AoVSkillsGUI instance) {

		/* Cores */
		SkillButton sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 71, instance.height - 48, AoVSkill.defender_core_1);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 96, instance.height - 48, AoVSkill.defender_core_2);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 121, instance.height - 48, AoVSkill.defender_core_3);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 146, instance.height - 48, AoVSkill.defender_core_4);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 171, instance.height - 48, AoVSkill.defender_capstone);
		instance.addNewButton(sbI);

		/* Tier 1 */
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 71, instance.height - 88, AoVSkill.defender_tier_1_1);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 96, instance.height-88, AoVSkill.defender_tier_1_2);
		// instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 121, instance.height - 88, AoVSkill.defender_tier_1_3);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 146, instance.height-88, AoVSkill.defender_tier_1_4);
		// instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 171, instance.height-88, AoVSkill.defender_tier_1_5);
		// instance.addNewButton(sbI);

		/* Tier 2 */
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 71, instance.height - 128, AoVSkill.defender_tier_2_1);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 96, instance.height-128, AoVSkill.defender_tier_2_2);
		// instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 121, instance.height - 128, AoVSkill.defender_tier_2_3);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 146, instance.height-128, AoVSkill.defender_tier_2_4);
		// instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 171, instance.height-128, AoVSkill.defender_tier_2_5);
		// instance.addNewButton(sbI);

		/* Tier 3 */
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 71, instance.height - 168, AoVSkill.defender_tier_3_1);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 96, instance.height - 168, AoVSkill.defender_tier_3_2);
		// instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 121, instance.height - 168, AoVSkill.defender_tier_3_3);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 146, instance.height - 168, AoVSkill.defender_tier_3_4);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 171, instance.height - 168, AoVSkill.defender_tier_3_5);
		instance.addNewButton(sbI);

		/* Tier 4 */
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 71, instance.height - 208, AoVSkill.defender_tier_4_1);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 96, instance.height - 208, AoVSkill.defender_tier_4_2);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 121, instance.height - 208, AoVSkill.defender_tier_4_3);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 146, instance.height - 208, AoVSkill.defender_tier_4_4);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 + 171, instance.height - 208, AoVSkill.defender_tier_4_5);
		instance.addNewButton(sbI);

	}

}
