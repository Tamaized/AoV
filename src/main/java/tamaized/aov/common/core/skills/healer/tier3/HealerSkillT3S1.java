package tamaized.aov.common.core.skills.healer.tier3;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class HealerSkillT3S1 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.cureCriticalWounds);
	}

	public HealerSkillT3S1() {
		super(spells,

				TextFormatting.AQUA + "Cure Critical Wounds",

				TextFormatting.RED + "Requires: 8 Points Spent in Tree",

				TextFormatting.RED + "Requires: Cure Serious Wounds",

				"",

				TextFormatting.YELLOW + "Added Spell: Cure Critical Wounds"

		);
	}

	@Override
	public String getName() {
		return "HealerSkillT3S1";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return AbilityBase.cureCriticalWounds.getIcon();
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.healer_tier_2_1;
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
