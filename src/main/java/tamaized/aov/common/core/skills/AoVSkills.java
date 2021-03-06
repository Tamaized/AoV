package tamaized.aov.common.core.skills;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.util.text.TranslationTextComponent;
import tamaized.aov.common.core.abilities.Abilities;

import java.util.List;
import java.util.Set;

public class AoVSkills {

	private static final List<AoVSkill> REGISTRY = Lists.newArrayList();
	private static final Set<AoVSkill> SELECTIVE_FOCUS_REGISTRY = Sets.newHashSet();

	/* Cleric */
	public static AoVSkill cleric_core_1;
	public static AoVSkill cleric_core_2;
	public static AoVSkill cleric_core_3;
	public static AoVSkill cleric_core_4;
	public static AoVSkill cleric_capstone;

	public static AoVSkill cleric_tier_1_1;
	public static AoVSkill cleric_tier_1_2;
	public static AoVSkill cleric_tier_1_3;

	public static AoVSkill cleric_tier_2_1;
	public static AoVSkill cleric_tier_2_2;
	public static AoVSkill cleric_tier_2_3;
	public static AoVSkill cleric_tier_2_4;

	public static AoVSkill cleric_tier_3_1;
	public static AoVSkill cleric_tier_3_2;
	public static AoVSkill cleric_tier_3_3;
	public static AoVSkill cleric_tier_3_5;

	public static AoVSkill cleric_tier_4_1;
	public static AoVSkill cleric_tier_4_2;
	public static AoVSkill cleric_tier_4_3;
	public static AoVSkill cleric_tier_4_5;

	/* Favored Soul */
	public static AoVSkill favoredsoul_core_1;
	public static AoVSkill favoredsoul_core_2;
	public static AoVSkill favoredsoul_core_3;
	public static AoVSkill favoredsoul_core_4;
	public static AoVSkill favoredsoul_capstone;

	public static AoVSkill favoredsoul_tier_1_1;
	public static AoVSkill favoredsoul_tier_1_3;
	public static AoVSkill favoredsoul_tier_1_5;

	public static AoVSkill favoredsoul_tier_2_1;
	public static AoVSkill favoredsoul_tier_2_2;
	public static AoVSkill favoredsoul_tier_2_3;

	public static AoVSkill favoredsoul_tier_3_1;
	public static AoVSkill favoredsoul_tier_3_2;
	public static AoVSkill favoredsoul_tier_3_3;
	public static AoVSkill favoredsoul_tier_3_5;

	public static AoVSkill favoredsoul_tier_4_1;
	public static AoVSkill favoredsoul_tier_4_3;
	public static AoVSkill favoredsoul_tier_4_4;
	public static AoVSkill favoredsoul_tier_4_5;

	/* Paladin */
	public static AoVSkill paladin_core_1;
	public static AoVSkill paladin_core_2;
	public static AoVSkill paladin_core_3;
	public static AoVSkill paladin_core_4;
	public static AoVSkill paladin_capstone;

	public static AoVSkill paladin_tier_1_1;
	public static AoVSkill paladin_tier_1_3;

	public static AoVSkill paladin_tier_2_1;
	public static AoVSkill paladin_tier_2_3;
	public static AoVSkill paladin_tier_2_4;

	public static AoVSkill paladin_tier_3_1;
	public static AoVSkill paladin_tier_3_3;
	public static AoVSkill paladin_tier_3_4;
	public static AoVSkill paladin_tier_3_5;

	public static AoVSkill paladin_tier_4_1;
	public static AoVSkill paladin_tier_4_2;
	public static AoVSkill paladin_tier_4_3;
	public static AoVSkill paladin_tier_4_5;

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

