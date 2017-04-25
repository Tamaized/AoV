package Tamaized.AoV.core.skills.caster.cores;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.SkillIcons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class CasterSkillCapStone extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public CasterSkillCapStone() {
		super(spells,

				TextFormatting.AQUA + "Capstone: Caster",

				TextFormatting.RED + "Requires: Caster Core 4",

				TextFormatting.RED + "Requires: Level 15",

				"",

				TextFormatting.GREEN + "+50 Spell Power",

				TextFormatting.GREEN + "+4 Charges"

		);
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(4, 50, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.charges;
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public String getName() {
		return "CasterSkillCapStone";
	}

	@Override
	public AoVSkill getParent() {
		return caster_core_4;
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
