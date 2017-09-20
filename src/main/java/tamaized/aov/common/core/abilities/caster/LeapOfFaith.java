package tamaized.aov.common.core.abilities.caster;

import tamaized.aov.AoV;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.registry.AoVPotions;
import tamaized.aov.registry.SoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import tamaized.tammodized.common.helper.MotionHelper;

public class LeapOfFaith extends AbilityBase {

	public LeapOfFaith() {
		super(

				TextFormatting.YELLOW + getStaticName(),

				"",

				TextFormatting.AQUA + "Charges: Infinite",

				"",

				TextFormatting.DARK_PURPLE + "You are able to leap through",

				TextFormatting.DARK_PURPLE + " the air to bring the fight",

				TextFormatting.DARK_PURPLE + " to your enemies",

				TextFormatting.DARK_PURPLE + " or traverse chasms."

		);
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid, "textures/spells/leapoffaith.png");
	}

	@Override
	public String getName() {
		return getStaticName();
	}

	public static String getStaticName() {
		return "Leap of Faith";
	}

	@Override
	public int getCoolDown() {
		return 15;
	}

	@Override
	public int getMaxCharges() {
		return -1;
	}

	@Override
	public int getChargeCost() {
		return 0;
	}

	@Override
	public double getMaxDistance() {
		return 1;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public void cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		Vec3d vec = caster.getLook(1.0F);
		double distance = 3.5;
		MotionHelper.addMotion(caster, new Vec3d(vec.x * distance, 1, vec.z * distance));
		caster.addPotionEffect(new PotionEffect(AoVPotions.slowFall, 20 * 15));
		SoundEvents.playMovingSoundOnServer(SoundEvents.boost, caster);
	}

}