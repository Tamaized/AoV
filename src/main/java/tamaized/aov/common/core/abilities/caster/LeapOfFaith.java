package tamaized.aov.common.core.abilities.caster;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.registry.AoVPotions;
import tamaized.aov.registry.SoundEvents;
import tamaized.tammodized.common.helper.MotionHelper;

public class LeapOfFaith extends AbilityBase {

	public LeapOfFaith() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", "aov.gui.infinite"),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.leapoffaith.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.leapoffaith.name";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid, "textures/spells/leapoffaith.png");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
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
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return false;
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		Vec3d vec = caster.getLook(1.0F);
		double distance = 3.5;
		MotionHelper.addMotion(caster, new Vec3d(vec.x * distance, 1, vec.z * distance));
		caster.addPotionEffect(new PotionEffect(AoVPotions.slowFall, 300));
		ILeapCapability cap = caster.hasCapability(CapabilityList.LEAP, null) ? caster.getCapability(CapabilityList.LEAP, null) : null;
		if (cap != null)
			cap.setLeapDuration(300);
		SoundEvents.playMovingSoundOnServer(SoundEvents.boost, caster);
		return true;
	}

}
