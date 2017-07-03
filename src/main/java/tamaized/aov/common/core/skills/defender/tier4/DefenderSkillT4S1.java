package tamaized.aov.common.core.skills.defender.tier4;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class DefenderSkillT4S1 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.stalwartPact);
	}

	public DefenderSkillT4S1() {
		super(spells,

				TextFormatting.AQUA + "Stalwart Pact",

				TextFormatting.RED + "Requires: 12 Points Spent in Tree",

				TextFormatting.RED + "Requires: Zeal",

				"",

				TextFormatting.YELLOW + "Added Spell: Stalwart Pact"

		);
	}

	@Override
	public String getName() {
		return "DefenderSkillT4S1";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return AbilityBase.stalwartPact.getIcon();
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.defender_tier_3_1;
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
