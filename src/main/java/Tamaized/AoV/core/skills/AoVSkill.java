package Tamaized.AoV.core.skills;

import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.caster.cores.*;
import Tamaized.AoV.core.skills.caster.tier1.*;
import Tamaized.AoV.core.skills.caster.tier2.*;
import Tamaized.AoV.core.skills.caster.tier3.*;
import Tamaized.AoV.core.skills.caster.tier4.*;
import Tamaized.AoV.core.skills.defender.cores.*;
import Tamaized.AoV.core.skills.defender.tier1.*;
import Tamaized.AoV.core.skills.defender.tier2.*;
import Tamaized.AoV.core.skills.defender.tier3.*;
import Tamaized.AoV.core.skills.defender.tier4.*;
import Tamaized.AoV.core.skills.healer.cores.*;
import Tamaized.AoV.core.skills.healer.tier1.*;
import Tamaized.AoV.core.skills.healer.tier2.*;
import Tamaized.AoV.core.skills.healer.tier3.*;
import Tamaized.AoV.core.skills.healer.tier4.*;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class AoVSkill {

	private static final List<AoVSkill> registry = new ArrayList<AoVSkill>();

	public static final int getID(AoVSkill skill) {
		return registry.contains(skill) ? registry.indexOf(skill) : -1;
	}

	public final int getID() {
		return getID(this);
	}

	public static final AoVSkill getSkillFromID(int id) {
		return id >= 0 && id < registry.size() ? registry.get(id) : null;
	}

	/* Healer */
	public static final AoVSkill healer_core_1 = new HealerSkillCore1();
	public static final AoVSkill healer_core_2 = new HealerSkillCore2();
	public static final AoVSkill healer_core_3 = new HealerSkillCore3();
	public static final AoVSkill healer_core_4 = new HealerSkillCore4();
	public static final AoVSkill healer_capstone = new HealerSkillCapStone();

	public static final AoVSkill healer_tier_1_1 = new HealerSkillT1S1();
	public static final AoVSkill healer_tier_1_2 = new HealerSkillT1S2();
	public static final AoVSkill healer_tier_1_3 = new HealerSkillT1S3();
	public static final AoVSkill healer_tier_1_4 = new HealerSkillT1S4();
	public static final AoVSkill healer_tier_1_5 = new HealerSkillT1S5();

	public static final AoVSkill healer_tier_2_1 = new HealerSkillT2S1();
	public static final AoVSkill healer_tier_2_2 = new HealerSkillT2S2();
	public static final AoVSkill healer_tier_2_3 = new HealerSkillT2S3();
	public static final AoVSkill healer_tier_2_4 = new HealerSkillT2S4();
	public static final AoVSkill healer_tier_2_5 = new HealerSkillT2S5();

	public static final AoVSkill healer_tier_3_1 = new HealerSkillT3S1();
	public static final AoVSkill healer_tier_3_2 = new HealerSkillT3S2();
	public static final AoVSkill healer_tier_3_3 = new HealerSkillT3S3();
	public static final AoVSkill healer_tier_3_4 = new HealerSkillT3S4();
	public static final AoVSkill healer_tier_3_5 = new HealerSkillT3S5();

	public static final AoVSkill healer_tier_4_1 = new HealerSkillT4S1();
	public static final AoVSkill healer_tier_4_2 = new HealerSkillT4S2();
	public static final AoVSkill healer_tier_4_3 = new HealerSkillT4S3();
	public static final AoVSkill healer_tier_4_4 = new HealerSkillT4S4();
	public static final AoVSkill healer_tier_4_5 = new HealerSkillT4S5();

	/* Caster */
	public static final AoVSkill caster_core_1 = new CasterSkillCore1();
	public static final AoVSkill caster_core_2 = new CasterSkillCore2();
	public static final AoVSkill caster_core_3 = new CasterSkillCore3();
	public static final AoVSkill caster_core_4 = new CasterSkillCore4();
	public static final AoVSkill caster_capstone = new CasterSkillCapStone();

	public static final AoVSkill caster_tier_1_1 = new CasterSkillT1S1();
	public static final AoVSkill caster_tier_1_2 = new CasterSkillT1S2();
	public static final AoVSkill caster_tier_1_3 = new CasterSkillT1S3();
	public static final AoVSkill caster_tier_1_4 = new CasterSkillT1S4();
	public static final AoVSkill caster_tier_1_5 = new CasterSkillT1S5();

	public static final AoVSkill caster_tier_2_1 = new CasterSkillT2S1();
	public static final AoVSkill caster_tier_2_2 = new CasterSkillT2S2();
	public static final AoVSkill caster_tier_2_3 = new CasterSkillT2S3();
	public static final AoVSkill caster_tier_2_4 = new CasterSkillT2S4();
	public static final AoVSkill caster_tier_2_5 = new CasterSkillT2S5();

	public static final AoVSkill caster_tier_3_1 = new CasterSkillT3S1();
	public static final AoVSkill caster_tier_3_2 = new CasterSkillT3S2();
	public static final AoVSkill caster_tier_3_3 = new CasterSkillT3S3();
	public static final AoVSkill caster_tier_3_4 = new CasterSkillT3S4();
	public static final AoVSkill caster_tier_3_5 = new CasterSkillT3S5();

	public static final AoVSkill caster_tier_4_1 = new CasterSkillT4S1();
	public static final AoVSkill caster_tier_4_2 = new CasterSkillT4S2();
	public static final AoVSkill caster_tier_4_3 = new CasterSkillT4S3();
	public static final AoVSkill caster_tier_4_4 = new CasterSkillT4S4();
	public static final AoVSkill caster_tier_4_5 = new CasterSkillT4S5();

	/* Defender */
	public static final AoVSkill defender_core_1 = new DefenderSkillCore1();
	public static final AoVSkill defender_core_2 = new DefenderSkillCore2();
	public static final AoVSkill defender_core_3 = new DefenderSkillCore3();
	public static final AoVSkill defender_core_4 = new DefenderSkillCore4();
	public static final AoVSkill defender_capstone = new DefenderSkillCapStone();

	public static final AoVSkill defender_tier_1_1 = new DefenderSkillT1S1();
	public static final AoVSkill defender_tier_1_2 = new DefenderSkillT1S2();
	public static final AoVSkill defender_tier_1_3 = new DefenderSkillT1S3();
	public static final AoVSkill defender_tier_1_4 = new DefenderSkillT1S4();
	public static final AoVSkill defender_tier_1_5 = new DefenderSkillT1S5();

	public static final AoVSkill defender_tier_2_1 = new DefenderSkillT2S1();
	public static final AoVSkill defender_tier_2_2 = new DefenderSkillT2S2();
	public static final AoVSkill defender_tier_2_3 = new DefenderSkillT2S3();
	public static final AoVSkill defender_tier_2_4 = new DefenderSkillT2S4();
	public static final AoVSkill defender_tier_2_5 = new DefenderSkillT2S5();

	public static final AoVSkill defender_tier_3_1 = new DefenderSkillT3S1();
	public static final AoVSkill defender_tier_3_2 = new DefenderSkillT3S2();
	public static final AoVSkill defender_tier_3_3 = new DefenderSkillT3S3();
	public static final AoVSkill defender_tier_3_4 = new DefenderSkillT3S4();
	public static final AoVSkill defender_tier_3_5 = new DefenderSkillT3S5();

	public static final AoVSkill defender_tier_4_1 = new DefenderSkillT4S1();
	public static final AoVSkill defender_tier_4_2 = new DefenderSkillT4S2();
	public static final AoVSkill defender_tier_4_3 = new DefenderSkillT4S3();
	public static final AoVSkill defender_tier_4_4 = new DefenderSkillT4S4();
	public static final AoVSkill defender_tier_4_5 = new DefenderSkillT4S5();

	private final Buffs buffs;
	private final List<AbilityBase> abilities;
	private final List<String> description;

	public AoVSkill(List<AbilityBase> spells, String... desc) {
		buffs = setupBuffs();
		description = new ArrayList<String>();
		for (String s : desc)
			description.add(s);
		abilities = spells;
		registry.add(this);
	}

	public final List<AbilityBase> getAbilities() {
		return abilities;
	}

	public abstract boolean isClassCore();

	public abstract String getName();

	public abstract AoVSkill getParent();

	public abstract int getCost();

	public abstract int getLevel();

	public abstract int getSpentPoints();

	protected abstract Buffs setupBuffs();

	public final Buffs getBuffs() {
		return buffs;
	}

	public abstract ResourceLocation getIcon();

	public final List<String> getDescription() {
		return description;
	}

	public final class Buffs {

		public final int charges;
		public final int spellPower;
		public final int dodge;
		public final int doublestrike;

		public final boolean selectiveFocus;

		public Buffs(int c, int sP, int dodge, int dStrike, boolean sel) {
			charges = c;
			spellPower = sP;
			this.dodge = dodge;
			doublestrike = dStrike;
			selectiveFocus = sel;
		}

	}

}
