package tamaized.aov.common.core.skills.caster.tier4;

import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class CasterSkillT4S2 extends AoVSkill {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid + ":textures/skills/CasterT4S2.png");

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {

	}

	public CasterSkillT4S2() {
		super(spells,

				TextFormatting.AQUA + "Charges II",

				TextFormatting.RED + "Requires: 12 Points Spent in Tree",

				TextFormatting.RED + "Requires: Charges I",

				"",

				TextFormatting.GREEN + "+2 Charges"

		);
	}

	@Override
	public String getName() {
		return "CasterSkillT4S2";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(1, 0, 0, 0, false);
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
		return AoVSkill.caster_tier_3_2;
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
