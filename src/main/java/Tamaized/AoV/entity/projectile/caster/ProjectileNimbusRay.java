package Tamaized.AoV.entity.projectile.caster;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.entity.projectile.ProjectileBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

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
		return AoV.damageSources.damageSource_caster_NimbusRay;
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
		IAoVCapability cap = shootingEntity.getCapability(CapabilityList.AOV, null);
		if(cap != null) cap.addExp(20, getSpell());
	}

}
