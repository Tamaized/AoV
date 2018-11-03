package tamaized.aov.common.core.skills;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.core.abilities.Abilities;

import java.util.List;
import java.util.Set;

public class AoVSkills {

	private static final List<AoVSkill> REGISTRY = Lists.newArrayList();
	private static final Set<AoVSkill> SELECTIVE_FOCUS_REGISTRY = Sets.newHashSet();

	/* Healer */
	public static AoVSkill healer_core_1;
	public static AoVSkill healer_core_2;
	public static AoVSkill healer_core_3;
	public static AoVSkill healer_core_4;
	public static AoVSkill healer_capstone;

	public static AoVSkill healer_tier_1_1;
	public static AoVSkill healer_tier_1_2;
	public static AoVSkill healer_tier_1_3;

	public static AoVSkill healer_tier_2_1;
	public static AoVSkill healer_tier_2_2;
	public static AoVSkill healer_tier_2_3;
	public static AoVSkill healer_tier_2_4;

	public static AoVSkill healer_tier_3_1;
	public static AoVSkill healer_tier_3_2;
	public static AoVSkill healer_tier_3_3;
	public static AoVSkill healer_tier_3_5;

	public static AoVSkill healer_tier_4_1;
	public static AoVSkill healer_tier_4_2;
	public static AoVSkill healer_tier_4_3;
	public static AoVSkill healer_tier_4_5;

	/* Caster */
	public static AoVSkill caster_core_1;
	public static AoVSkill caster_core_2;
	public static AoVSkill caster_core_3;
	public static AoVSkill caster_core_4;
	public static AoVSkill caster_capstone;

	public static AoVSkill caster_tier_1_1;
	public static AoVSkill caster_tier_1_3;

	public static AoVSkill caster_tier_2_1;
	public static AoVSkill caster_tier_2_2;
	public static AoVSkill caster_tier_2_3;

	public static AoVSkill caster_tier_3_1;
	public static AoVSkill caster_tier_3_2;
	public static AoVSkill caster_tier_3_3;

	public static AoVSkill caster_tier_4_1;
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
	public static AoVSkill defender_tier_1_3;

	public static AoVSkill defender_tier_2_1;
	public static AoVSkill defender_tier_2_3;
	public static AoVSkill defender_tier_2_4;

	public static AoVSkill defender_tier_3_1;
	public static AoVSkill defender_tier_3_3;
	public static AoVSkill defender_tier_3_4;
	public static AoVSkill defender_tier_3_5;

	public static AoVSkill defender_tier_4_1;
	public static AoVSkill defender_tier_4_2;
	public static AoVSkill defender_tier_4_3;
	public static AoVSkill defender_tier_4_5;

	/* Astro */
	public static AoVSkill astro_core_1;
	public static AoVSkill astro_core_2;
	public static AoVSkill astro_core_3;
	public static AoVSkill astro_core_4;
	public static AoVSkill astro_capstone;

	public static AoVSkill astro_tier_1_1;
	public static AoVSkill astro_tier_1_3;
	public static AoVSkill astro_tier_1_5;

	public static AoVSkill astro_tier_2_1;
	public static AoVSkill astro_tier_2_3;
	public static AoVSkill astro_tier_2_4;
	public static AoVSkill astro_tier_2_5;

	public static AoVSkill astro_tier_3_1;
	public static AoVSkill astro_tier_3_2;
	public static AoVSkill astro_tier_3_3;
	public static AoVSkill astro_tier_3_5;

	public static AoVSkill astro_tier_4_1;
	public static AoVSkill astro_tier_4_2;
	public static AoVSkill astro_tier_4_3;
	public static AoVSkill astro_tier_4_4;
	public static AoVSkill astro_tier_4_5;

	/* Druid */
	public static AoVSkill druid_core_1;
	public static AoVSkill druid_core_2;
	public static AoVSkill druid_core_3;
	public static AoVSkill druid_core_4;
	public static AoVSkill druid_capstone;

