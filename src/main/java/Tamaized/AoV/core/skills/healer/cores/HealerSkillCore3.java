package Tamaized.AoV.core.skills.healer.cores;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class HealerSkillCore3 extends AoVSkill {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid + ":textures/skills/test.png");

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
		return icon;
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
