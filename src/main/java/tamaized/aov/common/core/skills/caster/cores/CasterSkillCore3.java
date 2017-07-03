package tamaized.aov.common.core.skills.caster.cores;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class CasterSkillCore3 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.flameStrike);
	}

	public CasterSkillCore3() {
		super(spells, TextFormatting.AQUA + "Caster Core 3",

				TextFormatting.RED + "Requires: Caster Core 2",

				TextFormatting.RED + "Requires: Level 6",

				"",

				TextFormatting.GREEN + "+15 Spell Power",

				TextFormatting.GREEN + "+1 Charge",

				"",

				TextFormatting.YELLOW + "Added Spell: Flame Strike"

		);
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(1, 15, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return AbilityBase.flameStrike.getIcon();
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.caster_core_2;
	}

	@Override
	public String getName() {
		return "CasterSkillCore3";
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
