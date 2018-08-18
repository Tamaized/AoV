package tamaized.aov.common.core.abilities;

import tamaized.aov.common.core.abilities.astro.AspectedBenefic;
import tamaized.aov.common.core.abilities.astro.AspectedHelios;
import tamaized.aov.common.core.abilities.astro.Benefic;
import tamaized.aov.common.core.abilities.astro.CelestialOpposition;
import tamaized.aov.common.core.abilities.astro.Combust;
import tamaized.aov.common.core.abilities.astro.Draw;
import tamaized.aov.common.core.abilities.astro.EssentialDignity;
import tamaized.aov.common.core.abilities.astro.Gravity;
import tamaized.aov.common.core.abilities.astro.Helios;
import tamaized.aov.common.core.abilities.astro.Malefic;
import tamaized.aov.common.core.abilities.astro.Redraw;
import tamaized.aov.common.core.abilities.astro.RoyalRoad;
import tamaized.aov.common.core.abilities.astro.Spread;
import tamaized.aov.common.core.abilities.astro.TimeDilation;
import tamaized.aov.common.core.abilities.caster.BladeBarrier;
import tamaized.aov.common.core.abilities.caster.Destruction;
import tamaized.aov.common.core.abilities.caster.FlameStrike;
import tamaized.aov.common.core.abilities.caster.Implosion;
import tamaized.aov.common.core.abilities.caster.LeapOfFaith;
import tamaized.aov.common.core.abilities.caster.NimbusRay;
import tamaized.aov.common.core.abilities.caster.SearingLight;
import tamaized.aov.common.core.abilities.caster.SlayLiving;
import tamaized.aov.common.core.abilities.defender.Aid;
import tamaized.aov.common.core.abilities.defender.ShieldOfFaith;
import tamaized.aov.common.core.abilities.defender.StalwartPact;
import tamaized.aov.common.core.abilities.defender.Zeal;
import tamaized.aov.common.core.abilities.healer.Cores.Burst;
import tamaized.aov.common.core.abilities.healer.Cores.PosEnergyAura;
import tamaized.aov.common.core.abilities.healer.Cures.CureBlind;
import tamaized.aov.common.core.abilities.healer.Cures.CurePoison;
import tamaized.aov.common.core.abilities.healer.Cures.CureWither;
import tamaized.aov.common.core.abilities.healer.Healing.CureCriticalWounds;
import tamaized.aov.common.core.abilities.healer.Healing.CureLightWounds;
import tamaized.aov.common.core.abilities.healer.Healing.CureModWounds;
import tamaized.aov.common.core.abilities.healer.Healing.CureSeriousWounds;
import tamaized.aov.common.core.abilities.healer.Healing.Heal;
import tamaized.aov.common.core.abilities.universal.InvokeMass;

public class Abilities {

	// Universal
	public static AbilityBase invokeMass;

	// Healer
	public static AbilityBase cureLightWounds;
	public static AbilityBase cureModWounds;
	public static AbilityBase cureSeriousWounds;
	public static AbilityBase cureCriticalWounds;
	public static AbilityBase heal;
	public static AbilityBase burst;
	public static AbilityBase posEnergyAura;
	public static AbilityBase curePoison;
	public static AbilityBase cureWither;
	public static AbilityBase cureBlind;

	// Caster
	public static AbilityBase nimbusRay;
	public static AbilityBase searingLight;
	public static AbilityBase flameStrike;
	public static AbilityBase leapOfFaith;
	public static AbilityBase slayLiving;
	public static AbilityBase destruction;
	public static AbilityBase implosion;
	public static AbilityBase bladeBarrier;

	// Defender
	public static AbilityBase aid;
	public static AbilityBase shieldOfFaith;
	public static AbilityBase zeal;
	public static AbilityBase stalwartPact;
	public static AbilityBase defenderBlocking;
	public static AbilityBase defenderDodge;
	public static AbilityBase defenderDoublestrike;

	// Astro
	public static AbilityBase draw;
	public static AbilityBase royalroad;
	public static AbilityBase spread;
	public static AbilityBase redraw;
	public static AbilityBase timedilation;
	public static AbilityBase benefic;
	public static AbilityBase helios;
	public static AbilityBase essentialdignity;
	public static AbilityBase aspectedbenefic;
	public static AbilityBase aspectedhelios;
	public static AbilityBase malefic;
	public static AbilityBase combust;
	public static AbilityBase gravity;
	public static AbilityBase celestialopposition;

	//Druid
	public static AbilityBase wildshapeWolf;
	public static AbilityBase furiousClaw;
	public static AbilityBase druidicRegenerate;
	public static AbilityBase litStrike;
	public static AbilityBase furiousFang;
	public static AbilityBase naturesBounty;
	public static AbilityBase earthquake;
	public static AbilityBase furiousHowl;
	public static AbilityBase wildshapeWaterElemental;
	public static AbilityBase lightningStorm;
	public static AbilityBase formPack;
	public static AbilityBase wildshapeFireElemental;
	public static AbilityBase elementalEmpowerment;

	public static void register() {

		// Universal
		invokeMass = new InvokeMass();

		// Healer
		cureLightWounds = new CureLightWounds();
		cureModWounds = new CureModWounds();
		cureSeriousWounds = new CureSeriousWounds();
		cureCriticalWounds = new CureCriticalWounds();
		heal = new Heal();
		burst = new Burst();
		posEnergyAura = new PosEnergyAura();
		curePoison = new CurePoison();
		cureWither = new CureWither();
		cureBlind = new CureBlind();

		// Caster
		nimbusRay = new NimbusRay();
		searingLight = new SearingLight();
		flameStrike = new FlameStrike();
		leapOfFaith = new LeapOfFaith();
		slayLiving = new SlayLiving();
		destruction = new Destruction();
		implosion = new Implosion();
		bladeBarrier = new BladeBarrier();

		// Defender
		aid = new Aid();
		shieldOfFaith = new ShieldOfFaith();
		zeal = new Zeal();
		stalwartPact = new StalwartPact();
		defenderBlocking = new AbilityBase.NullAbility("DefenderBlocking");
		defenderDodge = new AbilityBase.NullAbility("DefenderDodge");
		defenderDoublestrike = new AbilityBase.NullAbility("DefenderDoublestrike");

		// Astro
		draw = new Draw();
		royalroad = new RoyalRoad();
		spread = new Spread();
		redraw = new Redraw();
		timedilation = new TimeDilation();
		benefic = new Benefic();
		helios = new Helios();
		essentialdignity = new EssentialDignity();
		aspectedbenefic = new AspectedBenefic();
		aspectedhelios = new AspectedHelios();
		malefic = new Malefic();
		combust = new Combust();
		gravity = new Gravity();
		celestialopposition = new CelestialOpposition();
	}

}
