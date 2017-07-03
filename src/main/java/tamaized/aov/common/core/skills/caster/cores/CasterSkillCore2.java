package tamaized.aov.common.core.skills.caster.cores;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.SkillIcons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class CasterSkillCore2 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public CasterSkillCore2() {
		super(spells,

				TextFormatting.AQUA + "Caster Core 2",

				TextFormatting.RED + "Requires: Class Core Caster",

				TextFormatting.RED + "Requires: Level 3",

				"",

				TextFormatting.GREEN + "+15 Spell Power"

		);
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 15, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.spellpower;
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.caster_core_1;
	}

	@Override
	public String getName() {
		return "CasterSkillCore2";
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
