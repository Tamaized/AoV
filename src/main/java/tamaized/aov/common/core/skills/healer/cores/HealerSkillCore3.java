package tamaized.aov.common.core.skills.healer.cores;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class HealerSkillCore3 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.burst);
	}

	public HealerSkillCore3() {
		super(spells,

				TextFormatting.AQUA + "Healer Core 3",

				TextFormatting.RED + "Requires: Healer Core 2",

				TextFormatting.RED + "Requires: Level 6",

				"",

				TextFormatting.GREEN + "+10 Spell Power",

				TextFormatting.GREEN + "+1 Charge",

				"",

				TextFormatting.YELLOW + "Added Spell: Positive Energy Burst"

		);
	}

	@Override
	public String getName() {
		return "HealerSkillCore3";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(1, 10, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return AbilityBase.burst.getIcon();
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.healer_core_2;
	}

	@Override
	public int getCost() {
		return 1;
	}

	@Override
	public int getLevel() {
		return 6;
	}

	@Override
	public int getSpentPoints() {
		return 0;
	}

}
