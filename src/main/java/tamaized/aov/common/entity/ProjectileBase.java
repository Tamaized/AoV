package tamaized.aov.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.AbilityBase;

import javax.annotation.Nonnull;

public abstract class ProjectileBase extends AbstractArrowEntity implements IProjectile, IEntityAdditionalSpawnData {

	/**
	 * The owner of this arrow.
	 */
	public Entity shootingEntity;
	private int ticksInAir;
	private double damage;

	private double speed = 0.5D;
	private float range = 0.0F;
	private int maxRange = -1;
	private Vec3d startingPoint;

	private AbilityBase parentSpell;
	private int color = 0xFFFFFFFF;

	public ProjectileBase(EntityType type, World worldIn) {
		super(type, worldIn);
		startingPoint = getPositionVector();
		pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
		damage = 2.0D;
		ignoreFrustumCheck = true;
	}

	public ProjectileBase(EntityType type, World worldIn, double x, double y, double z) {
		this(type, worldIn);
		setPosition(x, y, z);
		startingPoint = getPositionVector();
	}

	public ProjectileBase(EntityType type, World worldIn, LivingEntity shooter) {
		this(type, worldIn, shooter, shooter.getPosX(), shooter.getPosY(), shooter.getPosZ());
	}

	public ProjectileBase(EntityType type, World worldIn, LivingEntity shooter, double x, double y, double z) {
		this(type, worldIn);
		shootingEntity = shooter;
		setPosition(x, y + shooter.getEyeHeight(), z);
		startingPoint = getPositionVector();
		Vec3d vec = shooter.getLook(1.0f);
		setTheVelocity(vec.x, vec.y, vec.z);
	}

