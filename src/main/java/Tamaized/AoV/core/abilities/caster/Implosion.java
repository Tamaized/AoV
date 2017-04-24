package Tamaized.AoV.core.abilities.caster;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.entity.EntitySpellImplosion;
import Tamaized.AoV.entity.projectile.caster.ProjectileNimbusRay;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;

public class Implosion extends AbilityBase {

	private static final int charges = 1;
	private static final int distance = 10;

	public Implosion() {
		super(

				TextFormatting.YELLOW + getStaticName(),

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				TextFormatting.AQUA + "Range: " + distance,

				"",

				TextFormatting.DARK_PURPLE + "Targets around you",

				TextFormatting.DARK_PURPLE + "begin to implode",

				TextFormatting.DARK_PURPLE + "and die after a",

				TextFormatting.DARK_PURPLE + "short time."

		);
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid + ":textures/spells/test.png");
	}

	@Override
	public String getName() {
		return getStaticName();
	}

	public static String getStaticName() {
		return "Implosion";
	}

	@Override
	public int getCoolDown() {
		return 30;
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
		return distance;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public void cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		for (EntityLivingBase entity : caster.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(caster.getPosition().add(-distance, -1, -distance), caster.getPosition().add(distance, 5, distance)))) {
			IAoVCapability cap = caster.getCapability(CapabilityList.AOV, null);
			if (entity == caster || (cap != null && cap.hasSelectiveFocus() && entity.isOnSameTeam(caster))) continue;
			caster.world.spawnEntity(new EntitySpellImplosion(caster.world, entity));
			cap.addExp(20, AbilityBase.implosion);
		}
	}

}
