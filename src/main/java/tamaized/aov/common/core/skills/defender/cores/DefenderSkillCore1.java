package tamaized.aov.common.core.skills.defender.cores;

import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.SkillIcons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class DefenderSkillCore1 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public DefenderSkillCore1() {
		super(spells,

				TextFormatting.AQUA + "Class Core: Defender",

				TextFormatting.RED + "Obtaining this Skill",

				TextFormatting.RED + "prevents you from taking",

				TextFormatting.RED + "skills from any other class!",

				"",

				TextFormatting.GREEN + "+5% Dodge"


		);
	}

	@Override
	public String getName() {
		return "DefenderSkillCore1";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(1, 0, 5, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.dodge;
	}

	@Override
	public boolean isClassCore() {
		return true;
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