		/* Cleric */
		cleric_core_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.core.1.name"), Abilities.cureLightWounds.getIcon(), 0, 0, 1, 1, 0, 0, 0, true, null, Lists.newArrayList(Abilities.cureLightWounds)).setupTooltip(new TranslationTextComponent("aov.skill.cleric.core.1.desc")));
		cleric_core_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.core.2.name"), SkillIcons.SPELLPOWER, 3, 0, 0, 1, 10, 0, 0, false, cleric_core_1, Lists.newArrayList()).setupTooltip(null));
		cleric_core_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.core.3.name"), Abilities.burst.getIcon(), 6, 0, 1, 1, 10, 0, 0, false, cleric_core_2, Lists.newArrayList(Abilities.burst)).setupTooltip(new TranslationTextComponent("aov.skill.cleric.core.3.desc")));
		cleric_core_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.core.4.name"), Abilities.posEnergyAura.getIcon(), 12, 0, 1, 1, 10, 0, 0, false, cleric_core_3, Lists.newArrayList(Abilities.posEnergyAura)).setupTooltip(new TranslationTextComponent("aov.skill.cleric.core.4.desc")));
		cleric_capstone = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.core.5.name"), SkillIcons.CHARGES, 15, 0, 3, 1, 50, 0, 0, false, cleric_core_4, Lists.newArrayList()).setupTooltip(null));

		cleric_tier_1_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier1.1.name"), Abilities.cureModWounds.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, cleric_core_1, Lists.newArrayList(Abilities.cureModWounds)).setupTooltip(new TranslationTextComponent("aov.skill.cleric.tier1.1.desc")));
		cleric_tier_1_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier1.2.name"), Abilities.curePoison.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, cleric_core_1, Lists.newArrayList(Abilities.curePoison)).setupTooltip(new TranslationTextComponent("aov.skill.cleric.tier1.2.desc")));
		cleric_tier_1_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier1.3.name"), SkillIcons.SPELLPOWER, 0, 1, 0, 1, 10, 0, 0, false, cleric_core_1, Lists.newArrayList()).setupTooltip(null));

		cleric_tier_2_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier2.1.name"), Abilities.cureSeriousWounds.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, cleric_tier_1_1, Lists.newArrayList(Abilities.cureSeriousWounds)).setupTooltip(new TranslationTextComponent("aov.skill.cleric.tier2.1.desc")));
		cleric_tier_2_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier2.2.name"), Abilities.cureWither.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, cleric_tier_1_2, Lists.newArrayList(Abilities.cureWither)).setupTooltip(new TranslationTextComponent("aov.skill.cleric.tier2.2.desc")));
		cleric_tier_2_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier2.3.name"), SkillIcons.SPELLPOWER, 0, 4, 0, 1, 10, 0, 0, false, cleric_tier_1_3, Lists.newArrayList()).setupTooltip(null));
		cleric_tier_2_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier2.4.name"), Abilities.cureBlind.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, cleric_core_1, Lists.newArrayList(Abilities.cureBlind)).setupTooltip(new TranslationTextComponent("aov.skill.cleric.tier2.4.desc")));

		cleric_tier_3_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier3.1.name"), Abilities.cureCriticalWounds.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, cleric_tier_2_1, Lists.newArrayList(Abilities.cureCriticalWounds)).setupTooltip(new TranslationTextComponent("aov.skill.cleric.tier3.1.desc")));
		cleric_tier_3_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier3.2.name"), SkillIcons.CHARGES, 0, 8, 1, 1, 0, 0, 0, false, cleric_core_1, Lists.newArrayList()).setupTooltip(null));
		cleric_tier_3_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier3.3.name"), SkillIcons.SPELLPOWER, 0, 8, 0, 1, 10, 0, 0, false, cleric_tier_2_3, Lists.newArrayList()).setupTooltip(null));
		cleric_tier_3_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.global.invoke.name"), Abilities.invokeMass.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, cleric_core_1, Lists.newArrayList(Abilities.invokeMass)).setupTooltip(new TranslationTextComponent("aov.skill.global.invoke.desc")));

