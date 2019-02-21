package tamaized.aov.common.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.SoundEvents;

public class EntityMalefic extends ProjectileBase {

	private EntityLivingBase target;

	public EntityMalefic(World worldIn) {
		super(worldIn);
		setDamageRangeSpeed(2.0F, 0, 0.0F);
	}

	public EntityMalefic(World world, EntityPlayer shooter) {
		super(world, shooter);
	}

	public EntityMalefic(World world, EntityPlayer shooter, double x, double y, double z) {
		super(world, shooter, x, y, z);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
	}

	@Override
	public void onUpdate() {
		if (!world.isRemote) {
			if (target == null || target.isDead) {
				EntityLivingBase closest = null;
				double radius = 10;
				IAoVCapability cap = CapabilityList.getCap(shootingEntity, CapabilityList.AOV);
				for (EntityLivingBase e : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius)))
					if (shootingEntity != e)
						if (cap == null || !cap.hasSelectiveFocus() || IAoVCapability.selectiveTarget(shootingEntity, cap, e))
							if (closest == null || getDistance(closest) > getDistance(e))
								closest = e;
				target = closest;
			} else if (ticksExisted % 8 == 0) {
				double d0 = target.posX - posX;
				double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 2.0F) - posY;
				double d2 = target.posZ - posZ;
				shoot(d0, d1, d2, (float) getSpeed(), (float) (14 - world.getDifficulty().getId() * 4));
			}
		} else
			for (int i = 0; i < 5; i++)
				world.spawnParticle(EnumParticleTypes.END_ROD, posX + world.rand.nextDouble() - 0.5D, posY + world.rand.nextDouble() - 0.5D, posZ + world.rand.nextDouble() - 0.5D, 0, 5.0E-4F, 0);
		super.onUpdate();
	}

	@Override
	protected DamageSource getDamageSource() {
		return AoVDamageSource.createEntityDamageSource(AoVDamageSource.COSMIC, shootingEntity);
	}

	@Override
	protected float getDamageAmp(double damage, Entity shooter, Entity target) {
		return (float) damage;
	}

	@Override
	protected void arrowHit(EntityLivingBase entity) {
		if (shootingEntity != null && !shootingEntity.isDead) {
			IAoVCapability cap = CapabilityList.getCap(shootingEntity, CapabilityList.AOV);
			if (cap != null)
				cap.addExp(shootingEntity, 12, getSpell());
			SoundEvents.playMovingSoundOnServer(SoundEvents.malefic_hit, entity);
		}
	}

	@Override
	protected void blockHit(IBlockState state, BlockPos pos) {
		if (!world.isRemote)
			world.playSound(null, pos, SoundEvents.malefic_hit, SoundCategory.NEUTRAL, 1.0F, 1.0F);
	}

}
