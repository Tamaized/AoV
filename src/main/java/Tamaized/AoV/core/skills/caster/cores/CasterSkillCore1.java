package Tamaized.AoV.core.skills.caster.cores;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class CasterSkillCore1 extends AoVSkill {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid + ":textures/skills/test.png");

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.nimbusRay);
	}

	public CasterSkillCore1() {
		super(spells,

				TextFormatting.AQUA + "Class Core: Offensive Caster",

				TextFormatting.RED + "Obtaining this Skill",

				TextFormatting.RED + "prevents you from taking",

				TextFormatting.RED + "skills from any other class!",

				"",

				TextFormatting.GREEN + "+1 Charge",

				TextFormatting.GREEN + "+10 Spell Power",

				"",

				TextFormatting.YELLOW + "Added Spell: Nimbus Ray"

		);
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(1, 10, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}

	@Override
	public boolean isClassCore() {
		return true;
	}

	@Override
	public String getName() {
		return "CasterSkillCore1";
	}

	@Override
	public AoVSkill getParent() {
		return null;
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
		return 0;
	}

}