	public static AoVSkill druid_tier_1_1;
	public static AoVSkill druid_tier_1_2;
	public static AoVSkill druid_tier_1_3;
	public static AoVSkill druid_tier_1_4;
	public static AoVSkill druid_tier_1_5;

	public static AoVSkill druid_tier_2_2;
	public static AoVSkill druid_tier_2_3;
	public static AoVSkill druid_tier_2_4;
	public static AoVSkill druid_tier_2_5;

	public static AoVSkill druid_tier_3_1;
	public static AoVSkill druid_tier_3_2;
	public static AoVSkill druid_tier_3_3;
	public static AoVSkill druid_tier_3_4;
	public static AoVSkill druid_tier_3_5;

	public static AoVSkill druid_tier_4_1;
	public static AoVSkill druid_tier_4_2;
	public static AoVSkill druid_tier_4_3;
	public static AoVSkill druid_tier_4_4;
	public static AoVSkill druid_tier_4_5;

	public static void register() { // TODO: use IForgeRegistry
		REGISTRY.clear();

		/* Healer */
		healer_core_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.core.1.name"), Abilities.cureLightWounds.getIcon(), 0, 0, 1, 1, 0, 0, 0, true, null, Lists.newArrayList(Abilities.cureLightWounds)).setupTooltip(new TextComponentTranslation("aov.skill.healer.core.1.desc")));
		healer_core_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.core.2.name"), SkillIcons.spellpower, 3, 0, 0, 1, 10, 0, 0, false, healer_core_1, Lists.newArrayList()).setupTooltip(null));
		healer_core_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.core.3.name"), Abilities.burst.getIcon(), 6, 0, 1, 1, 10, 0, 0, false, healer_core_2, Lists.newArrayList(Abilities.burst)).setupTooltip(new TextComponentTranslation("aov.skill.healer.core.3.desc")));
		healer_core_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.core.4.name"), Abilities.posEnergyAura.getIcon(), 12, 0, 1, 1, 10, 0, 0, false, healer_core_3, Lists.newArrayList(Abilities.posEnergyAura)).setupTooltip(new TextComponentTranslation("aov.skill.healer.core.4.desc")));
		healer_capstone = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.core.5.name"), SkillIcons.charges, 15, 0, 3, 1, 50, 0, 0, false, healer_core_4, Lists.newArrayList()).setupTooltip(null));

		healer_tier_1_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier1.1.name"), Abilities.cureModWounds.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, healer_core_1, Lists.newArrayList(Abilities.cureModWounds)).setupTooltip(new TextComponentTranslation("aov.skill.healer.tier1.1.desc")));
		healer_tier_1_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier1.2.name"), Abilities.curePoison.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, healer_core_1, Lists.newArrayList(Abilities.curePoison)).setupTooltip(new TextComponentTranslation("aov.skill.healer.tier1.2.desc")));
		healer_tier_1_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier1.3.name"), SkillIcons.spellpower, 0, 1, 0, 1, 10, 0, 0, false, healer_core_1, Lists.newArrayList()).setupTooltip(null));

		healer_tier_2_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier2.1.name"), Abilities.cureSeriousWounds.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, healer_tier_1_1, Lists.newArrayList(Abilities.cureSeriousWounds)).setupTooltip(new TextComponentTranslation("aov.skill.healer.tier2.1.desc")));
		healer_tier_2_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier2.2.name"), Abilities.cureWither.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, healer_tier_1_2, Lists.newArrayList(Abilities.cureWither)).setupTooltip(new TextComponentTranslation("aov.skill.healer.tier2.2.desc")));
		healer_tier_2_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier2.3.name"), SkillIcons.spellpower, 0, 4, 0, 1, 10, 0, 0, false, healer_tier_1_3, Lists.newArrayList()).setupTooltip(null));
		healer_tier_2_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier2.4.name"), Abilities.cureBlind.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, healer_core_1, Lists.newArrayList(Abilities.cureBlind)).setupTooltip(new TextComponentTranslation("aov.skill.healer.tier2.4.desc")));

		healer_tier_3_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier3.1.name"), Abilities.cureCriticalWounds.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, healer_tier_2_1, Lists.newArrayList(Abilities.cureCriticalWounds)).setupTooltip(new TextComponentTranslation("aov.skill.healer.tier3.1.desc")));
		healer_tier_3_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier3.2.name"), SkillIcons.charges, 0, 8, 1, 1, 0, 0, 0, false, healer_core_1, Lists.newArrayList()).setupTooltip(null));
		healer_tier_3_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier3.3.name"), SkillIcons.spellpower, 0, 8, 0, 1, 10, 0, 0, false, healer_tier_2_3, Lists.newArrayList()).setupTooltip(null));
		healer_tier_3_5 = register(new AoVSkill(new TextComponentTranslation("aov.skill.global.invoke.name"), Abilities.invokeMass.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, healer_core_1, Lists.newArrayList(Abilities.invokeMass)).setupTooltip(new TextComponentTranslation("aov.skill.global.invoke.desc")));

		healer_tier_4_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier4.1.name"), Abilities.heal.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, healer_tier_3_1, Lists.newArrayList(Abilities.heal)).setupTooltip(new TextComponentTranslation("aov.skill.healer.tier4.1.desc")));
		healer_tier_4_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier4.2.name"), SkillIcons.charges, 0, 12, 2, 1, 0, 0, 0, false, healer_tier_3_2, Lists.newArrayList()).setupTooltip(null));
		healer_tier_4_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.healer.tier4.3.name"), SkillIcons.spellpower, 0, 12, 0, 1, 20, 0, 0, false, healer_tier_3_3, Lists.newArrayList()).setupTooltip(null));
		healer_tier_4_5 = registerAsSelectiveFocus(new AoVSkill(new TextComponentTranslation("aov.skill.global.selective.name"), SkillIcons.selectiveFocus, 0, 12, 0, 1, 0, 0, 0, false, healer_core_1, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.global.selective.desc")));

		/* Caster */
		caster_core_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.core.1.name"), Abilities.nimbusRay.getIcon(), 0, 0, 1, 1, 10, 0, 0, true, null, Lists.newArrayList(Abilities.nimbusRay)).setupTooltip(new TextComponentTranslation("aov.skill.caster.core.1.desc")));
		caster_core_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.core.2.name"), SkillIcons.spellpower, 3, 0, 0, 1, 15, 0, 0, false, caster_core_1, Lists.newArrayList()).setupTooltip(null));
		caster_core_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.core.3.name"), Abilities.flameStrike.getIcon(), 6, 0, 1, 1, 15, 0, 0, false, caster_core_2, Lists.newArrayList(Abilities.flameStrike)).setupTooltip(new TextComponentTranslation("aov.skill.caster.core.3.desc")));
		caster_core_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.core.4.name"), Abilities.bladeBarrier.getIcon(), 12, 0, 1, 1, 15, 0, 0, false, caster_core_3, Lists.newArrayList(Abilities.bladeBarrier)).setupTooltip(new TextComponentTranslation("aov.skill.caster.core.4.desc")));
		caster_capstone = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.core.5.name"), SkillIcons.charges, 15, 0, 4, 1, 50, 0, 0, false, caster_core_4, Lists.newArrayList()).setupTooltip(null));

		caster_tier_1_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier1.1.name"), Abilities.searingLight.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, caster_core_1, Lists.newArrayList(Abilities.searingLight)).setupTooltip(new TextComponentTranslation("aov.skill.caster.tier1.1.desc")));
		caster_tier_1_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier1.3.name"), SkillIcons.spellpower, 0, 1, 0, 1, 15, 0, 0, false, caster_core_1, Lists.newArrayList()).setupTooltip(null));

		caster_tier_2_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier2.1.name"), Abilities.slayLiving.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, caster_tier_1_1, Lists.newArrayList(Abilities.slayLiving)).setupTooltip(new TextComponentTranslation("aov.skill.caster.tier2.1.desc")));
		caster_tier_2_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier2.2.name"), SkillIcons.charges, 0, 4, 1, 1, 0, 0, 0, false, caster_core_1, Lists.newArrayList()).setupTooltip(null));
		caster_tier_2_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier2.3.name"), SkillIcons.spellpower, 0, 4, 0, 1, 15, 0, 0, false, caster_tier_1_3, Lists.newArrayList()).setupTooltip(null));

		caster_tier_3_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier3.1.name"), Abilities.destruction.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, caster_tier_2_1, Lists.newArrayList(Abilities.destruction)).setupTooltip(new TextComponentTranslation("aov.skill.caster.tier3.1.desc")));
		caster_tier_3_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier3.2.name"), SkillIcons.charges, 0, 8, 1, 1, 0, 0, 0, false, caster_tier_2_2, Lists.newArrayList()).setupTooltip(null));
		caster_tier_3_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier3.3.name"), SkillIcons.spellpower, 0, 8, 0, 1, 15, 0, 0, false, caster_tier_2_3, Lists.newArrayList()).setupTooltip(null));

		caster_tier_4_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier4.1.name"), Abilities.implosion.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, caster_tier_3_1, Lists.newArrayList(Abilities.implosion)).setupTooltip(new TextComponentTranslation("aov.skill.caster.tier4.1.desc")));
		caster_tier_4_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier4.3.name"), SkillIcons.spellpower, 0, 12, 0, 1, 25, 0, 0, false, caster_tier_3_3, Lists.newArrayList()).setupTooltip(null));
		caster_tier_4_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.caster.tier4.4.name"), Abilities.leapOfFaith.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, caster_core_1, Lists.newArrayList(Abilities.leapOfFaith)).setupTooltip(new TextComponentTranslation("aov.skill.caster.tier4.4.desc")));
		caster_tier_4_5 = registerAsSelectiveFocus(new AoVSkill(new TextComponentTranslation("aov.skill.global.selective.name"), SkillIcons.selectiveFocus, 0, 12, 0, 1, 0, 0, 0, false, caster_core_1, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.global.selective.desc")));

		/* Defender */
		defender_core_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.core.1.name"), SkillIcons.dodge, 0, 0, 1, 1, 0, 5, 0, true, null, Lists.newArrayList()).setupTooltip(null));
		defender_core_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.core.2.name"), SkillIcons.doublestrike, 3, 0, 0, 1, 0, 0, 5, false, defender_core_1, Lists.newArrayList()).setupTooltip(null));
		defender_core_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.core.3.name"), SkillIcons.defender, 6, 0, 0, 1, 0, 0, 0, false, defender_core_2, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.defender.core.3.desc")));
		defender_core_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.core.4.name"), SkillIcons.defender, 12, 0, 0, 1, 0, 0, 0, false, defender_core_3, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.defender.core.4.desc")));
		defender_capstone = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.core.5.name"), SkillIcons.defender, 15, 0, 0, 1, 0, 0, 0, false, defender_core_4, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.defender.core.5.desc")));

		defender_tier_1_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier1.1.name"), Abilities.aid.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, defender_core_1, Lists.newArrayList(Abilities.aid)).setupTooltip(new TextComponentTranslation("aov.skill.defender.tier1.1.desc")));
		defender_tier_1_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier1.3.name"), SkillIcons.dodge, 0, 1, 0, 1, 0, 5, 0, false, defender_core_1, Lists.newArrayList()).setupTooltip(null));

		defender_tier_2_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier2.1.name"), Abilities.shieldOfFaith.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, defender_tier_1_1, Lists.newArrayList(Abilities.shieldOfFaith)).setupTooltip(new TextComponentTranslation("aov.skill.defender.tier2.1.desc")));
		defender_tier_2_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier2.3.name"), SkillIcons.dodge, 0, 4, 0, 1, 0, 5, 0, false, defender_tier_1_3, Lists.newArrayList()).setupTooltip(null));
		defender_tier_2_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier2.4.name"), SkillIcons.doublestrike, 0, 4, 0, 1, 0, 0, 5, false, defender_core_1, Lists.newArrayList()).setupTooltip(null));

		defender_tier_3_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier3.1.name"), Abilities.zeal.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, defender_tier_2_1, Lists.newArrayList(Abilities.zeal)).setupTooltip(new TextComponentTranslation("aov.skill.defender.tier3.1.desc")));
		defender_tier_3_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier3.3.name"), SkillIcons.dodge, 0, 8, 0, 1, 0, 5, 0, false, defender_tier_2_3, Lists.newArrayList()).setupTooltip(null));
		defender_tier_3_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier3.4.name"), SkillIcons.doublestrike, 0, 8, 0, 1, 0, 0, 5, false, defender_tier_2_4, Lists.newArrayList()).setupTooltip(null));
		defender_tier_3_5 = register(new AoVSkill(new TextComponentTranslation("aov.skill.global.invoke.name"), Abilities.invokeMass.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, defender_core_1, Lists.newArrayList(Abilities.invokeMass)).setupTooltip(new TextComponentTranslation("aov.skill.global.invoke.desc")));

		defender_tier_4_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier4.1.name"), Abilities.stalwartPact.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, defender_tier_3_1, Lists.newArrayList(Abilities.stalwartPact)).setupTooltip(new TextComponentTranslation("aov.skill.defender.tier4.1.desc")));
		defender_tier_4_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier4.2.name"), SkillIcons.vitality, 0, 12, 0, 1, 0, 0, 0, false, defender_core_1, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.defender.tier4.2.desc")));
		defender_tier_4_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.defender.tier4.3.name"), SkillIcons.dodge, 0, 12, 0, 1, 0, 5, 0, false, defender_tier_3_3, Lists.newArrayList()).setupTooltip(null));
		defender_tier_4_5 = registerAsSelectiveFocus(new AoVSkill(new TextComponentTranslation("aov.skill.global.selective.name"), SkillIcons.selectiveFocus, 0, 12, 0, 1, 0, 0, 0, false, defender_core_1, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.global.selective.desc")));

		/* Astro */
		astro_core_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.core.1.name"), Abilities.draw.getIcon(), 0, 0, 0, 1, 0, 0, 0, true, null, Lists.newArrayList(Abilities.draw)).setupTooltip(new TextComponentTranslation("aov.skill.astro.core.1.desc")));
		astro_core_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.core.2.name"), Abilities.royalroad.getIcon(), 3, 0, 0, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList(Abilities.royalroad)).setupTooltip(new TextComponentTranslation("aov.skill.astro.core.2.desc")));
		astro_core_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.core.3.name"), Abilities.spread.getIcon(), 6, 0, 0, 1, 0, 0, 0, false, astro_core_2, Lists.newArrayList(Abilities.spread)).setupTooltip(new TextComponentTranslation("aov.skill.astro.core.3.desc")));
		astro_core_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.core.4.name"), Abilities.redraw.getIcon(), 12, 0, 0, 1, 0, 0, 0, false, astro_core_3, Lists.newArrayList(Abilities.redraw)).setupTooltip(new TextComponentTranslation("aov.skill.astro.core.4.desc")));
		astro_capstone = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.core.5.name"), Abilities.timedilation.getIcon(), 15, 0, 0, 1, 0, 0, 0, false, astro_core_4, Lists.newArrayList(Abilities.timedilation)).setupTooltip(new TextComponentTranslation("aov.skill.astro.core.5.desc")));

		astro_tier_1_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier1.1.name"), Abilities.malefic.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList(Abilities.malefic)).setupTooltip(new TextComponentTranslation("aov.skill.astro.tier1.1.desc")));
		astro_tier_1_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier1.3.name"), SkillIcons.spellpower, 0, 1, 0, 1, 10, 0, 0, false, astro_core_1, Lists.newArrayList()).setupTooltip(null));
		astro_tier_1_5 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier1.5.name"), Abilities.benefic.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList(Abilities.benefic)).setupTooltip(new TextComponentTranslation("aov.skill.astro.tier1.5.desc")));

		astro_tier_2_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier2.1.name"), Abilities.combust.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, astro_tier_1_1, Lists.newArrayList(Abilities.combust)).setupTooltip(new TextComponentTranslation("aov.skill.astro.tier2.1.desc")));
		astro_tier_2_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier2.3.name"), SkillIcons.spellpower, 0, 4, 0, 1, 10, 0, 0, false, astro_tier_1_3, Lists.newArrayList()).setupTooltip(null));
		astro_tier_2_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier2.4.name"), Abilities.essentialdignity.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList(Abilities.essentialdignity)).setupTooltip(new TextComponentTranslation("aov.skill.astro.tier2.4.desc")));
		astro_tier_2_5 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier2.5.name"), Abilities.helios.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, astro_tier_1_5, Lists.newArrayList(Abilities.helios)).setupTooltip(new TextComponentTranslation("aov.skill.astro.tier2.5.desc")));

		astro_tier_3_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier3.1.name"), Abilities.gravity.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, astro_tier_2_1, Lists.newArrayList(Abilities.gravity)).setupTooltip(new TextComponentTranslation("aov.skill.astro.tier3.1.desc")));
		astro_tier_3_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier3.2.name"), SkillIcons.charges, 0, 8, 1, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList()).setupTooltip(null));
		astro_tier_3_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier3.3.name"), SkillIcons.spellpower, 0, 8, 0, 1, 10, 0, 0, false, astro_tier_2_3, Lists.newArrayList()).setupTooltip(null));
		astro_tier_3_5 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier3.5.name"), Abilities.aspectedbenefic.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, astro_tier_2_5, Lists.newArrayList(Abilities.aspectedbenefic)).setupTooltip(new TextComponentTranslation("aov.skill.astro.tier3.5.desc")));

		astro_tier_4_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier4.1.name"), Abilities.celestialopposition.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, astro_tier_3_1, Lists.newArrayList(Abilities.celestialopposition)).setupTooltip(new TextComponentTranslation("aov.skill.astro.tier4.1.desc")));
		astro_tier_4_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier4.2.name"), SkillIcons.charges, 0, 12, 2, 1, 0, 0, 0, false, astro_tier_3_2, Lists.newArrayList()).setupTooltip(null));
		astro_tier_4_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier4.3.name"), SkillIcons.spellpower, 0, 12, 0, 1, 20, 0, 0, false, astro_tier_3_3, Lists.newArrayList()).setupTooltip(null));
		astro_tier_4_4 = registerAsSelectiveFocus(new AoVSkill(new TextComponentTranslation("aov.skill.global.selective.name"), SkillIcons.selectiveFocus, 0, 12, 0, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.global.selective.desc")));
		astro_tier_4_5 = register(new AoVSkill(new TextComponentTranslation("aov.skill.astro.tier4.5.name"), Abilities.aspectedhelios.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, astro_tier_3_5, Lists.newArrayList(Abilities.aspectedhelios)).setupTooltip(new TextComponentTranslation("aov.skill.astro.tier4.5.desc")));

		/* Druid */
		druid_core_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.core.1.name"), SkillIcons.vitality, 0, 0, 0, 1, 0, 0, 0, true, null, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.druid.core.1.desc")));
		druid_core_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.core.2.name"), Abilities.wildshapeWolf.getIcon(), 3, 0, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList(Abilities.wildshapeWolf)).setupTooltip(new TextComponentTranslation("aov.skill.druid.core.2.desc")));
		druid_core_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.core.3.name"), SkillIcons.vitality, 6, 0, 0, 1, 0, 0, 0, false, druid_core_2, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.druid.core.3.desc")));
		druid_core_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.core.4.name"), SkillIcons.vitality, 12, 0, 0, 1, 0, 0, 0, false, druid_core_3, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.druid.core.4.desc")));
		druid_capstone = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.core.5.name"), SkillIcons.vitality, 15, 0, 0, 1, 0, 0, 0, false, druid_core_4, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.druid.core.5.desc")));

		druid_tier_1_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier1.1.name"), Abilities.cureLightWounds.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList(Abilities.cureLightWounds)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier1.1.desc")));
		druid_tier_1_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier1.2.name"), Abilities.furiousClaw.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList(Abilities.furiousClaw)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier1.2.desc")));
		druid_tier_1_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier1.3.name"), SkillIcons.dodge, 0, 1, 0, 1, 0, 1, 0, false, druid_core_1, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier1.3.desc")));
		druid_tier_1_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier1.4.name"), Abilities.druidicRegenerate.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList(Abilities.druidicRegenerate)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier1.4.desc")));
		druid_tier_1_5 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier1.5.name"), Abilities.litStrike.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList(Abilities.litStrike)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier1.5.desc")));

		druid_tier_2_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier2.2.name"), Abilities.furiousFang.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, druid_tier_1_2, Lists.newArrayList(Abilities.furiousFang)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier2.2.desc")));
		druid_tier_2_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier2.3.name"), SkillIcons.dodge, 0, 4, 0, 1, 0, 1, 0, false, druid_tier_1_3, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier2.3.desc")));
		druid_tier_2_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier2.4.name"), Abilities.naturesBounty.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, druid_tier_1_4, Lists.newArrayList(Abilities.naturesBounty)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier2.4.desc")));
		druid_tier_2_5 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier2.5.name"), Abilities.earthquake.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, druid_tier_1_5, Lists.newArrayList(Abilities.earthquake)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier2.5.desc")));

		druid_tier_3_1 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier3.1.name"), Abilities.cureModWounds.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, druid_tier_1_1, Lists.newArrayList(Abilities.cureModWounds)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier3.1.desc")));
		druid_tier_3_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier3.2.name"), Abilities.furiousHowl.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, druid_tier_2_2, Lists.newArrayList(Abilities.furiousHowl)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier3.2.desc")));
		druid_tier_3_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier3.3.name"), SkillIcons.dodge, 0, 8, 0, 1, 0, 1, 0, false, druid_tier_2_3, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier3.3.desc")));
		druid_tier_3_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier3.4.name"), Abilities.wildshapeWaterElemental.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, druid_tier_2_4, Lists.newArrayList(Abilities.wildshapeWaterElemental)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier3.4.desc")));
		druid_tier_3_5 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier3.5.name"), Abilities.lightningStorm.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, druid_tier_2_5, Lists.newArrayList(Abilities.lightningStorm)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier3.5.desc")));

		druid_tier_4_1 = registerAsSelectiveFocus(new AoVSkill(new TextComponentTranslation("aov.skill.global.selective.name"), SkillIcons.selectiveFocus, 0, 12, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.global.selective.desc")));
		druid_tier_4_2 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier4.2.name"), Abilities.formPack.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, druid_tier_3_2, Lists.newArrayList(Abilities.formPack)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier4.2.desc")));
		druid_tier_4_3 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier4.3.name"), SkillIcons.dodge, 0, 12, 0, 1, 0, 1, 0, false, druid_tier_3_3, Lists.newArrayList()).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier4.3.desc")));
		druid_tier_4_4 = register(new AoVSkill(new TextComponentTranslation("aov.skill.druid.tier4.4.name"), Abilities.wildshapeFireElemental.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, druid_tier_3_4, Lists.newArrayList(Abilities.wildshapeFireElemental)).setupTooltip(new TextComponentTranslation("aov.skill.druid.tier4.4.desc")));
		//druid_tier_4_5
	}

	public static AoVSkill register(AoVSkill skill) {
		REGISTRY.add(skill);
		return skill;
	}

	public static int getID(AoVSkill skill) {
		return REGISTRY.contains(skill) ? REGISTRY.indexOf(skill) : -1;
	}

	public static AoVSkill getSkillFromID(int id) {
		return id >= 0 && id < REGISTRY.size() ? REGISTRY.get(id) : null;
	}

	public static AoVSkill registerAsSelectiveFocus(AoVSkill skill) {
		SELECTIVE_FOCUS_REGISTRY.add(skill);
		return register(skill);
	}

	public static boolean isSelectiveFocusSkill(AoVSkill skill) {
		return SELECTIVE_FOCUS_REGISTRY.contains(skill);
	}
}
