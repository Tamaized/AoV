package tamaized.aov.common.core.skills.defender.cores;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.SkillIcons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class DefenderSkillCore3 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public DefenderSkillCore3() {
		super(spells,

				TextFormatting.AQUA + "Defender Core 3",

				TextFormatting.RED + "Requires: Defender Core 2",

				TextFormatting.RED + "Requires: Level 6",

				"",

				TextFormatting.YELLOW + "While a shield is equiped, you deal more knockback."

		);
	}

	@Override
	public String getName() {
		return "DefenderSkillCore3";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.defender;
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.defender_core_2;
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
