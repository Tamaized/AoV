package Tamaized.AoV.core.abilities.caster;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.sound.SoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;

public class Destruction extends AbilityBase {

	private static final int charges = 2;
	private static final int distance = 20;

	public Destruction() {
		super(

				TextFormatting.YELLOW + getStaticName(),

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				TextFormatting.AQUA + "Range: " + distance,

				"",

				TextFormatting.DARK_PURPLE + "Destroys a target.",

				TextFormatting.DARK_PURPLE + "Works on Undead",

				TextFormatting.DARK_PURPLE + "but not bosses."

		);
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid, "textures/spells/destruction.png");
	}

	@Override
	public String getName() {
		return getStaticName();
	}

	public static String getStaticName() {
		return "Destruction";
	}

	@Override
	public int getCoolDown() {
		return 15;
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
		if (cap != null && target.isNonBoss()) {
			target.attackEntityFrom(AoV.damageSources.destruction, Integer.MAX_VALUE);
			target.world.playSound(null, target.posX, target.posY, target.posZ, SoundEvents.destruction, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			cap.addExp(caster, 20, AbilityBase.destruction);
		}
	}

}
