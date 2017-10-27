package tamaized.aov.common.core.skills.defender.tier2;

import com.google.common.collect.Lists;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.SkillIcons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import tamaized.aov.common.core.skills.AoVSkills;

import java.util.List;

public class DefenderSkillT2S4 extends AoVSkill {

	private static final List<AbilityBase> spells = Lists.newArrayList();

	private static final int COST = 1;
	private static final int LEVEL = 0;
	private static final int SPENT = 4;
	private static final int CHARGES = 0;
	private static final int SPELLPOWER = 0;
	private static final int DODGE = 0;
	private static final int DOUBLESTRIKE = 5;
	private static final boolean SELECTIVE_FOCUS = false;

	static {

	}

	public DefenderSkillT2S4() {
		super(spells,

				new TextComponentTranslation("aov.skill.defender.tier2.4.name"),

				new TextComponentTranslation("aov.skill.global.minpoint", SPENT),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.skill.global.doublestrike", DOUBLESTRIKE)

		);
	}

	public String getName() {
		return "DefenderSkillT2S4";
	}

	@Override
	public int getCharges() {
		return CHARGES;
	}

	@Override
	public int getSpellPower() {
		return SPELLPOWER;
	}

	@Override
	public int getDodge() {
		return DODGE;
	}

	@Override
	public int getDoubleStrike() {
		return DOUBLESTRIKE;
	}

	@Override
	public boolean grantsSelectiveFocus() {
		return SELECTIVE_FOCUS;
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.doublestrike;
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkills.defender_core_1;
	}

	@Override
	public int getCost() {
		return COST;
	}

	@Override
	public int getLevel() {
		return LEVEL;
	}

	@Override
	public int getSpentPoints() {
		return SPENT;
	}

}
