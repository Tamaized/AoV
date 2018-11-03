package tamaized.aov.common.core.abilities.druid;

import com.google.common.collect.Sets;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.SkillIcons;
import tamaized.tammodized.common.helper.CapabilityHelper;
import tamaized.tammodized.common.helper.RayTraceHelper;

import javax.annotation.Nullable;

public class FuriousClaw extends AbilityBase {

	public static final byte BIT = 0b1000;

	private static final String UNLOC = "aov.spells.furiousclaw";

	public FuriousClaw() {
		super(

				new TextComponentTranslation(UNLOC.concat(".name")),

				new TextComponentTranslation(""),

				new TextComponentTranslation(UNLOC.concat(".desc"))

		);
	}

	@Override
	public String getName() {
		return I18n.format(UNLOC.concat(".name"));
	}

	@Override
	public int getMaxCharges() {
		return 10;
	}

	@Override
	public int getChargeCost() {
		return 1;
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
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return false;
	}

	@Override
	public boolean shouldDisable(@Nullable EntityPlayer caster, IAoVCapability cap) {
		IPolymorphCapability poly = CapabilityHelper.getCap(caster, CapabilityList.POLYMORPH, null);
		return poly == null || poly.getMorph() != IPolymorphCapability.Morph.Wolf;
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		return invoke(BIT, caster, this);
	}

	public static boolean invoke(byte bit, EntityPlayer caster, AbilityBase ability){
		IPolymorphCapability cap = CapabilityHelper.getCap(caster, CapabilityList.POLYMORPH, null);
		if (cap == null || cap.getMorph() != IPolymorphCapability.Morph.Wolf)
			return false;
		if (caster.world.isRemote)
			caster.swingArm(EnumHand.MAIN_HAND);
		cap.addFlagBits(bit);
		RayTraceResult ray = RayTraceHelper.tracePath(caster.world, caster, (int) caster.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue(), 1, Sets.newHashSet(caster));
		if (ray != null && ray.typeOfHit == RayTraceResult.Type.ENTITY) {
			caster.attackTargetEntityWithCurrentItem(ray.entityHit);
			IAoVCapability aov = CapabilityHelper.getCap(caster, CapabilityList.AOV, null);
			if (aov != null)
				aov.addExp(caster, 20, ability);
		}
		cap.subtractFlagBits(bit);
		return true;
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.vitality;
	}

	@Override
	public boolean runOnClient() {
		return true;
	}
}