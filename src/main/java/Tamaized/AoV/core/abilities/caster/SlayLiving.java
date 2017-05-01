package Tamaized.AoV.core.abilities.caster;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.entity.projectile.caster.ProjectileNimbusRay;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class SlayLiving extends AbilityBase {

	private static final int charges = 4;
	private static final int distance = 3;

	public SlayLiving() {
		super(

				TextFormatting.YELLOW + getStaticName(),

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				TextFormatting.AQUA + "Range: " + distance,

				"",

				TextFormatting.DARK_PURPLE + "Destroys a living target.",

				TextFormatting.DARK_PURPLE + "Does not affect Undead or",

				TextFormatting.DARK_PURPLE + "bosses."

		);
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid, "textures/spells/slayliving.png");
	}

	@Override
	public String getName() {
		return getStaticName();
	}

	public static String getStaticName() {
		return "Slay Living";
	}

	@Override
	public int getCoolDown() {
		return 6;
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
		if (target == null) return;
		IAoVCapability cap = caster.getCapability(CapabilityList.AOV, null);
		if (cap != null && !target.isEntityUndead() && target.isNonBoss()) {
			target.attackEntityFrom(AoV.damageSources.destruction, Integer.MAX_VALUE);
			cap.addExp(caster, 20, AbilityBase.slayLiving);
		}
	}

}
