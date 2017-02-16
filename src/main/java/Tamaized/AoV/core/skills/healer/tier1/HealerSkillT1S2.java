package Tamaized.AoV.core.skills.healer.tier1;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class HealerSkillT1S2 extends AoVSkill {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid + ":textures/skills/HealerT1S2.png");

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.curePoison);
	}

	public HealerSkillT1S2() {
		super(spells,

				TextFormatting.AQUA + "Cure Poison",

				TextFormatting.RED + "Requires: 1 Point Spent in Tree",

				"",

				TextFormatting.YELLOW + "Added Spell: Cure Poison"

		);
	}

	@Override
	public String getName() {
		return "HealerSkillT1S2";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
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
		return 1;
	}

}
