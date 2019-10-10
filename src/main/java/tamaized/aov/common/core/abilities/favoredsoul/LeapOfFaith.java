package tamaized.aov.common.core.abilities.favoredsoul;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.helper.MotionHelper;
import tamaized.aov.registry.AoVPotions;
import tamaized.aov.registry.SoundEvents;

import java.util.Objects;

public class LeapOfFaith extends AbilityBase {

	public LeapOfFaith() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", "aov.gui.infinite"),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.leapoffaith.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.leapoffaith.name";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.MODID, "textures/spells/leapoffaith.png");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
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
	public boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target) {
		return false;
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity caster, LivingEntity target) {
		Vec3d vec = caster.getLook(1.0F);
		double distance = 3.5;
		MotionHelper.addMotion(caster, new Vec3d(vec.x * distance, 1, vec.z * distance));
		caster.addPotionEffect(new EffectInstance(Objects.requireNonNull(AoVPotions.slowFall.get()), 300));
		ILeapCapability cap = CapabilityList.getCap(caster, CapabilityList.LEAP);
		if (cap != null)
			cap.setLeapDuration(300);
		SoundEvents.playMovingSoundOnServer(SoundEvents.boost, caster);
		return true;
	}

}
