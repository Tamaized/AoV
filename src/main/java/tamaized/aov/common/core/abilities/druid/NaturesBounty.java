package tamaized.aov.common.core.abilities.druid;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.SkillIcons;
import tamaized.aov.registry.AoVPotions;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class NaturesBounty extends AbilityBase {

	private static final String UNLOC = "aov.spells.naturesbounty";

	public NaturesBounty() {
		super(

				new TextComponentTranslation(UNLOC.concat(".name")),

				new TextComponentTranslation(""),

				new TextComponentTranslation(UNLOC.concat(".desc"))

		);
	}

	@Override
	public String getName() {
		return UNLOC.concat(".name");
	}

	@Override
	public int getMaxCharges() {
		return 5;
	}

	@Override
	public int getExtraCharges(EntityLivingBase entity, IAoVCapability cap) {
		return IAoVCapability.isImprovedCentered(entity, cap) ? getMaxCharges() : super.getExtraCharges(entity, cap);
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public double getMaxDistance() {
		return 10;
	}

	@Override
	public int getCoolDown() {
		return 45;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return IAoVCapability.canBenefit(caster, cap, target);
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		if (target != null)
			addPotionEffects(target);
		else
			addPotionEffects(caster);
		IAoVCapability cap = CapabilityHelper.getCap(caster, CapabilityList.AOV, null);
		if (cap != null)
			cap.addExp(caster, 15, this);
		return true;
	}

	private void addPotionEffects(EntityLivingBase entity) {
		entity.addPotionEffect(new PotionEffect(AoVPotions.naturesBounty, 20 * (60 * 15)));
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.vitality;
	}
}
