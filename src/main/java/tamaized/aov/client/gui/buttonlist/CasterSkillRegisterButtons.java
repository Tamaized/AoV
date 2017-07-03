package tamaized.aov.client.gui.buttonlist;

import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.client.gui.buttons.SkillButton;
import tamaized.aov.client.gui.AoVSkillsGUI;

public class CasterSkillRegisterButtons {

	public static void register(AoVSkillsGUI instance) {

		/* Cores */
		SkillButton sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 197, instance.height - 48, AoVSkill.caster_core_1);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 172, instance.height - 48, AoVSkill.caster_core_2);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 147, instance.height - 48, AoVSkill.caster_core_3);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 122, instance.height - 48, AoVSkill.caster_core_4);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 97, instance.height - 48, AoVSkill.caster_capstone);
		instance.addNewButton(sbI);

		/* Tier 1 */
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 197, instance.height - 88, AoVSkill.caster_tier_1_1);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 172, instance.height-88, AoVSkill.caster_tier_1_2);
		// instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 147, instance.height - 88, AoVSkill.caster_tier_1_3);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 122, instance.height-88, AoVSkill.caster_tier_1_4);
		// instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 97, instance.height-88, AoVSkill.caster_tier_1_5);
		// instance.addNewButton(sbI);

		/* Tier 2 */
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 197, instance.height - 128, AoVSkill.caster_tier_2_1);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 172, instance.height - 128, AoVSkill.caster_tier_2_2);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 147, instance.height - 128, AoVSkill.caster_tier_2_3);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 122, instance.height-128, AoVSkill.caster_tier_2_4);
		// instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 97, instance.height-128, AoVSkill.caster_tier_2_5);
		// instance.addNewButton(sbI);

		/* Tier 3 */
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 197, instance.height - 168, AoVSkill.caster_tier_3_1);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 172, instance.height - 168, AoVSkill.caster_tier_3_2);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 147, instance.height - 168, AoVSkill.caster_tier_3_3);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width/2 - 122, instance.height-168, AoVSkill.caster_tier_3_4);
		// instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 97, instance.height - 168, AoVSkill.caster_tier_3_5);
		// instance.addNewButton(sbI);

		/* Tier 4 */
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 197, instance.height - 208, AoVSkill.caster_tier_4_1);
		instance.addNewButton(sbI);
		// sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 172, instance.height - 208, AoVSkill.caster_tier_4_2);
		// instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 147, instance.height - 208, AoVSkill.caster_tier_4_3);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 122, instance.height - 208, AoVSkill.caster_tier_4_4);
		instance.addNewButton(sbI);
		sbI = new SkillButton(instance.getSkillButtonID(), instance.width / 2 - 97, instance.height - 208, AoVSkill.caster_tier_4_5);
		instance.addNewButton(sbI);

	}

}
