package Tamaized.AoV.core.abilities;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.caster.Destruction;
import Tamaized.AoV.core.abilities.caster.FlameStrike;
import Tamaized.AoV.core.abilities.caster.Implosion;
import Tamaized.AoV.core.abilities.caster.LeapOfFaith;
import Tamaized.AoV.core.abilities.caster.NimbusRay;
import Tamaized.AoV.core.abilities.caster.SearingLight;
import Tamaized.AoV.core.abilities.caster.SlayLiving;
import Tamaized.AoV.core.abilities.defender.Aid;
import Tamaized.AoV.core.abilities.defender.ShieldOfFaith;
import Tamaized.AoV.core.abilities.defender.StalwartPact;
import Tamaized.AoV.core.abilities.defender.Zeal;
import Tamaized.AoV.core.abilities.healer.Cores.Burst;
import Tamaized.AoV.core.abilities.healer.Cores.PosEnergyAura;
import Tamaized.AoV.core.abilities.healer.Cures.CureBlind;
import Tamaized.AoV.core.abilities.healer.Cures.CurePoison;
import Tamaized.AoV.core.abilities.healer.Cures.CureWither;
import Tamaized.AoV.core.abilities.healer.Healing.CureCriticalWounds;
import Tamaized.AoV.core.abilities.healer.Healing.CureLightWounds;
import Tamaized.AoV.core.abilities.healer.Healing.CureModWounds;
import Tamaized.AoV.core.abilities.healer.Healing.CureSeriousWounds;
import Tamaized.AoV.core.abilities.healer.Healing.Heal;
import Tamaized.AoV.core.abilities.universal.InvokeMass;
import Tamaized.AoV.network.ServerPacketHandler;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

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
		
		public NullAbility(String name){
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

	private final List<String> description;

	public AbilityBase(String... desc) {
		description = new ArrayList<String>();
		for (String s : desc)
			description.add(s);
		registry.add(this);
	}

	public final List<String> getDescription() {
		return description;
	}

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

	protected static void sendPacketTypeTarget(Ability ability, int entityID) {
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ServerPacketHandler.getPacketTypeID(ServerPacketHandler.PacketType.SPELLCAST_TARGET));
			outputStream.writeInt(entityID);
			ability.encode(outputStream);
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			if (AoV.channel != null && pkt != null) AoV.channel.sendToServer(pkt);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static void sendPacketTypeSelf(Ability ability) {
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ServerPacketHandler.getPacketTypeID(ServerPacketHandler.PacketType.SPELLCAST_SELF));
			ability.encode(outputStream);
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			if (AoV.channel != null && pkt != null) AoV.channel.sendToServer(pkt);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
