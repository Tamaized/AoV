package tamaized.aov.common.core.skills.caster.cores;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;

import java.util.List;

public class CasterSkillCore3 extends AoVSkill {

	private static final List<AbilityBase> spells = Lists.newArrayList();

	private static final int COST = 1;
	private static final int LEVEL = 6;
	private static final int SPENT = 0;
	private static final int CHARGES = 1;
	private static final int SPELLPOWER = 15;
	private static final int DODGE = 0;
	private static final int DOUBLESTRIKE = 0;
	private static final boolean SELECTIVE_FOCUS = false;

	static {
		spells.add(AbilityBase.flameStrike);
	}

	public CasterSkillCore3() {
		super(spells,

				new TextComponentTranslation("aov.skill.caster.core.3.name"),

				new TextComponentTranslation("aov.skill.caster.core.3.req"),

				new TextComponentTranslation("aov.skill.global.minlevel", LEVEL),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.skill.global.spellpower", SPELLPOWER),

				new TextComponentTranslation("aov.skill.global.charge", CHARGES),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.skill.caster.core.3.desc")

		);
	}

	@Override
	public ResourceLocation getIcon() {
		return AbilityBase.flameStrike.getIcon();
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
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkills.caster_core_2;
	}

	@Deprecated
	public String getName() {
		return "CasterSkillCore3";
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
