package tamaized.aov.common.core.skills;

import tamaized.aov.common.core.skills.astro.cores.AstroSkillCore1;
import tamaized.aov.common.core.skills.astro.cores.AstroSkillCore2;
import tamaized.aov.common.core.skills.astro.cores.AstroSkillCore3;
import tamaized.aov.common.core.skills.astro.cores.AstroSkillCore4;
import tamaized.aov.common.core.skills.astro.tier1.AstroSkillT1S1;
import tamaized.aov.common.core.skills.astro.tier1.AstroSkillT1S3;
import tamaized.aov.common.core.skills.astro.tier1.AstroSkillT1S5;
import tamaized.aov.common.core.skills.astro.tier2.AstroSkillT2S1;
import tamaized.aov.common.core.skills.astro.tier2.AstroSkillT2S3;
import tamaized.aov.common.core.skills.astro.tier2.AstroSkillT2S5;
import tamaized.aov.common.core.skills.astro.tier3.AstroSkillT3S1;
import tamaized.aov.common.core.skills.astro.tier3.AstroSkillT3S2;
import tamaized.aov.common.core.skills.astro.tier3.AstroSkillT3S3;
import tamaized.aov.common.core.skills.astro.tier3.AstroSkillT3S5;
import tamaized.aov.common.core.skills.astro.tier4.AstroSkillT4S1;
import tamaized.aov.common.core.skills.astro.tier4.AstroSkillT4S2;
import tamaized.aov.common.core.skills.astro.tier4.AstroSkillT4S3;
import tamaized.aov.common.core.skills.astro.tier4.AstroSkillT4S4;
import tamaized.aov.common.core.skills.astro.tier4.AstroSkillT4S5;
import tamaized.aov.common.core.skills.caster.cores.CasterSkillCapStone;
import tamaized.aov.common.core.skills.caster.cores.CasterSkillCore1;
import tamaized.aov.common.core.skills.caster.cores.CasterSkillCore2;
import tamaized.aov.common.core.skills.caster.cores.CasterSkillCore3;
import tamaized.aov.common.core.skills.caster.cores.CasterSkillCore4;
import tamaized.aov.common.core.skills.caster.tier1.CasterSkillT1S1;
import tamaized.aov.common.core.skills.caster.tier1.CasterSkillT1S3;
import tamaized.aov.common.core.skills.caster.tier2.CasterSkillT2S1;
import tamaized.aov.common.core.skills.caster.tier2.CasterSkillT2S2;
import tamaized.aov.common.core.skills.caster.tier2.CasterSkillT2S3;
import tamaized.aov.common.core.skills.caster.tier3.CasterSkillT3S1;
import tamaized.aov.common.core.skills.caster.tier3.CasterSkillT3S2;
import tamaized.aov.common.core.skills.caster.tier3.CasterSkillT3S3;
import tamaized.aov.common.core.skills.caster.tier4.CasterSkillT4S1;
import tamaized.aov.common.core.skills.caster.tier4.CasterSkillT4S3;
import tamaized.aov.common.core.skills.caster.tier4.CasterSkillT4S4;
import tamaized.aov.common.core.skills.caster.tier4.CasterSkillT4S5;
import tamaized.aov.common.core.skills.defender.cores.DefenderSkillCapStone;
import tamaized.aov.common.core.skills.defender.cores.DefenderSkillCore1;
import tamaized.aov.common.core.skills.defender.cores.DefenderSkillCore2;
import tamaized.aov.common.core.skills.defender.cores.DefenderSkillCore3;
import tamaized.aov.common.core.skills.defender.cores.DefenderSkillCore4;
import tamaized.aov.common.core.skills.defender.tier1.DefenderSkillT1S1;
import tamaized.aov.common.core.skills.defender.tier1.DefenderSkillT1S3;
import tamaized.aov.common.core.skills.defender.tier2.DefenderSkillT2S1;
import tamaized.aov.common.core.skills.defender.tier2.DefenderSkillT2S3;
import tamaized.aov.common.core.skills.defender.tier2.DefenderSkillT2S4;
import tamaized.aov.common.core.skills.defender.tier3.DefenderSkillT3S1;
import tamaized.aov.common.core.skills.defender.tier3.DefenderSkillT3S3;
import tamaized.aov.common.core.skills.defender.tier3.DefenderSkillT3S4;
import tamaized.aov.common.core.skills.defender.tier3.DefenderSkillT3S5;
import tamaized.aov.common.core.skills.defender.tier4.DefenderSkillT4S1;
import tamaized.aov.common.core.skills.defender.tier4.DefenderSkillT4S2;
import tamaized.aov.common.core.skills.defender.tier4.DefenderSkillT4S3;
import tamaized.aov.common.core.skills.defender.tier4.DefenderSkillT4S5;
import tamaized.aov.common.core.skills.healer.cores.HealerSkillCapStone;
import tamaized.aov.common.core.skills.healer.cores.HealerSkillCore1;
import tamaized.aov.common.core.skills.healer.cores.HealerSkillCore2;
import tamaized.aov.common.core.skills.healer.cores.HealerSkillCore3;
import tamaized.aov.common.core.skills.healer.cores.HealerSkillCore4;
import tamaized.aov.common.core.skills.healer.tier1.HealerSkillT1S1;
import tamaized.aov.common.core.skills.healer.tier1.HealerSkillT1S2;
import tamaized.aov.common.core.skills.healer.tier1.HealerSkillT1S3;
import tamaized.aov.common.core.skills.healer.tier2.HealerSkillT2S1;
import tamaized.aov.common.core.skills.healer.tier2.HealerSkillT2S2;
import tamaized.aov.common.core.skills.healer.tier2.HealerSkillT2S3;
import tamaized.aov.common.core.skills.healer.tier2.HealerSkillT2S4;
import tamaized.aov.common.core.skills.healer.tier3.HealerSkillT3S1;
import tamaized.aov.common.core.skills.healer.tier3.HealerSkillT3S2;
import tamaized.aov.common.core.skills.healer.tier3.HealerSkillT3S3;
import tamaized.aov.common.core.skills.healer.tier3.HealerSkillT3S5;
import tamaized.aov.common.core.skills.healer.tier4.HealerSkillT4S1;
import tamaized.aov.common.core.skills.healer.tier4.HealerSkillT4S2;
import tamaized.aov.common.core.skills.healer.tier4.HealerSkillT4S3;
import tamaized.aov.common.core.skills.healer.tier4.HealerSkillT4S5;