		cleric_tier_4_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier4.1.name"), Abilities.heal.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, cleric_tier_3_1, Lists.newArrayList(Abilities.heal)).setupTooltip(new TranslationTextComponent("aov.skill.cleric.tier4.1.desc")));
		cleric_tier_4_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier4.2.name"), SkillIcons.CHARGES, 0, 12, 2, 1, 0, 0, 0, false, cleric_tier_3_2, Lists.newArrayList()).setupTooltip(null));
		cleric_tier_4_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.cleric.tier4.3.name"), SkillIcons.SPELLPOWER, 0, 12, 0, 1, 20, 0, 0, false, cleric_tier_3_3, Lists.newArrayList()).setupTooltip(null));
		cleric_tier_4_5 = registerAsSelectiveFocus(new AoVSkill(new TranslationTextComponent("aov.skill.global.selective.name"), SkillIcons.SELECTIVEFOCUS, 0, 12, 0, 1, 0, 0, 0, false, cleric_core_1, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.global.selective.desc")));

		/* Favored Soul */
		favoredsoul_core_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.core.1.name"), Abilities.nimbusRay.getIcon(), 0, 0, 1, 1, 10, 0, 0, true, null, Lists.newArrayList(Abilities.nimbusRay)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.core.1.desc")));
		favoredsoul_core_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.core.2.name"), SkillIcons.SPELLPOWER, 3, 0, 0, 1, 15, 0, 0, false, favoredsoul_core_1, Lists.newArrayList()).setupTooltip(null));
		favoredsoul_core_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.core.3.name"), Abilities.flameStrike.getIcon(), 6, 0, 1, 1, 15, 0, 0, false, favoredsoul_core_2, Lists.newArrayList(Abilities.flameStrike)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.core.3.desc")));
		favoredsoul_core_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.core.4.name"), Abilities.bladeBarrier.getIcon(), 12, 0, 1, 1, 15, 0, 0, false, favoredsoul_core_3, Lists.newArrayList(Abilities.bladeBarrier)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.core.4.desc")));
		favoredsoul_capstone = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.core.5.name"), Abilities.archAngelicForm.getIcon(), 15, 0, 4, 1, 50, 0, 0, false, favoredsoul_core_4, Lists.newArrayList(Abilities.archAngelicForm)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.core.5.desc")));

		favoredsoul_tier_1_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier1.1.name"), Abilities.searingLight.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, favoredsoul_core_1, Lists.newArrayList(Abilities.searingLight)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.tier1.1.desc")));
		favoredsoul_tier_1_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier1.3.name"), SkillIcons.SPELLPOWER, 0, 1, 0, 1, 15, 0, 0, false, favoredsoul_core_1, Lists.newArrayList()).setupTooltip(null));
		favoredsoul_tier_1_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier1.5.name"), Abilities.cureLightWounds.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, favoredsoul_core_1, Lists.newArrayList(Abilities.cureLightWounds)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.tier1.5.desc")));

		favoredsoul_tier_2_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier2.1.name"), Abilities.ordersWrath.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, favoredsoul_tier_1_1, Lists.newArrayList(Abilities.ordersWrath)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.tier2.1.desc")));
		favoredsoul_tier_2_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier2.2.name"), SkillIcons.CHARGES, 0, 4, 1, 1, 0, 0, 0, false, favoredsoul_core_1, Lists.newArrayList()).setupTooltip(null));
		favoredsoul_tier_2_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier2.3.name"), SkillIcons.SPELLPOWER, 0, 4, 0, 1, 15, 0, 0, false, favoredsoul_tier_1_3, Lists.newArrayList()).setupTooltip(null));

		favoredsoul_tier_3_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier3.1.name"), Abilities.chaosHammer.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, favoredsoul_tier_2_1, Lists.newArrayList(Abilities.chaosHammer)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.tier3.1.desc")));
		favoredsoul_tier_3_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier3.2.name"), SkillIcons.CHARGES, 0, 8, 1, 1, 0, 0, 0, false, favoredsoul_tier_2_2, Lists.newArrayList()).setupTooltip(null));
		favoredsoul_tier_3_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier3.3.name"), SkillIcons.SPELLPOWER, 0, 8, 0, 1, 15, 0, 0, false, favoredsoul_tier_2_3, Lists.newArrayList()).setupTooltip(null));
		favoredsoul_tier_3_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier3.5.name"), Abilities.cureModWounds.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, favoredsoul_tier_1_5, Lists.newArrayList(Abilities.cureModWounds)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.tier3.5.desc")));

		favoredsoul_tier_4_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier4.1.name"), Abilities.implosion.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, favoredsoul_tier_3_1, Lists.newArrayList(Abilities.implosion)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.tier4.1.desc")));
		favoredsoul_tier_4_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier4.3.name"), SkillIcons.SPELLPOWER, 0, 12, 0, 1, 25, 0, 0, false, favoredsoul_tier_3_3, Lists.newArrayList()).setupTooltip(null));
		favoredsoul_tier_4_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.favoredsoul.tier4.4.name"), Abilities.leapOfFaith.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, favoredsoul_core_1, Lists.newArrayList(Abilities.leapOfFaith)).setupTooltip(new TranslationTextComponent("aov.skill.favoredsoul.tier4.4.desc")));
		favoredsoul_tier_4_5 = registerAsSelectiveFocus(new AoVSkill(new TranslationTextComponent("aov.skill.global.selective.name"), SkillIcons.SELECTIVEFOCUS, 0, 12, 0, 1, 0, 0, 0, false, favoredsoul_core_1, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.global.selective.desc")));

		/* Paladin */
		paladin_core_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.core.1.name"), SkillIcons.DODGE, 0, 0, 1, 1, 0, 5, 0, true, null, Lists.newArrayList()).setupTooltip(null));
		paladin_core_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.core.2.name"), SkillIcons.DOUBLESTRIKE, 3, 0, 0, 1, 0, 0, 5, false, paladin_core_1, Lists.newArrayList()).setupTooltip(null));
		paladin_core_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.core.3.name"), SkillIcons.DEFENDER, 6, 0, 0, 1, 0, 0, 0, false, paladin_core_2, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.paladin.core.3.desc")));
		paladin_core_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.core.4.name"), SkillIcons.DEFENDER, 12, 0, 0, 1, 0, 0, 0, false, paladin_core_3, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.paladin.core.4.desc")));
		paladin_capstone = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.core.5.name"), SkillIcons.DEFENDER, 15, 0, 0, 1, 0, 0, 0, false, paladin_core_4, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.paladin.core.5.desc")));

		paladin_tier_1_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier1.1.name"), Abilities.aid.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, paladin_core_1, Lists.newArrayList(Abilities.aid)).setupTooltip(new TranslationTextComponent("aov.skill.paladin.tier1.1.desc")));
		paladin_tier_1_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier1.3.name"), SkillIcons.DODGE, 0, 1, 0, 1, 0, 5, 0, false, paladin_core_1, Lists.newArrayList()).setupTooltip(null));

		paladin_tier_2_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier2.1.name"), Abilities.shieldOfFaith.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, paladin_tier_1_1, Lists.newArrayList(Abilities.shieldOfFaith)).setupTooltip(new TranslationTextComponent("aov.skill.paladin.tier2.1.desc")));
		paladin_tier_2_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier2.3.name"), SkillIcons.DODGE, 0, 4, 0, 1, 0, 5, 0, false, paladin_tier_1_3, Lists.newArrayList()).setupTooltip(null));
		paladin_tier_2_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier2.4.name"), SkillIcons.DOUBLESTRIKE, 0, 4, 0, 1, 0, 0, 5, false, paladin_core_1, Lists.newArrayList()).setupTooltip(null));

		paladin_tier_3_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier3.1.name"), Abilities.zeal.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, paladin_tier_2_1, Lists.newArrayList(Abilities.zeal)).setupTooltip(new TranslationTextComponent("aov.skill.paladin.tier3.1.desc")));
		paladin_tier_3_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier3.3.name"), SkillIcons.DODGE, 0, 8, 0, 1, 0, 5, 0, false, paladin_tier_2_3, Lists.newArrayList()).setupTooltip(null));
		paladin_tier_3_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier3.4.name"), SkillIcons.DOUBLESTRIKE, 0, 8, 0, 1, 0, 0, 5, false, paladin_tier_2_4, Lists.newArrayList()).setupTooltip(null));
		paladin_tier_3_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.global.invoke.name"), Abilities.invokeMass.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, paladin_core_1, Lists.newArrayList(Abilities.invokeMass)).setupTooltip(new TranslationTextComponent("aov.skill.global.invoke.desc")));

		paladin_tier_4_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier4.1.name"), Abilities.stalwartPact.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, paladin_tier_3_1, Lists.newArrayList(Abilities.stalwartPact)).setupTooltip(new TranslationTextComponent("aov.skill.paladin.tier4.1.desc")));
		paladin_tier_4_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier4.2.name"), SkillIcons.VITALITY, 0, 12, 0, 1, 0, 0, 0, false, paladin_core_1, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.paladin.tier4.2.desc")));
		paladin_tier_4_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.paladin.tier4.3.name"), SkillIcons.DODGE, 0, 12, 0, 1, 0, 5, 0, false, paladin_tier_3_3, Lists.newArrayList()).setupTooltip(null));
		paladin_tier_4_5 = registerAsSelectiveFocus(new AoVSkill(new TranslationTextComponent("aov.skill.global.selective.name"), SkillIcons.SELECTIVEFOCUS, 0, 12, 0, 1, 0, 0, 0, false, paladin_core_1, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.global.selective.desc")));

		/* Astro */
		astro_core_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.core.1.name"), Abilities.draw.getIcon(), 0, 0, 0, 1, 0, 0, 0, true, null, Lists.newArrayList(Abilities.draw)).setupTooltip(new TranslationTextComponent("aov.skill.astro.core.1.desc")));
		astro_core_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.core.2.name"), Abilities.royalroad.getIcon(), 3, 0, 0, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList(Abilities.royalroad)).setupTooltip(new TranslationTextComponent("aov.skill.astro.core.2.desc")));
		astro_core_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.core.3.name"), Abilities.spread.getIcon(), 6, 0, 0, 1, 0, 0, 0, false, astro_core_2, Lists.newArrayList(Abilities.spread)).setupTooltip(new TranslationTextComponent("aov.skill.astro.core.3.desc")));
		astro_core_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.core.4.name"), Abilities.redraw.getIcon(), 12, 0, 0, 1, 0, 0, 0, false, astro_core_3, Lists.newArrayList(Abilities.redraw)).setupTooltip(new TranslationTextComponent("aov.skill.astro.core.4.desc")));
		astro_capstone = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.core.5.name"), Abilities.timedilation.getIcon(), 15, 0, 0, 1, 0, 0, 0, false, astro_core_4, Lists.newArrayList(Abilities.timedilation)).setupTooltip(new TranslationTextComponent("aov.skill.astro.core.5.desc")));

		astro_tier_1_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier1.1.name"), Abilities.malefic.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList(Abilities.malefic)).setupTooltip(new TranslationTextComponent("aov.skill.astro.tier1.1.desc")));
		astro_tier_1_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier1.3.name"), SkillIcons.SPELLPOWER, 0, 1, 0, 1, 10, 0, 0, false, astro_core_1, Lists.newArrayList()).setupTooltip(null));
		astro_tier_1_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier1.5.name"), Abilities.benefic.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList(Abilities.benefic)).setupTooltip(new TranslationTextComponent("aov.skill.astro.tier1.5.desc")));

		astro_tier_2_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier2.1.name"), Abilities.combust.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, astro_tier_1_1, Lists.newArrayList(Abilities.combust)).setupTooltip(new TranslationTextComponent("aov.skill.astro.tier2.1.desc")));
		astro_tier_2_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier2.3.name"), SkillIcons.SPELLPOWER, 0, 4, 0, 1, 10, 0, 0, false, astro_tier_1_3, Lists.newArrayList()).setupTooltip(null));
		astro_tier_2_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier2.4.name"), Abilities.essentialdignity.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList(Abilities.essentialdignity)).setupTooltip(new TranslationTextComponent("aov.skill.astro.tier2.4.desc")));
		astro_tier_2_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier2.5.name"), Abilities.helios.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, astro_tier_1_5, Lists.newArrayList(Abilities.helios)).setupTooltip(new TranslationTextComponent("aov.skill.astro.tier2.5.desc")));

		astro_tier_3_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier3.1.name"), Abilities.gravity.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, astro_tier_2_1, Lists.newArrayList(Abilities.gravity)).setupTooltip(new TranslationTextComponent("aov.skill.astro.tier3.1.desc")));
		astro_tier_3_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier3.2.name"), SkillIcons.CHARGES, 0, 8, 1, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList()).setupTooltip(null));
		astro_tier_3_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier3.3.name"), SkillIcons.SPELLPOWER, 0, 8, 0, 1, 10, 0, 0, false, astro_tier_2_3, Lists.newArrayList()).setupTooltip(null));
		astro_tier_3_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier3.5.name"), Abilities.aspectedbenefic.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, astro_tier_2_5, Lists.newArrayList(Abilities.aspectedbenefic)).setupTooltip(new TranslationTextComponent("aov.skill.astro.tier3.5.desc")));

		astro_tier_4_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier4.1.name"), Abilities.celestialopposition.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, astro_tier_3_1, Lists.newArrayList(Abilities.celestialopposition)).setupTooltip(new TranslationTextComponent("aov.skill.astro.tier4.1.desc")));
		astro_tier_4_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier4.2.name"), SkillIcons.CHARGES, 0, 12, 2, 1, 0, 0, 0, false, astro_tier_3_2, Lists.newArrayList()).setupTooltip(null));
		astro_tier_4_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier4.3.name"), SkillIcons.SPELLPOWER, 0, 12, 0, 1, 20, 0, 0, false, astro_tier_3_3, Lists.newArrayList()).setupTooltip(null));
		astro_tier_4_4 = registerAsSelectiveFocus(new AoVSkill(new TranslationTextComponent("aov.skill.global.selective.name"), SkillIcons.SELECTIVEFOCUS, 0, 12, 0, 1, 0, 0, 0, false, astro_core_1, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.global.selective.desc")));
		astro_tier_4_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.astro.tier4.5.name"), Abilities.aspectedhelios.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, astro_tier_3_5, Lists.newArrayList(Abilities.aspectedhelios)).setupTooltip(new TranslationTextComponent("aov.skill.astro.tier4.5.desc")));

		/* Druid */
		druid_core_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.core.1.name"), SkillIcons.CENTERED, 0, 0, 0, 1, 0, 0, 0, true, null, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.druid.core.1.desc")));
		druid_core_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.core.2.name"), Abilities.wildshapeWolf.getIcon(), 3, 0, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList(Abilities.wildshapeWolf)).setupTooltip(new TranslationTextComponent("aov.skill.druid.core.2.desc")));
		druid_core_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.core.3.name"), SkillIcons.DODGE, 6, 0, 0, 1, 0, 0, 0, false, druid_core_2, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.druid.core.3.desc")));
		druid_core_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.core.4.name"), SkillIcons.CENTERED, 12, 0, 0, 1, 0, 0, 0, false, druid_core_3, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.druid.core.4.desc")));
		druid_capstone = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.core.5.name"), SkillIcons.DODGE, 15, 0, 0, 1, 0, 0, 0, false, druid_core_4, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.druid.core.5.desc")));

		druid_tier_1_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier1.1.name"), Abilities.cureLightWounds.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList(Abilities.cureLightWounds)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier1.1.desc")));
		druid_tier_1_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier1.2.name"), Abilities.furiousClaw.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList(Abilities.furiousClaw)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier1.2.desc")));
		druid_tier_1_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier1.3.name"), SkillIcons.DODGE, 0, 1, 0, 1, 0, 1, 0, false, druid_core_1, Lists.newArrayList()).setupTooltip(null));
		druid_tier_1_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier1.4.name"), Abilities.druidicRegenerate.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList(Abilities.druidicRegenerate)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier1.4.desc")));
		druid_tier_1_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier1.5.name"), Abilities.litStrike.getIcon(), 0, 1, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList(Abilities.litStrike)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier1.5.desc")));

		druid_tier_2_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier2.2.name"), Abilities.furiousFang.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, druid_tier_1_2, Lists.newArrayList(Abilities.furiousFang)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier2.2.desc")));
		druid_tier_2_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier2.3.name"), SkillIcons.DODGE, 0, 4, 0, 1, 0, 1, 0, false, druid_tier_1_3, Lists.newArrayList()).setupTooltip(null));
		druid_tier_2_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier2.4.name"), Abilities.naturesBounty.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, druid_tier_1_4, Lists.newArrayList(Abilities.naturesBounty)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier2.4.desc")));
		druid_tier_2_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier2.5.name"), Abilities.earthquake.getIcon(), 0, 4, 0, 1, 0, 0, 0, false, druid_tier_1_5, Lists.newArrayList(Abilities.earthquake)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier2.5.desc")));

		druid_tier_3_1 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier3.1.name"), Abilities.cureModWounds.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, druid_tier_1_1, Lists.newArrayList(Abilities.cureModWounds)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier3.1.desc")));
		druid_tier_3_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier3.2.name"), Abilities.furiousHowl.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, druid_tier_2_2, Lists.newArrayList(Abilities.furiousHowl)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier3.2.desc")));
		druid_tier_3_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier3.3.name"), SkillIcons.DODGE, 0, 8, 0, 1, 0, 1, 0, false, druid_tier_2_3, Lists.newArrayList()).setupTooltip(null));
		druid_tier_3_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier3.4.name"), Abilities.wildshapeWaterElemental.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, druid_tier_2_4, Lists.newArrayList(Abilities.wildshapeWaterElemental)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier3.4.desc")));
		druid_tier_3_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier3.5.name"), Abilities.lightningStorm.getIcon(), 0, 8, 0, 1, 0, 0, 0, false, druid_tier_2_5, Lists.newArrayList(Abilities.lightningStorm)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier3.5.desc")));

		druid_tier_4_1 = registerAsSelectiveFocus(new AoVSkill(new TranslationTextComponent("aov.skill.global.selective.name"), SkillIcons.SELECTIVEFOCUS, 0, 12, 0, 1, 0, 0, 0, false, druid_core_1, Lists.newArrayList()).setupTooltip(new TranslationTextComponent("aov.skill.global.selective.desc")));
		druid_tier_4_2 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier4.2.name"), Abilities.formPack.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, druid_tier_3_2, Lists.newArrayList(Abilities.formPack)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier4.2.desc")));
		druid_tier_4_3 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier4.3.name"), SkillIcons.DODGE, 0, 12, 0, 1, 0, 1, 0, false, druid_tier_3_3, Lists.newArrayList()).setupTooltip(null));
		druid_tier_4_4 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier4.4.name"), Abilities.wildshapeFireElemental.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, druid_tier_3_4, Lists.newArrayList(Abilities.wildshapeFireElemental)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier4.4.desc")));
		druid_tier_4_5 = register(new AoVSkill(new TranslationTextComponent("aov.skill.druid.tier4.5.name"), Abilities.elementalEmpowerment.getIcon(), 0, 12, 0, 1, 0, 0, 0, false, druid_tier_3_5, Lists.newArrayList(Abilities.elementalEmpowerment)).setupTooltip(new TranslationTextComponent("aov.skill.druid.tier4.5.desc")));
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
