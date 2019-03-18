package tamaized.aov.common.core.abilities;

import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
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
import tamaized.aov.common.core.abilities.cleric.Cores.Burst;
import tamaized.aov.common.core.abilities.cleric.Cores.PosEnergyAura;
import tamaized.aov.common.core.abilities.cleric.Cures.CureBlind;
import tamaized.aov.common.core.abilities.cleric.Cures.CurePoison;
import tamaized.aov.common.core.abilities.cleric.Cures.CureWither;
import tamaized.aov.common.core.abilities.cleric.Healing.CureCriticalWounds;
import tamaized.aov.common.core.abilities.cleric.Healing.CureLightWounds;
import tamaized.aov.common.core.abilities.cleric.Healing.CureModWounds;
import tamaized.aov.common.core.abilities.cleric.Healing.CureSeriousWounds;
import tamaized.aov.common.core.abilities.cleric.Healing.Heal;
import tamaized.aov.common.core.abilities.druid.DruidicRegenerate;
import tamaized.aov.common.core.abilities.druid.Earthquake;
import tamaized.aov.common.core.abilities.druid.ElementalEmpowerment;
import tamaized.aov.common.core.abilities.druid.FormPack;
import tamaized.aov.common.core.abilities.druid.FuriousClaw;
import tamaized.aov.common.core.abilities.druid.FuriousFang;
import tamaized.aov.common.core.abilities.druid.FuriousHowl;
import tamaized.aov.common.core.abilities.druid.LightningStorm;
import tamaized.aov.common.core.abilities.druid.LightningStrike;
import tamaized.aov.common.core.abilities.druid.NaturesBounty;
import tamaized.aov.common.core.abilities.druid.Polymorph;
import tamaized.aov.common.core.abilities.favoredsoul.AlignmentAoE;
import tamaized.aov.common.core.abilities.favoredsoul.BladeBarrier;
import tamaized.aov.common.core.abilities.favoredsoul.FlameStrike;
import tamaized.aov.common.core.abilities.favoredsoul.Implosion;
import tamaized.aov.common.core.abilities.favoredsoul.LeapOfFaith;
import tamaized.aov.common.core.abilities.favoredsoul.NimbusRay;
import tamaized.aov.common.core.abilities.favoredsoul.SearingLight;
import tamaized.aov.common.core.abilities.paladin.Aid;
import tamaized.aov.common.core.abilities.paladin.ShieldOfFaith;
import tamaized.aov.common.core.abilities.paladin.StalwartPact;
import tamaized.aov.common.core.abilities.paladin.Zeal;
import tamaized.aov.common.core.abilities.universal.InvokeMass;

public class Abilities {

	// Universal
	public static AbilityBase invokeMass;

	// Cleric
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

	// Favored Soul
	public static AbilityBase nimbusRay;
	public static AbilityBase searingLight;
	public static AbilityBase flameStrike;
	public static AbilityBase leapOfFaith;
	public static AbilityBase ordersWrath;
	public static AbilityBase chaosHammer;
	public static AbilityBase implosion;
	public static AbilityBase bladeBarrier;
	public static AbilityBase archAngelicForm;

	// Paladin
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
	public static AbilityBase druidCentered;
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

		// Cleric
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

		// Favored Soul
		nimbusRay = new NimbusRay();
		searingLight = new SearingLight();
		flameStrike = new FlameStrike();
		leapOfFaith = new LeapOfFaith();
		ordersWrath = new AlignmentAoE(AlignmentAoE.Type.OrdersWrath);
		chaosHammer = new AlignmentAoE(AlignmentAoE.Type.ChaosHammer);
		implosion = new Implosion();
		bladeBarrier = new BladeBarrier();
		archAngelicForm = new Polymorph("archangelic", IPolymorphCapability.Morph.ArchAngel);

		// Paladin
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

		druidCentered = new AbilityBase.NullAbility("DruidCentered");
		wildshapeWolf = new Polymorph("wolf", IPolymorphCapability.Morph.Wolf);
		furiousClaw = new FuriousClaw();
		druidicRegenerate = new DruidicRegenerate();
		litStrike = new LightningStrike();
		furiousFang = new FuriousFang();
		naturesBounty = new NaturesBounty();
		earthquake = new Earthquake();
		furiousHowl = new FuriousHowl();
		wildshapeWaterElemental = new Polymorph("water", IPolymorphCapability.Morph.WaterElemental);
		lightningStorm = new LightningStorm();
		formPack = new FormPack();
		wildshapeFireElemental = new Polymorph("fire", IPolymorphCapability.Morph.FireElemental);
		elementalEmpowerment = new ElementalEmpowerment();

	}

}