@SuppressWarnings("unused")
public class AoVSkills {

	/* Healer */
	public static AoVSkill healer_core_1;
	public static AoVSkill healer_core_2;
	public static AoVSkill healer_core_3;
	public static AoVSkill healer_core_4;
	public static AoVSkill healer_capstone;

	public static AoVSkill healer_tier_1_1;
	public static AoVSkill healer_tier_1_2;
	public static AoVSkill healer_tier_1_3;
	public static AoVSkill healer_tier_1_4;
	public static AoVSkill healer_tier_1_5;

	public static AoVSkill healer_tier_2_1;
	public static AoVSkill healer_tier_2_2;
	public static AoVSkill healer_tier_2_3;
	public static AoVSkill healer_tier_2_4;
	public static AoVSkill healer_tier_2_5;

	public static AoVSkill healer_tier_3_1;
	public static AoVSkill healer_tier_3_2;
	public static AoVSkill healer_tier_3_3;
	public static AoVSkill healer_tier_3_4;
	public static AoVSkill healer_tier_3_5;

	public static AoVSkill healer_tier_4_1;
	public static AoVSkill healer_tier_4_2;
	public static AoVSkill healer_tier_4_3;
	public static AoVSkill healer_tier_4_4;
	public static AoVSkill healer_tier_4_5;

	/* Caster */
	public static AoVSkill caster_core_1;
	public static AoVSkill caster_core_2;
	public static AoVSkill caster_core_3;
	public static AoVSkill caster_core_4;
	public static AoVSkill caster_capstone;

	public static AoVSkill caster_tier_1_1;
	public static AoVSkill caster_tier_1_2;
	public static AoVSkill caster_tier_1_3;
	public static AoVSkill caster_tier_1_4;
	public static AoVSkill caster_tier_1_5;

	public static AoVSkill caster_tier_2_1;
	public static AoVSkill caster_tier_2_2;
	public static AoVSkill caster_tier_2_3;
	public static AoVSkill caster_tier_2_4;
	public static AoVSkill caster_tier_2_5;

	public static AoVSkill caster_tier_3_1;
	public static AoVSkill caster_tier_3_2;
	public static AoVSkill caster_tier_3_3;
	public static AoVSkill caster_tier_3_4;
	public static AoVSkill caster_tier_3_5;

