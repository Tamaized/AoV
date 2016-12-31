package Tamaized.AoV.core.skills;

import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCapStone;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore1;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore2;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore3;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore4;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S1;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S2;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S3;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S4;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S5;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S1;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S2;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S3;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S4;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S5;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S1;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S2;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S3;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S4;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S5;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S1;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S2;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S3;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S4;
import Tamaized.AoV.core.skills.caster.tier4.CasterSkillT4S5;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCapStone;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore1;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore2;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore3;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore4;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S1;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S2;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S3;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S4;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S5;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S1;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S2;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S3;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S4;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S5;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S1;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S2;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S3;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S4;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S5;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S1;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S2;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S3;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S4;
import Tamaized.AoV.core.skills.healer.tier4.HealerSkillT4S5;
import net.minecraft.util.ResourceLocation;

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

	public abstract boolean isCore();

	public abstract String getName();

	public abstract AoVSkill getParent();

	public abstract int getCost();

	public abstract int getLevel();

	public abstract int getSpentPoints();

	public abstract List<AbilityBase> getSpells();

	protected abstract Buffs setupBuffs();

	public final Buffs getBuffs() {
		return buffs;
	}

	public abstract ResourceLocation getIcon();

	public final List<String> getDescription() {
		return description;
	}

	public static void registerSkills() {
		/* Healer */ {
			new HealerSkillCore1();
			new HealerSkillCore2();
			new HealerSkillCore3();
			new HealerSkillCore4();
			new HealerSkillCapStone();

			new HealerSkillT1S1();
			new HealerSkillT1S2();
			new HealerSkillT1S3();
			new HealerSkillT1S4();
			new HealerSkillT1S5();

			new HealerSkillT2S1();
			new HealerSkillT2S2();
			new HealerSkillT2S3();
			new HealerSkillT2S4();
			new HealerSkillT2S5();

			new HealerSkillT3S1();
			new HealerSkillT3S2();
			new HealerSkillT3S3();
			new HealerSkillT3S4();
			new HealerSkillT3S5();

			new HealerSkillT4S1();
			new HealerSkillT4S2();
			new HealerSkillT4S3();
			new HealerSkillT4S4();
			new HealerSkillT4S5();
		}

		/* Caster */ {
			new CasterSkillCore1();
			new CasterSkillCore2();
			new CasterSkillCore3();
			new CasterSkillCore4();
			new CasterSkillCapStone();

			new CasterSkillT1S1();
			new CasterSkillT1S2();
			new CasterSkillT1S3();
			new CasterSkillT1S4();
			new CasterSkillT1S5();

			new CasterSkillT2S1();
			new CasterSkillT2S2();
			new CasterSkillT2S3();
			new CasterSkillT2S4();
			new CasterSkillT2S5();

			new CasterSkillT3S1();
			new CasterSkillT3S2();
			new CasterSkillT3S3();
			new CasterSkillT3S4();
			new CasterSkillT3S5();

			new CasterSkillT4S1();
			new CasterSkillT4S2();
			new CasterSkillT4S3();
			new CasterSkillT4S4();
			new CasterSkillT4S5();
		}

	}

	public final class Buffs {

		public final int charges;
		public final int spellPower;

		public final boolean selectiveFocus;

		public Buffs(int c, int sP, boolean sel) {
			charges = c;
			spellPower = sP;
			selectiveFocus = sel;
		}

	}

}
