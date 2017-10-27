package tamaized.aov.common.core.skills.defender.tier2;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;

import java.util.List;

public class DefenderSkillT2S1 extends AoVSkill {

	private static final List<AbilityBase> spells = Lists.newArrayList();

	private static final int COST = 1;
	private static final int LEVEL = 0;
	private static final int SPENT = 4;
	private static final int CHARGES = 0;
	private static final int SPELLPOWER = 0;
	private static final int DODGE = 0;
	private static final int DOUBLESTRIKE = 0;
	private static final boolean SELECTIVE_FOCUS = false;

	static {
		spells.add(AbilityBase.shieldOfFaith);
	}

	public DefenderSkillT2S1() {
		super(spells,

				new TextComponentTranslation("aov.skill.defender.tier2.1.name"),

				new TextComponentTranslation("aov.skill.global.minpoint", SPENT),

				new TextComponentTranslation("aov.skill.defender.tier2.1.req"),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.skill.defender.tier2.1.desc")

		);
	}

	public String getName() {
		return "DefenderSkillT2S1";
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
		return AbilityBase.shieldOfFaith.getIcon();
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkills.defender_tier_1_1;
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
