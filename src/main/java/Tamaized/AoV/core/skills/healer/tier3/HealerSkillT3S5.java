package Tamaized.AoV.core.skills.healer.tier3;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class HealerSkillT3S5 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.invokeMass);
	}

	public HealerSkillT3S5() {
		super(spells,

				TextFormatting.AQUA + "Invoke Mass",

				TextFormatting.RED + "Requires: 8 Points Spent in Tree",

				"",

				TextFormatting.YELLOW + "Added Ability: Invoke Mass",

				TextFormatting.YELLOW + " While this toggle is active",

				TextFormatting.YELLOW + " your spells cost double but",

				TextFormatting.YELLOW + " can be cast as an AoE (Area of Effect)"

		);
	}

	@Override
	public String getName() {
		return "HealerSkillT3S5";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return AbilityBase.invokeMass.getIcon();
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.healer_core_1;
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
