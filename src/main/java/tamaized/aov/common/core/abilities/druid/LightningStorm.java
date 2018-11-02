package tamaized.aov.common.core.abilities.druid;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.SkillIcons;
import tamaized.aov.common.entity.EntitySpellLightningStorm;
import tamaized.aov.common.helper.UtilHelper;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class LightningStorm extends AbilityBase {

	private static final String UNLOC = "aov.spells.litstorm";
	private static final float DAMAGE = 3F;
	private static final int DISTANCE = 25;

	public LightningStorm() {
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
		return 2;
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
		return 45;
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
		EntitySpellLightningStorm storm = new EntitySpellLightningStorm(caster.world, caster, damage);
		Vec3d pos = UtilHelper.getSpellLocation(caster, DISTANCE, target);
		storm.setPosition(pos.x, pos.y + 10F, pos.z);
		caster.world.spawnEntity(storm);
		return true;
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.vitality;
	}
}
