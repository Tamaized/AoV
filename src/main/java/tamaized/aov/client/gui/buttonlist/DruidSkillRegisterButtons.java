package tamaized.aov.client.gui.buttonlist;

import tamaized.aov.client.gui.AoVSkillsGUI;
import tamaized.aov.client.gui.buttons.SkillButton;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.skills.AoVSkills;

public class DruidSkillRegisterButtons implements IClassButtons {

	@Override
	public void register(AoVSkillsGUI instance) {

		/* Cores */
		instance.addNewButton(new SkillButton(instance.width / 2 - 63, instance.height - 48, AoVSkills.druid_core_1));
		instance.addNewButton(new SkillButton(instance.width / 2 - 38, instance.height - 48, AoVSkills.druid_core_2));
		instance.addNewButton(new SkillButton(instance.width / 2 - 13, instance.height - 48, AoVSkills.druid_core_3));
		instance.addNewButton(new SkillButton(instance.width / 2 + 12, instance.height - 48, AoVSkills.druid_core_4));
		instance.addNewButton(new SkillButton(instance.width / 2 + 37, instance.height - 48, AoVSkills.druid_capstone));

		/* Tier 1 */
		instance.addNewButton(new SkillButton(instance.width / 2 - 63, instance.height - 88, AoVSkills.druid_tier_1_1));
		instance.addNewButton(new SkillButton(instance.width / 2 - 38, instance.height - 88, AoVSkills.druid_tier_1_2));
		instance.addNewButton(new SkillButton(instance.width / 2 - 13, instance.height - 88, AoVSkills.druid_tier_1_3));
		instance.addNewButton(new SkillButton(instance.width / 2 + 12, instance.height - 88, AoVSkills.druid_tier_1_4));
		instance.addNewButton(new SkillButton(instance.width / 2 + 37, instance.height - 88, AoVSkills.druid_tier_1_5));

		/* Tier 2 */
		//instance.addNewButton(new SkillButton(instance.width / 2 - 63, instance.height - 128, AoVSkills.druid_tier_2_1));
		instance.addNewButton(new SkillButton(instance.width / 2 - 38, instance.height - 128, AoVSkills.druid_tier_2_2));
		instance.addNewButton(new SkillButton(instance.width / 2 - 13, instance.height - 128, AoVSkills.druid_tier_2_3));
		instance.addNewButton(new SkillButton(instance.width / 2 + 12, instance.height - 128, AoVSkills.druid_tier_2_4));
		instance.addNewButton(new SkillButton(instance.width / 2 + 37, instance.height - 128, AoVSkills.druid_tier_2_5));

		/* Tier 3 */
		instance.addNewButton(new SkillButton(instance.width / 2 - 63, instance.height - 168, AoVSkills.druid_tier_3_1));
		instance.addNewButton(new SkillButton(instance.width / 2 - 38, instance.height - 168, AoVSkills.druid_tier_3_2));
		instance.addNewButton(new SkillButton(instance.width / 2 - 13, instance.height - 168, AoVSkills.druid_tier_3_3));
		instance.addNewButton(new SkillButton(instance.width / 2 + 12, instance.height - 168, AoVSkills.druid_tier_3_4));
		instance.addNewButton(new SkillButton(instance.width / 2 + 37, instance.height - 168, AoVSkills.druid_tier_3_5));

		/* Tier 4 */
		instance.addNewButton(new SkillButton(instance.width / 2 - 63, instance.height - 208, AoVSkills.druid_tier_4_1));
		instance.addNewButton(new SkillButton(instance.width / 2 - 38, instance.height - 208, AoVSkills.druid_tier_4_2));
		instance.addNewButton(new SkillButton(instance.width / 2 - 13, instance.height - 208, AoVSkills.druid_tier_4_3));
		instance.addNewButton(new SkillButton(instance.width / 2 + 12, instance.height - 208, AoVSkills.druid_tier_4_4));
		instance.addNewButton(new SkillButton(instance.width / 2 + 37, instance.height - 208, AoVSkills.druid_tier_4_5));


	}

	@Override
	public boolean active(IAoVCapability cap) {
		return cap.getCoreSkill() == AoVSkills.druid_core_1;
	}

}
