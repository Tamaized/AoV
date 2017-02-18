package Tamaized.AoV.core.skills.caster.tier4;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class CasterSkillT4S5 extends AoVSkill {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid + ":textures/skills/CasterT4S5.png");

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public CasterSkillT4S5() {
		super(spells,

				TextFormatting.AQUA + "Selective Focus",

				TextFormatting.RED + "Requires: 12 Points Spent in Tree",

				"",

				TextFormatting.DARK_PURPLE + "Your Spells no longer",

				TextFormatting.DARK_PURPLE + "affect Monsters if they",

				TextFormatting.DARK_PURPLE + "benefit from the Spell",

				TextFormatting.DARK_PURPLE + "or Friendly Players/Creatures",

				TextFormatting.DARK_PURPLE + "if they would be harmed by the spell."

		);
	}

	@Override
	public String getName() {
		return "CasterSkillT4S5";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(0, 0, 0, 0, true);
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
	public int getCost() {
		return 1;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public int getSpentPoints() {
		return 12;
	}

}
