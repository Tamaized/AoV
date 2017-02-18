package Tamaized.AoV.core.skills.caster.cores;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class CasterSkillCore2 extends AoVSkill {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid + ":textures/skills/test.png");

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.nimbusRay);
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
		return icon;
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
