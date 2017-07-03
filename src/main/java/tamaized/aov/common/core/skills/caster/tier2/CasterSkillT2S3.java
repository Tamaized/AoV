package tamaized.aov.common.core.skills.caster.tier2;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.SkillIcons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class CasterSkillT2S3 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public CasterSkillT2S3() {
		super(spells,

				TextFormatting.AQUA + "Spell Power II",

				TextFormatting.RED + "Requires: 4 Points Spent in Tree",

				TextFormatting.RED + "Requires: Spell Power I",

				"",

				TextFormatting.GREEN + "+15 Spell Power"

		);
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 15, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.spellpower;
	}

	public String getName() {
		return "CasterSkillT2S3";
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.caster_tier_1_3;
	}

	@Override
	public int getCost() {
		return 1;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public int getSpentPoints() {
		return 4;
	}

}
