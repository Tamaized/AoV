package tamaized.aov.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.AoVEntities;
import tamaized.aov.registry.SoundEvents;

import java.util.Objects;

public class EntityMalefic extends ProjectileBase {

	private LivingEntity target;

	public EntityMalefic(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entitymalefic.get()), worldIn);
		setDamageRangeSpeed(2.0F, 0, 0.0F);
	}

	public EntityMalefic(World world, PlayerEntity shooter) {
		super(Objects.requireNonNull(AoVEntities.entitymalefic.get()), world, shooter);
	}

	public EntityMalefic(World world, PlayerEntity shooter, double x, double y, double z) {
		super(Objects.requireNonNull(AoVEntities.entitymalefic.get()), world, shooter, x, y, z);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
	}

	@Override
	public void tick() {
		if (!world.isRemote) {
			if (target == null || target.removed) {
				LivingEntity closest = null;
				double radius = 10;
				IAoVCapability cap = CapabilityList.getCap(shootingEntity, CapabilityList.AOV);
				for (LivingEntity e : world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(getPosX() - radius, getPosY() - radius, getPosZ() - radius, getPosX() + radius, getPosY() + radius, getPosZ() + radius)))
					if (shootingEntity != e)
						if (cap == null || !cap.hasSelectiveFocus() || IAoVCapability.selectiveTarget(shootingEntity, cap, e))
							if (closest == null || getDistance(closest) > getDistance(e))
								closest = e;
				target = closest;
			} else if (ticksExisted % 8 == 0) {
				double d0 = target.getPosX() - getPosX();
				double d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 2.0F) - getPosY();
				double d2 = target.getPosZ() - getPosZ();
				shoot(d0, d1, d2, (float) getSpeed(), (float) (14 - world.getDifficulty().getId() * 4));
			}
		} else
			for (int i = 0; i < 5; i++)
				world.addParticle(ParticleTypes.END_ROD, getPosX() + world.rand.nextDouble() - 0.5D, getPosY() + world.rand.nextDouble() - 0.5D, getPosZ() + world.rand.nextDouble() - 0.5D, 0, 5.0E-4F, 0);
		super.tick();
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
	protected void arrowHit(LivingEntity entity) {
		if (shootingEntity != null && !shootingEntity.removed) {
			IAoVCapability cap = CapabilityList.getCap(shootingEntity, CapabilityList.AOV);
			if (cap != null)
				cap.addExp(shootingEntity, 12, getSpell());
			SoundEvents.playMovingSoundOnServer(SoundEvents.malefic_hit, entity);
		}
	}

	@Override
	protected void blockHit(BlockState state, BlockPos pos) {
		if (!world.isRemote)
			world.playSound(null, pos, SoundEvents.malefic_hit, SoundCategory.NEUTRAL, 1.0F, 1.0F);
	}

}
