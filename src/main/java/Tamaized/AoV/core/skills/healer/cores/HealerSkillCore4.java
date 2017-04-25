package Tamaized.AoV.core.skills.healer.cores;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class HealerSkillCore4 extends AoVSkill {

	private static final List<AbilityBase> spells = new ArrayList<AbilityBase>();

	static {
		spells.add(AbilityBase.posEnergyAura);
	}

	public HealerSkillCore4() {
		super(spells,

				TextFormatting.AQUA + "Healer Core 4",

				TextFormatting.RED + "Requires: Healer Core 3",

				TextFormatting.RED + "Requires: Level 12",

				"",

				TextFormatting.GREEN + "+10 Spell Power",

				TextFormatting.GREEN + "+1 Charge",

				"",

				TextFormatting.YELLOW + "Added Spell: Positive Energy Aura"

		);
	}

	@Override
	public String getName() {
		return "HealerSkillCore4";
	}

	@Override
	protected Buffs setupBuffs() {
		return new Buffs(1, 10, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return AbilityBase.posEnergyAura.getIcon();
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return AoVSkill.healer_core_3;
	}

	@Override
	public int getCost() {
		return 1;
	}

	@Override
	public int getLevel() {
		return 12;
	}

	@Override
	public int getSpentPoints() {
		return 0;
	}

}
