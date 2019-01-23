package tamaized.aov.client.gui.buttonlist;

import tamaized.aov.client.gui.AoVSkillsGUI;
import tamaized.aov.client.gui.buttons.SkillButton;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.skills.AoVSkills;

public class ClericSkillRegisterButtons implements IClassButtons {

	@Override
	public void register(AoVSkillsGUI instance) {

		/* Cores */
		SkillButton sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 63, instance.height - 48, AoVSkills.cleric_core_1);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 38, instance.height - 48, AoVSkills.cleric_core_2);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 13, instance.height - 48, AoVSkills.cleric_core_3);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 + 12, instance.height - 48, AoVSkills.cleric_core_4);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 + 37, instance.height - 48, AoVSkills.cleric_capstone);
		instance.addNewButton(sbI);

		/* Tier 1 */
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 63, instance.height - 88, AoVSkills.cleric_tier_1_1);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 38, instance.height - 88, AoVSkills.cleric_tier_1_2);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 13, instance.height - 88, AoVSkills.cleric_tier_1_3);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width/2 + 12, instance.height-88, AoVSkills.cleric_tier_1_4);
		// instance.addNewButton(sbI);
		// sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width/2 + 37, instance.height-88, AoVSkills.cleric_tier_1_5);
		// instance.addNewButton(sbI);

		/* Tier 2 */
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 63, instance.height - 128, AoVSkills.cleric_tier_2_1);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 38, instance.height - 128, AoVSkills.cleric_tier_2_2);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 13, instance.height - 128, AoVSkills.cleric_tier_2_3);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 + 12, instance.height - 128, AoVSkills.cleric_tier_2_4);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width/2 + 37, instance.height-128, AoVSkills.cleric_tier_2_5);
		// instance.addNewButton(sbI);

		/* Tier 3 */
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 63, instance.height - 168, AoVSkills.cleric_tier_3_1);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 38, instance.height - 168, AoVSkills.cleric_tier_3_2);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 13, instance.height - 168, AoVSkills.cleric_tier_3_3);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width/2 + 12, instance.height-168, AoVSkills.cleric_tier_3_4);
		// instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 + 37, instance.height - 168, AoVSkills.cleric_tier_3_5);
		instance.addNewButton(sbI);

		/* Tier 4 */
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 63, instance.height - 208, AoVSkills.cleric_tier_4_1);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 38, instance.height - 208, AoVSkills.cleric_tier_4_2);
		instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 - 13, instance.height - 208, AoVSkills.cleric_tier_4_3);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width/2 + 12, instance.height-208, AoVSkills.cleric_tier_4_4);
		// instance.addNewButton(sbI);
		sbI = new SkillButton(AoVSkillsGUI.getSkillButtonID(), instance.width / 2 + 37, instance.height - 208, AoVSkills.cleric_tier_4_5);
		instance.addNewButton(sbI);
	}

	@Override
	public boolean active(IAoVCapability cap) {
		return cap.getCoreSkill() == AoVSkills.cleric_core_1;
	}

}