	public static AoVSkill caster_tier_4_1;
	public static AoVSkill caster_tier_4_2;
	public static AoVSkill caster_tier_4_3;
	public static AoVSkill caster_tier_4_4;
	public static AoVSkill caster_tier_4_5;

	/* Defender */
	public static AoVSkill defender_core_1;
	public static AoVSkill defender_core_2;
	public static AoVSkill defender_core_3;
	public static AoVSkill defender_core_4;
	public static AoVSkill defender_capstone;

	public static AoVSkill defender_tier_1_1;
	public static AoVSkill defender_tier_1_2;
	public static AoVSkill defender_tier_1_3;
	public static AoVSkill defender_tier_1_4;
	public static AoVSkill defender_tier_1_5;

	public static AoVSkill defender_tier_2_1;
	public static AoVSkill defender_tier_2_2;
	public static AoVSkill defender_tier_2_3;
	public static AoVSkill defender_tier_2_4;
	public static AoVSkill defender_tier_2_5;

	public static AoVSkill defender_tier_3_1;
	public static AoVSkill defender_tier_3_2;
	public static AoVSkill defender_tier_3_3;
	public static AoVSkill defender_tier_3_4;
	public static AoVSkill defender_tier_3_5;

	public static AoVSkill defender_tier_4_1;
	public static AoVSkill defender_tier_4_2;
	public static AoVSkill defender_tier_4_3;
	public static AoVSkill defender_tier_4_4;
	public static AoVSkill defender_tier_4_5;

	/* Astro */
	public static AoVSkill astro_core_1;
	public static AoVSkill astro_core_2;
	public static AoVSkill astro_core_3;
	public static AoVSkill astro_core_4;
	public static AoVSkill astro_capstone;

	public static AoVSkill astro_tier_1_1;
	public static AoVSkill astro_tier_1_2;
	public static AoVSkill astro_tier_1_3;
	public static AoVSkill astro_tier_1_4;
	public static AoVSkill astro_tier_1_5;

	public static AoVSkill astro_tier_2_1;
	public static AoVSkill astro_tier_2_2;
	public static AoVSkill astro_tier_2_3;
	public static AoVSkill astro_tier_2_4;
	public static AoVSkill astro_tier_2_5;

	public static AoVSkill astro_tier_3_1;
	public static AoVSkill astro_tier_3_2;
	public static AoVSkill astro_tier_3_3;
	public static AoVSkill astro_tier_3_4;
	public static AoVSkill astro_tier_3_5;

	public static AoVSkill astro_tier_4_1;
	public static AoVSkill astro_tier_4_2;
	public static AoVSkill astro_tier_4_3;
	public static AoVSkill astro_tier_4_4;
	public static AoVSkill astro_tier_4_5;

