package Tamaized.AoV.core.abilities.caster;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.TamModized.helper.MotionHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;

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
		MotionHelper.addMotion(caster, vec.xCoord * distance, 1, vec.zCoord * distance);
		caster.addPotionEffect(new PotionEffect(AoV.potions.slowFall, 20 * 15));
	}

}
