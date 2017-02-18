package Tamaized.AoV.core.skills.healer.cores;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class HealerSkillCore2 extends AoVSkill {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid + ":textures/skills/test.png");

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public HealerSkillCore2() {
		super(spells,

				TextFormatting.AQUA + "Healer Core 2",

				TextFormatting.RED + "Requires: Class Core Healer",

				TextFormatting.RED + "Requires: Level 3",

				"",

				TextFormatting.GREEN + "+10 Spell Power"

		);
	}

	@Override
	public String getName() {
		return "HealerSkillCore2";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 10, 0, 0, false);
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
		return 3;
	}

	@Override
	public int getSpentPoints() {
		return 0;
	}

}
