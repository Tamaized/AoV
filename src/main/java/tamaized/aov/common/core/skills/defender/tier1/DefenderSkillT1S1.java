package tamaized.aov.common.core.skills.defender.tier1;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class DefenderSkillT1S1 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.aid);
	}

	public DefenderSkillT1S1() {
		super(spells,

				TextFormatting.AQUA + "Aid",

				TextFormatting.RED + "Requires: 1 Point Spent in Tree",

				"",

				TextFormatting.YELLOW + "Added Spell: Aid"

		);
	}

	@Override
	public String getName() {
		return "DefenderSkillT1S1";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return AbilityBase.aid.getIcon();
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.defender_core_1;
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
		return 1;
	}

}
