package tamaized.aov.common.core.skills.caster.cores;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.SkillIcons;
import tamaized.aov.common.core.skills.AoVSkills;

import java.util.List;

public class CasterSkillCore2 extends AoVSkill {

	private static final List<AbilityBase> spells = Lists.newArrayList();

	private static final int COST = 1;
	private static final int LEVEL = 3;
	private static final int SPENT = 0;
	private static final int CHARGES = 0;
	private static final int SPELLPOWER = 15;
	private static final int DODGE = 0;
	private static final int DOUBLESTRIKE = 0;
	private static final boolean SELECTIVE_FOCUS = false;

	static {

	}

	public CasterSkillCore2() {
		super(spells,

				new TextComponentTranslation("aov.skill.caster.core.2.name"),

				new TextComponentTranslation("aov.skill.caster.core.2.req"),

				new TextComponentTranslation("aov.skill.global.minlevel", LEVEL),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.skill.global.spellpower", SPELLPOWER)

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
		return SkillIcons.spellpower;
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkills.caster_core_1;
	}

	@Deprecated
	public String getName() {
		return "CasterSkillCore2";
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
