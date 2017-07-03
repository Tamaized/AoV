package tamaized.aov.common.core.skills.defender.cores;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.SkillIcons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class DefenderSkillCapStone extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public DefenderSkillCapStone() {
		super(spells,

				TextFormatting.AQUA + "Capstone: Defender",

				TextFormatting.RED + "Requires: Defender Core 4",

				TextFormatting.RED + "Requires: Level 15",

				"",

				TextFormatting.YELLOW + "Shields you have equiped, if damaged, are fully repaired"


		);
	}

	@Override
	public String getName() {
		return "DefenderSkillCapStone";
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
		return AoVSkill.defender_core_4;
	}

	@Override
	public int getCost() {
		return 1;
	}

	@Override
	public int getLevel() {
		return 15;
	}

	@Override
	public int getSpentPoints() {
		return 0;
	}

}
