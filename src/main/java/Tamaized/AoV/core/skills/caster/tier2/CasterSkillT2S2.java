package Tamaized.AoV.core.skills.caster.tier2;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.AoVSkill.Buffs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class CasterSkillT2S2 extends AoVSkill {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid + ":textures/skills/CasterT2S2.png");

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public CasterSkillT2S2() {
		super(spells,

				TextFormatting.AQUA + "Charges I",

				TextFormatting.RED + "Requires: 4 Points Spent in Tree",

				"",

				TextFormatting.GREEN + "+1 Charge"

		);
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(1, 0, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}

	public String getName() {
		return "CasterSkillT2S2";
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
		return 4;
	}

}