	@SuppressWarnings("unused")
	public ProjectileBase(EntityType type, World worldIn, LivingEntity shooter, LivingEntity target, float dmg) {
		this(type, worldIn, shooter.getPosX(), shooter.getPosY() + (double) shooter.getEyeHeight() - 0.10000000149011612D, shooter.getPosZ());
		shootingEntity = shooter;
		damage = dmg;
		double d0 = target.getPosX() - getPosX();
		double d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 2.0F) - getPosY();
		double d2 = target.getPosZ() - getPosZ();
		shoot(d0, d1/* + d3 * 0.20000000298023224D */, d2, 1.6F, (float) (14 - world.getDifficulty().getId() * 4));
	}

	@Override
	public float getBrightness() {
		return 1.0F;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int c) {
		color = c;
	}

	public void setTheVelocity(double x, double y, double z) {
		setMotion(x, y, z);

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(x * x + z * z);
			this.rotationPitch = (float) (MathHelper.atan2(y, (double) f) * (180D / Math.PI));
			this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
		}
	}

	public AbilityBase getSpell() {
		return parentSpell;
	}

	public void setSpell(AbilityBase ability) {
		parentSpell = ability;
	}

	public void setMaxRange(int range) {
		maxRange = range;
	}

	public float getRange() {
		return range;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(int s) {
		speed = s;
	}

	@Override
	public double getDamage() {
		return damage;
	}

	@Override
	public void setDamage(double damageIn) {
		damage = damageIn;
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeDouble(getPosX());
		buffer.writeDouble(getPosY());
		buffer.writeDouble(getPosZ());

		buffer.writeDouble(damage);
		buffer.writeFloat(range);
		buffer.writeDouble(speed);

		buffer.writeInt(color);
	}

	@Override
	public void readSpawnData(PacketBuffer data) {
		setPosition(data.readDouble(), data.readDouble(), data.readDouble());
		setDamageRangeSpeed(data.readDouble(), data.readFloat(), data.readDouble());
		setColor(data.readInt());
	}

	@Override
	protected void registerData() {

	}

	@Override
	public boolean getIsCritical() {
		return false;
	}

	@Override
	public void setIsCritical(boolean critical) {

	}

	@Override
	public void tick() {
		// super.tick();
		baseTick();

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(getMotion().x * getMotion().x + getMotion().z * getMotion().z);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(getMotion().x, getMotion().z) * (180.0D / Math.PI));
			prevRotationPitch = rotationPitch = (float) (Math.atan2(getMotion().y, (double) f) * (180.0D / Math.PI));
		}

		BlockPos blockpos = new BlockPos(getPosX(), getPosY(), getPosZ());
		BlockState iblockstate = world.getBlockState(blockpos);

		if (!iblockstate.isAir(this.world, blockpos)) {
			VoxelShape voxelshape = iblockstate.getCollisionShape(this.world, blockpos);
			if (!voxelshape.isEmpty()) {
				for (AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
					if (axisalignedbb.offset(blockpos).contains(new Vec3d(this.getPosX(), this.getPosY(), this.getPosZ()))) {
						blockHit(iblockstate, blockpos);
						remove();
						return;
					}
				}
			}
		}

		// Traveling
		++ticksInAir;
		if (maxRange >= 0) {
			if (startingPoint.distanceTo(getPositionVector()) >= maxRange)
				remove();
		} else if (ticksInAir > 20 * 10)
			remove();

		if (!world.isRemote)
			for (Entity e : world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(speed * 2F))) {
				if (e == this || e == shootingEntity || !canHitEntity(e))
					continue;
				onHit(e);
			}

		setPosition(getPosX() + getMotion().x * speed, getPosY() + getMotion().y * speed, getPosZ() + getMotion().z * speed);
		float f4 = MathHelper.sqrt(getMotion().x * getMotion().x + getMotion().z * getMotion().z);
		rotationYaw = (float) (Math.atan2(getMotion().x, getMotion().z) * (180.0D / Math.PI));

		rotationPitch = (float) (MathHelper.atan2(getMotion().y, (double) f4) * (180D / Math.PI));
		while (rotationPitch - prevRotationPitch < -180.0F) {
			prevRotationPitch -= 360.0F;
		}

		while (rotationPitch - prevRotationPitch >= 180.0F) {
			prevRotationPitch += 360.0F;
		}

		while (rotationYaw - prevRotationYaw < -180.0F) {
			prevRotationYaw -= 360.0F;
		}

		while (rotationYaw - prevRotationYaw >= 180.0F) {
			prevRotationYaw += 360.0F;
		}

		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		float f1 = 0.99F;
		float f2 = range;

		if (isInWater()) {
			remove();
			for (int l = 0; l < 4; ++l) {
				f4 = 0.25F;
				world.addParticle(ParticleTypes.BUBBLE, getPosX() - getMotion().x * (double) f4, getPosY() - getMotion().y * (double) f4, getPosZ() - getMotion().z * (double) f4, getMotion().x, getMotion().y, getMotion().z);
			}
			f1 = 0.6F;
		}

		if (isWet())
			extinguish();

		setMotion(getMotion().x * (double) f1, getMotion().y * (double) f1 - (double) f2, getMotion().z * (double) f1);
		setPosition(getPosX(), getPosY(), getPosZ());
		doBlockCollisions();
		postTick();
	}

	protected void postTick() {

	}

	protected boolean canHitEntity(Entity entity) {
		IAoVCapability cap = CapabilityList.getCap(shootingEntity, CapabilityList.AOV);
		return entity instanceof LivingEntity && (cap == null || IAoVCapability.selectiveTarget(shootingEntity, cap, (LivingEntity) entity));
	}

	protected abstract DamageSource getDamageSource();

	protected abstract float getDamageAmp(double damage, Entity shooter, Entity target);

	@Override
	protected void onHit(RayTraceResult raytraceResultIn) {
		// NO-OP
	}

	protected void onHit(Entity entity) {
		DamageSource damagesource = getDamageSource();

		if (isBurning() && !(entity instanceof EndermanEntity)) {
			entity.setFire(5);
		}

		if (entity.attackEntityFrom(damagesource, getDamageAmp(damage, shootingEntity, entity))) {
			if (entity instanceof LivingEntity) {
				LivingEntity entitylivingbase = (LivingEntity) entity;

				arrowHit(entitylivingbase);

				if (shootingEntity != null && entitylivingbase != shootingEntity && entitylivingbase instanceof PlayerEntity && shootingEntity instanceof ServerPlayerEntity) {
					((ServerPlayerEntity) shootingEntity).connection.sendPacket(new SChangeGameStatePacket(6, 0.0F));
				}
			}

			if (!(entity instanceof EndermanEntity)) {
				remove();
			}
		} else {
			setMotion(getMotion().x * -0.10000000149011612D, getMotion().y * -0.10000000149011612D, getMotion().z * -0.10000000149011612D);
			rotationYaw += 180.0F;
			prevRotationYaw += 180.0F;
			ticksInAir = 0;

			if (!world.isRemote && getMotion().x * getMotion().x + getMotion().y * getMotion().y + getMotion().z * getMotion().z < 0.0010000000474974513D) {
				if (pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
					entityDropItem(getArrowStack(), 0.1F);
				}

				remove();
			}
		}
	}

	@Override
	protected abstract void arrowHit(LivingEntity entity);

	protected abstract void blockHit(BlockState state, BlockPos pos);

	@Override
	public void writeAdditional(CompoundNBT nbt) {
		nbt.putDouble("damage", damage);
		nbt.putFloat("range", range);
		nbt.putDouble("speed", speed);
		nbt.putInt("maxRange", maxRange);
	}

	@Override
	public void readAdditional(CompoundNBT nbt) {
		damage = nbt.getDouble("damage");
		range = nbt.getFloat("range");
		speed = nbt.getDouble("speed");
		maxRange = nbt.getInt("maxRange");
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(@Nonnull PlayerEntity p_70100_1_) {

	}

	public void setDamageRangeSpeed(double d, float r, double s) {
		damage = d;
		range = r;
		speed = s;
	}

	@Nonnull
	@Override
	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}