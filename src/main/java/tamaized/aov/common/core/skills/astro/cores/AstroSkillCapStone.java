package tamaized.aov.common.core.skills.astro.cores;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.common.core.skills.SkillIcons;

import java.util.List;

public class AstroSkillCapStone extends AoVSkill {

	private static final List<AbilityBase> spells = Lists.newArrayList();

	private static final int COST = 1;
	private static final int LEVEL = 15;
	private static final int SPENT = 0;
	private static final int CHARGES = -1;
	private static final int SPELLPOWER = 0;
	private static final int DODGE = 0;
	private static final int DOUBLESTRIKE = 0;
	private static final boolean SELECTIVE_FOCUS = false;

	static {
		spells.add(Abilities.timedilation);
	}

	public AstroSkillCapStone() {
		super(spells,

				new TextComponentTranslation("aov.skill.astro.core.5.name"),

				new TextComponentTranslation("aov.skill.astro.core.5.req"),

				new TextComponentTranslation("aov.skill.global.minlevel", LEVEL),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.skill.astro.core.5.desc")

		);
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
		return SkillIcons.charges;
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkills.astro_core_4;
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
