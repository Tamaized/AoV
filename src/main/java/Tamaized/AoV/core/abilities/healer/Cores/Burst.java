package Tamaized.AoV.core.abilities.healer.Cores;

import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.helper.ParticleHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;

public class Burst extends AbilityBase {

	private final static int charges = 6;
	private final static int range = 20;
	private final static int dmg = 4;

	public Burst() {
		super(

				TextFormatting.YELLOW + getStaticName(),

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				TextFormatting.AQUA + "Range: " + range,

				TextFormatting.AQUA + "Base Healing: " + dmg,

				"",

				TextFormatting.DARK_PURPLE + "Heals everything and cures",

				TextFormatting.DARK_PURPLE + "poison, blind, and slows.",

				TextFormatting.DARK_PURPLE + "around you, including you.",

				TextFormatting.DARK_PURPLE + "Deals damage to Undead."

		);
	}

	@Override
	public void cast(Ability ability, EntityPlayer player, EntityLivingBase e) {
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null) return;
		ParticleHelper.spawnParticleMesh(ParticleHelper.Type.BURST, player.world, player.getPositionVector(), range, 0xFFFF00FF);
		int a = (int) (dmg * (1f + (cap.getSpellPower() / 100f)));
		List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.getPosition().add(-range, -range, -range), player.getPosition().add(range, range, range)));
		for (EntityLivingBase entity : list) {
			if (entity.isEntityUndead()) entity.attackEntityFrom(DamageSource.MAGIC, a);
			else if (cap.hasSelectiveFocus() && (entity instanceof IMob)) continue;
			else entity.heal(a);
			cap.addExp(20, this);
		}
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid + ":textures/spells/posenergyburst.png");
	}

	@Override
	public String getName() {
		return getStaticName();
	}

	public static String getStaticName() {
		return "Positive Energy Burst";
	}

	@Override
	public int getCoolDown() {
		return 3;
	}

	@Override
	public int getMaxCharges() {
		return charges;
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public double getMaxDistance() {
		return range;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

}
