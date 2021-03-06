package tamaized.aov.common.core.abilities.druid;

import com.google.common.collect.Sets;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.helper.RayTraceHelper;

import javax.annotation.Nullable;

public class FuriousClaw extends AbilityBase {

	public static final byte BIT = 0b1000;
	private static final int CHARGES = 10;
	public static final float DAMAGE = 2F;

	private static final String UNLOC = "aov.spells.furiousclaw";

	private static final ResourceLocation ICON = new ResourceLocation(AoV.MODID, "textures/spells/furiousclaw.png");

	public FuriousClaw() {
		super(

				new TranslationTextComponent(UNLOC.concat(".name")),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", CHARGES),

				new TranslationTextComponent("aov.spells.global.damage", DAMAGE),

				new TranslationTextComponent(""),

				new TranslationTextComponent(UNLOC.concat(".desc"))

		);
	}

	public static boolean invoke(byte bit, PlayerEntity caster, AbilityBase ability) {
		IPolymorphCapability cap = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		if (cap == null || cap.getMorph() != IPolymorphCapability.Morph.Wolf)
			return false;
		if (caster.world.isRemote)
			caster.swingArm(Hand.MAIN_HAND);
		cap.addFlagBits(bit);
		RayTraceResult ray = RayTraceHelper.tracePath(caster, caster.world, caster, (int) caster.getAttribute(PlayerEntity.REACH_DISTANCE).getValue(), 1, Sets.newHashSet(caster));
		if (ray instanceof EntityRayTraceResult) {
			caster.attackTargetEntityWithCurrentItem(((EntityRayTraceResult) ray).getEntity());
			IAoVCapability aov = CapabilityList.getCap(caster, CapabilityList.AOV);
			if (aov != null)
				aov.addExp(caster, 20, ability);
		}
		cap.subtractFlagBits(bit);
		return true;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(UNLOC.concat(".name"));
	}

	@Override
	public int getMaxCharges() {
		return CHARGES;
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public int getExtraCharges(LivingEntity entity, IAoVCapability cap) {
		return IAoVCapability.isImprovedCentered(entity, cap) ? getMaxCharges() : super.getExtraCharges(entity, cap);
	}

	@Override
	public double getMaxDistance() {
		return 0;
	}

	@Override
	public int getCoolDown() {
		return 30;
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
	public boolean shouldDisable(@Nullable PlayerEntity caster, IAoVCapability cap) {
		IPolymorphCapability poly = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		return poly == null || poly.getMorph() != IPolymorphCapability.Morph.Wolf;
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity caster, LivingEntity target) {
		return invoke(BIT, caster, this);
	}

	@Override
	public ResourceLocation getIcon() {
		return ICON;
	}

	@Override
	public boolean runOnClient() {
		return true;
	}
}
