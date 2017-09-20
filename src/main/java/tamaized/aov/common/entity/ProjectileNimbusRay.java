package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.registry.AoVDamageSource;

public class ProjectileNimbusRay extends ProjectileBase {

	public ProjectileNimbusRay(World worldIn) {
		super(worldIn);
		setDamageRangeSpeed(2.0F, 0, 0.0F);
	}

	public ProjectileNimbusRay(World world, EntityPlayer shooter, double x, double y, double z) {
		super(world, shooter, x, y, z);
	}

	@Override
	protected DamageSource getDamageSource() {
		return AoVDamageSource.damageSource_caster_NimbusRay;
	}

	@Override
	protected boolean canHitEntity(Entity entity) {
		return true;
	}

	@Override
	protected float getDamageAmp(double damage, Entity shooter, Entity target) {
		return (float) (damage * (target instanceof EntityMob && ((EntityMob) target).isEntityUndead() ? 2 : 1));
	}

	@Override
	protected void arrowHit(EntityLivingBase entity) {
		if (shootingEntity != null) {
			IAoVCapability cap = shootingEntity.getCapability(CapabilityList.AOV, null);
			if (cap != null)
				cap.addExp(shootingEntity, 20, getSpell());
		}
	}

}
