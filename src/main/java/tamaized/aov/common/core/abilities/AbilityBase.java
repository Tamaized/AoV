package tamaized.aov.common.core.abilities;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.caster.*;
import tamaized.aov.common.core.abilities.defender.Aid;
import tamaized.aov.common.core.abilities.defender.ShieldOfFaith;
import tamaized.aov.common.core.abilities.defender.StalwartPact;
import tamaized.aov.common.core.abilities.defender.Zeal;
import tamaized.aov.common.core.abilities.healer.Cores.Burst;
import tamaized.aov.common.core.abilities.healer.Cores.PosEnergyAura;
import tamaized.aov.common.core.abilities.healer.Cures.CureBlind;
import tamaized.aov.common.core.abilities.healer.Cures.CurePoison;
import tamaized.aov.common.core.abilities.healer.Cures.CureWither;
import tamaized.aov.common.core.abilities.healer.Healing.*;
import tamaized.aov.common.core.abilities.universal.InvokeMass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbilityBase {

	private static final List<AbilityBase> registry = new ArrayList<AbilityBase>();

	public static final int getID(AbilityBase skill) {
		return registry.contains(skill) ? registry.indexOf(skill) : -1;
	}

	public final int getID() {
		return getID(this);
	}

	public static final AbilityBase getAbilityFromID(int id) {
		return id >= 0 && id < registry.size() ? registry.get(id) : null;
	}

	// Universal
	public static final AbilityBase invokeMass = new InvokeMass();

	// Healer
	public static final AbilityBase cureLightWounds = new CureLightWounds();
	public static final AbilityBase cureModWounds = new CureModWounds();
	public static final AbilityBase cureSeriousWounds = new CureSeriousWounds();
	public static final AbilityBase cureCriticalWounds = new CureCriticalWounds();
	public static final AbilityBase heal = new Heal();
	public static final AbilityBase burst = new Burst();
	public static final AbilityBase posEnergyAura = new PosEnergyAura();
	public static final AbilityBase curePoison = new CurePoison();
	public static final AbilityBase cureWither = new CureWither();
	public static final AbilityBase cureBlind = new CureBlind();

	// Caster
	public static final AbilityBase nimbusRay = new NimbusRay();
	public static final AbilityBase searingLight = new SearingLight();
	public static final AbilityBase flameStrike = new FlameStrike();
	public static final AbilityBase leapOfFaith = new LeapOfFaith();
	public static final AbilityBase slayLiving = new SlayLiving();
	public static final AbilityBase destruction = new Destruction();
	public static final AbilityBase implosion = new Implosion();
	public static final AbilityBase bladeBarrier = new BladeBarrier();

	// Defender
	public static final AbilityBase aid = new Aid();
	public static final AbilityBase shieldOfFaith = new ShieldOfFaith();
	public static final AbilityBase zeal = new Zeal();
	public static final AbilityBase stalwartPact = new StalwartPact();
	public static final AbilityBase defenderBlocking = new NullAbility("DefenderBlocking");
	public static final AbilityBase defenderDodge = new NullAbility("DefenderDodge");
	public static final AbilityBase defenderDoublestrike = new NullAbility("DefenderDoublestrike");

	private static final class NullAbility extends AbilityBase {

		private final String name;

		public NullAbility(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getMaxCharges() {
			return 0;
		}

		@Override
		public int getChargeCost() {
			return 0;
		}

		@Override
		public double getMaxDistance() {
			return 0;
		}

		@Override
		public int getCoolDown() {
			return 0;
		}

		@Override
		public boolean usesInvoke() {
			return false;
		}

		@Override
		public void cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {

		}

		@Override
		public ResourceLocation getIcon() {
			return null;
		}

	}

	private final List<TextComponentTranslation> description;

	public AbilityBase(TextComponentTranslation... desc) {
		description = Lists.newArrayList();
		description.addAll(Arrays.asList(desc));
		registry.add(this);
	}

	@SideOnly(Side.CLIENT)
	public final List<String> getDescription() {
		List<String> list = Lists.newArrayList();
		for (TextComponentTranslation s : description) {
			list.add(I18n.format(s.getKey(), s.getFormatArgs()));
		}
		return list;
	}

	@SideOnly(Side.CLIENT)
	public abstract String getName();

	public abstract int getMaxCharges();

	public abstract int getChargeCost();

	public abstract double getMaxDistance();

	public abstract int getCoolDown();

	public abstract boolean usesInvoke();

	public int getCost(IAoVCapability cap) {
		return (usesInvoke() && cap.getInvokeMass()) ? (getChargeCost() * 2) : getChargeCost();
	}

	public abstract void cast(Ability ability, EntityPlayer caster, EntityLivingBase target);

	public abstract ResourceLocation getIcon();

}
