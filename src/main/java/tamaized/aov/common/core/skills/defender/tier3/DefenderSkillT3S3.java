package tamaized.aov.common.core.skills.defender.tier3;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.SkillIcons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class DefenderSkillT3S3 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public DefenderSkillT3S3() {
		super(spells,

				TextFormatting.AQUA + "Dodge III",

				TextFormatting.RED + "Requires: 8 Points Spent in Tree",

				TextFormatting.RED + "Requires: Dodge II",

				"",

				TextFormatting.GREEN + "+5% Dodge"

		);
	}

	@Override
	public String getName() {
		return "DefenderSkillT3S3";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, 5, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.dodge;
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.defender_tier_2_3;
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
		return 8;
	}

}