	public static void register() {
		/* Healer */
		healer_core_1 = new HealerSkillCore1();
		healer_core_2 = new HealerSkillCore2();
		healer_core_3 = new HealerSkillCore3();
		healer_core_4 = new HealerSkillCore4();
		healer_capstone = new HealerSkillCapStone();

		healer_tier_1_1 = new HealerSkillT1S1();
		healer_tier_1_2 = new HealerSkillT1S2();
		healer_tier_1_3 = new HealerSkillT1S3();
		healer_tier_1_4 = new NullSkill();
		healer_tier_1_5 = new NullSkill();

		healer_tier_2_1 = new HealerSkillT2S1();
		healer_tier_2_2 = new HealerSkillT2S2();
		healer_tier_2_3 = new HealerSkillT2S3();
		healer_tier_2_4 = new HealerSkillT2S4();
		healer_tier_2_5 = new NullSkill();

		healer_tier_3_1 = new HealerSkillT3S1();
		healer_tier_3_2 = new HealerSkillT3S2();
		healer_tier_3_3 = new HealerSkillT3S3();
		healer_tier_3_4 = new NullSkill();
		healer_tier_3_5 = new HealerSkillT3S5();

		healer_tier_4_1 = new HealerSkillT4S1();
		healer_tier_4_2 = new HealerSkillT4S2();
		healer_tier_4_3 = new HealerSkillT4S3();
		healer_tier_4_4 = new NullSkill();
		healer_tier_4_5 = new HealerSkillT4S5();

		/* Caster */
		caster_core_1 = new CasterSkillCore1();
		caster_core_2 = new CasterSkillCore2();
		caster_core_3 = new CasterSkillCore3();
		caster_core_4 = new CasterSkillCore4();
		caster_capstone = new CasterSkillCapStone();

		caster_tier_1_1 = new CasterSkillT1S1();
		caster_tier_1_2 = new NullSkill();
		caster_tier_1_3 = new CasterSkillT1S3();
		caster_tier_1_4 = new NullSkill();
		caster_tier_1_5 = new NullSkill();

		caster_tier_2_1 = new CasterSkillT2S1();
		caster_tier_2_2 = new CasterSkillT2S2();
		caster_tier_2_3 = new CasterSkillT2S3();
		caster_tier_2_4 = new NullSkill();
		caster_tier_2_5 = new NullSkill();

		caster_tier_3_1 = new CasterSkillT3S1();
		caster_tier_3_2 = new CasterSkillT3S2();
		caster_tier_3_3 = new CasterSkillT3S3();
		caster_tier_3_4 = new NullSkill();
		caster_tier_3_5 = new NullSkill();

		caster_tier_4_1 = new CasterSkillT4S1();
		caster_tier_4_2 = new NullSkill();
		caster_tier_4_3 = new CasterSkillT4S3();
		caster_tier_4_4 = new CasterSkillT4S4();
		caster_tier_4_5 = new CasterSkillT4S5();

		/* Defender */
		defender_core_1 = new DefenderSkillCore1();
		defender_core_2 = new DefenderSkillCore2();
		defender_core_3 = new DefenderSkillCore3();
		defender_core_4 = new DefenderSkillCore4();
		defender_capstone = new DefenderSkillCapStone();

		defender_tier_1_1 = new DefenderSkillT1S1();
		defender_tier_1_2 = new NullSkill();
		defender_tier_1_3 = new DefenderSkillT1S3();
		defender_tier_1_4 = new NullSkill();
		defender_tier_1_5 = new NullSkill();

		defender_tier_2_1 = new DefenderSkillT2S1();
		defender_tier_2_2 = new NullSkill();
		defender_tier_2_3 = new DefenderSkillT2S3();
		defender_tier_2_4 = new DefenderSkillT2S4();
		defender_tier_2_5 = new NullSkill();

		defender_tier_3_1 = new DefenderSkillT3S1();
		defender_tier_3_2 = new NullSkill();
		defender_tier_3_3 = new DefenderSkillT3S3();
		defender_tier_3_4 = new DefenderSkillT3S4();
		defender_tier_3_5 = new DefenderSkillT3S5();

		defender_tier_4_1 = new DefenderSkillT4S1();
		defender_tier_4_2 = new DefenderSkillT4S2();
		defender_tier_4_3 = new DefenderSkillT4S3();
		defender_tier_4_4 = new NullSkill();
		defender_tier_4_5 = new DefenderSkillT4S5();

		/* Astro */
		astro_core_1 = new AstroSkillCore1();
		astro_core_2 = new AstroSkillCore2();
		astro_core_3 = new AstroSkillCore3();
		astro_core_4 = new AstroSkillCore4();
		astro_capstone = new NullSkill();

		astro_tier_1_1 = new AstroSkillT1S1();
		astro_tier_1_2 = new NullSkill();
		astro_tier_1_3 = new AstroSkillT1S3();
		astro_tier_1_4 = new NullSkill();
		astro_tier_1_5 = new AstroSkillT1S5();

		astro_tier_2_1 = new AstroSkillT2S1();
		astro_tier_2_2 = new NullSkill();
		astro_tier_2_3 = new AstroSkillT2S3();
		astro_tier_2_4 = new NullSkill();
		astro_tier_2_5 = new AstroSkillT2S5();

		astro_tier_3_1 = new AstroSkillT3S1();
		astro_tier_3_2 = new AstroSkillT3S2();
		astro_tier_3_3 = new AstroSkillT3S3();
		astro_tier_3_4 = new NullSkill();
		astro_tier_3_5 = new AstroSkillT3S5();

		astro_tier_4_1 = new AstroSkillT4S1();
		astro_tier_4_2 = new AstroSkillT4S2();
		astro_tier_4_3 = new AstroSkillT4S3();
		astro_tier_4_4 = new AstroSkillT4S4();
		astro_tier_4_5 = new AstroSkillT4S5();
	}
}
