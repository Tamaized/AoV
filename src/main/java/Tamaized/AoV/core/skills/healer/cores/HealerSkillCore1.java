package Tamaized.AoV.core.skills.healer.cores;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class HealerSkillCore1 extends AoVSkill {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid + ":textures/skills/HealerCore.png");

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.cureLightWounds);
	}

	public HealerSkillCore1() {
		super(spells,

				TextFormatting.AQUA + "Class Core: Healer",

				TextFormatting.RED + "Obtaining this Skill",

				TextFormatting.RED + "prevents you from taking",

				TextFormatting.RED + "skills from any other class!",

				"",

				TextFormatting.GREEN + "+1 Charge",

				"",

				TextFormatting.YELLOW + "Added Spell: Cure Light Wounds"

		);
	}

	@Override
	public String getName() {
		return "HealerSkillCore1";
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
