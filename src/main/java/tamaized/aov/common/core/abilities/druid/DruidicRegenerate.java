package tamaized.aov.common.core.abilities.druid;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.SkillIcons;
import tamaized.aov.common.helper.ParticleHelper;
import tamaized.aov.proxy.CommonProxy;
import tamaized.tammodized.common.helper.CapabilityHelper;

import java.util.List;

public class DruidicRegenerate extends AbilityBase {

	private static final String UNLOC = "aov.spells.druidregenerate";

	public DruidicRegenerate(){
		super(

				new TextComponentTranslation(UNLOC.concat(".name")),

				new TextComponentTranslation(""),

				new TextComponentTranslation(UNLOC.concat(".desc"))

		);
	}

	@Override
	public String getName() {
		return null;
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
		return 6;
	}

	@Override
	public int getCoolDown() {
		return 3;
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
		IAoVCapability cap = CapabilityHelper.getCap(caster, CapabilityList.AOV, null);
		if(cap != null) {
			int range = (int) (getMaxDistance() * 2);
			List<EntityLivingBase> list = caster.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(caster.getPosition().add(-range, -range, -range), caster.getPosition().add(range, range, range)));
			for (EntityLivingBase entity : list) {
				entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 10));
				cap.addExp(caster, 15, this);
			}
			return true;
		}
		return false;
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.vitality;
	}
}
