package Tamaized.AoV.core.skills.defender.tier2;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class DefenderSkillT2S3 extends AoVSkill {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid + ":textures/skills/DefenderT2S3.png");

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public DefenderSkillT2S3() {
		super(spells,

				TextFormatting.AQUA + "Dodge II",

				TextFormatting.RED + "Requires: 4 Points Spent in Tree",

				TextFormatting.RED + "Requires: Dodge I",

				"",

				TextFormatting.GREEN + "+5% Dodge"

		);
	}

	@Override
	public String getName() {
		return "DefenderSkillT2S3";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, 5, 0, false);
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
		return AoVSkill.defender_tier_1_3;
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
		return 4;
	}

}
