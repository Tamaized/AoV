package tamaized.aov.common.core.abilities.druid;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.SkillIcons;
import tamaized.aov.common.entity.EntitySpellLightningBolt;
import tamaized.aov.common.helper.UtilHelper;
import tamaized.aov.registry.SoundEvents;
import tamaized.tammodized.common.helper.CapabilityHelper;
import tamaized.tammodized.common.helper.RayTraceHelper;

import javax.annotation.Nullable;
import java.util.HashSet;

public class LightningStrike extends AbilityBase {

	private static final String UNLOC = "aov.spells.litstrike";
	private static final float DAMAGE = 5F;
	private static final int DISTANCE = 40;

	public LightningStrike() {
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
		return 10;
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public double getMaxDistance() {
		return DISTANCE;
	}

	@Override
	public int getCoolDown() {
		return 15;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return IAoVCapability.selectiveTarget(caster, cap, target);
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		IAoVCapability cap = CapabilityHelper.getCap(caster, CapabilityList.AOV, null);
		if (cap == null)
			return false;
		float damage = DAMAGE * (1F + (cap.getSpellPower() / 100F));
		EntitySpellLightningBolt strike = new EntitySpellLightningBolt(caster.world, caster, damage);
		Vec3d pos = UtilHelper.getSpellLocation(caster, DISTANCE, target);
		strike.setPosition(pos.x, pos.y, pos.z);
		caster.world.spawnEntity(strike);
//		strike.world.playSound(null, strike.posX, strike.posY, strike.posZ, SoundEvents.firestrike, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		return true;
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.vitality;
	}
}
