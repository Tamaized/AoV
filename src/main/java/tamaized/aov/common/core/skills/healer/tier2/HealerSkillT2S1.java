package tamaized.aov.common.core.skills.healer.tier2;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class HealerSkillT2S1 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.cureSeriousWounds);
	}

	public HealerSkillT2S1() {
		super(spells,

				TextFormatting.AQUA + "Cure Serious Wounds",

				TextFormatting.RED + "Requires: 4 Points Spent in Tree",

				TextFormatting.RED + "Requires: Cure Moderate Wounds",

				"",

				TextFormatting.YELLOW + "Added Spell: Cure Serious Wounds"

		);
	}

	@Override
	public String getName() {
		return "HealerSkillT2S1";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return AbilityBase.cureSeriousWounds.getIcon();
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.healer_tier_1_1;
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
