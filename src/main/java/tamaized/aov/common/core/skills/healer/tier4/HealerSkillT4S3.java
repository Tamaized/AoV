package tamaized.aov.common.core.skills.healer.tier4;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.SkillIcons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class HealerSkillT4S3 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public HealerSkillT4S3() {
		super(spells,

				TextFormatting.AQUA + "Spell Power IV",

				TextFormatting.RED + "Requires: 12 Points Spent in Tree",

				TextFormatting.RED + "Requires: Spell Power III",

				"",

				TextFormatting.GREEN + "+20 Spell Power"

		);
	}

	@Override
	public String getName() {
		return "HealerSkillT4S3";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 20, 0, 0, false);
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
		return AoVSkill.healer_tier_3_3;
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
		return 12;
	}

}
