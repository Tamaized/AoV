package Tamaized.AoV.core.skills.defender.cores;

import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.SkillIcons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class DefenderSkillCore2 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public DefenderSkillCore2() {
		super(spells,

				TextFormatting.AQUA + "Defender Core 2",

				TextFormatting.RED + "Requires: Class Core Defender",

				TextFormatting.RED + "Requires: Level 3",

				"",

				TextFormatting.GREEN + "+5% Doublestrike"

		);
	}

	@Override
	public String getName() {
		return "DefenderSkillCore2";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, 0, 5, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.doublestrike;
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
		return 3;
	}

	@Override
	public int getSpentPoints() {
		return 0;
	}

}
