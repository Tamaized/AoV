package Tamaized.AoV.entity.projectile.caster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import Tamaized.AoV.AoV;
import Tamaized.AoV.entity.projectile.ProjectileBase;

public class ProjectileNimbusRay extends ProjectileBase {

	public ProjectileNimbusRay(World worldIn) {
		super(worldIn);
		setDamageRangeSpeed(2.0F, getRange(), 0.0F);
	}

	@Override
	protected void particles() {

	}

	@Override
	protected DamageSource getDamageSource() {
		return AoV.damageSources.damageSource_caster_NimbusRay;
	}

}
