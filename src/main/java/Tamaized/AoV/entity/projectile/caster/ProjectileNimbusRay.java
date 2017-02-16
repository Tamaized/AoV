package Tamaized.AoV.entity.projectile.caster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.entity.projectile.ProjectileBase;

public class ProjectileNimbusRay extends ProjectileBase {

	public ProjectileNimbusRay(World worldIn) {
		super(worldIn);
		setDamageRangeSpeed(2.0F, 0, 0.0F);
	}

	@Override
	protected DamageSource getDamageSource() {
		return AoV.damageSources.damageSource_caster_NimbusRay;
	}

	@Override
	protected boolean canHitEntity(Entity entity) {
		return true;
	}

	@Override
	protected float getDamageAmp(double damage, Entity shooter, Entity target) {
		return (float) ((damage * (shooter.hasCapability(CapabilityList.AOV, null) ? (1.0F+(shooter.getCapability(CapabilityList.AOV, null).getSpellPower()/100F)) : 1)) * (target instanceof EntityMob && ((EntityMob)target).isEntityUndead() ? 2 : 1));
	}

}
