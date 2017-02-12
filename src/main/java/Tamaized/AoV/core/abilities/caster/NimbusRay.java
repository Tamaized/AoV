package Tamaized.AoV.core.abilities.caster;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class NimbusRay extends AbilityBase {

	private static final int damage = 4;
	private static final int charges = 6;
	private static final int distance = 20;

	public NimbusRay() {
		super(

				TextFormatting.YELLOW + getStaticName(),

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				TextFormatting.AQUA + "Range: " + distance,

				TextFormatting.AQUA + "Base Damage: " + damage,

				"",

				TextFormatting.DARK_PURPLE + "Shoots a small ray of light",

				TextFormatting.DARK_PURPLE + "to deal damage.",

				TextFormatting.DARK_PURPLE + "This damage is doubled on",

				TextFormatting.DARK_PURPLE + "Undead targets."

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
		return "Nimbus Ray";
	}

	@Override
	public int getCoolDown() {
		return 2;
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

	}

}
