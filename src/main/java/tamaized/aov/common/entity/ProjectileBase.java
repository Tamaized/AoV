package tamaized.aov.common.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nonnull;

public abstract class ProjectileBase extends EntityArrow implements IProjectile, IEntityAdditionalSpawnData {

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

	public ProjectileBase(World worldIn) {
		super(worldIn);
		startingPoint = getPositionVector();
		pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
		damage = 2.0D;
		setSize(0.5F, 0.5F);
		ignoreFrustumCheck = true;
	}

	public ProjectileBase(World worldIn, double x, double y, double z) {
		this(worldIn);
		setPosition(x, y, z);
		startingPoint = getPositionVector();
	}

	public ProjectileBase(World worldIn, EntityLivingBase shooter) {
		this(worldIn, shooter, shooter.posX, shooter.posY, shooter.posZ);
	}

	public ProjectileBase(World worldIn, EntityLivingBase shooter, double x, double y, double z) {
		this(worldIn);
		shootingEntity = shooter;
		setPosition(x, y + shooter.getEyeHeight(), z);
		startingPoint = getPositionVector();
		Vec3d vec = shooter.getLook(1.0f);
		setTheVelocity(vec.x, vec.y, vec.z);
	}

	@SuppressWarnings("unused")
	public ProjectileBase(World worldIn, EntityLivingBase shooter, EntityLivingBase target, float dmg) {
		this(worldIn, shooter.posX, shooter.posY + (double) shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
		shootingEntity = shooter;
		damage = dmg;
		double d0 = target.posX - posX;
		double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 2.0F) - posY;
		double d2 = target.posZ - posZ;
		shoot(d0, d1/* + d3 * 0.20000000298023224D */, d2, 1.6F, (float) (14 - world.getDifficulty().getId() * 4));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int c) {
		color = c;
	}

	public void setTheVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(x * x + z * z);
			this.rotationPitch = (float) (MathHelper.atan2(y, (double) f) * (180D / Math.PI));
			this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
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
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeDouble(posX);
		buffer.writeDouble(posY);
		buffer.writeDouble(posZ);

		buffer.writeDouble(damage);
		buffer.writeFloat(range);
		buffer.writeDouble(speed);

		buffer.writeInt(color);
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		setPosition(data.readDouble(), data.readDouble(), data.readDouble());
		setDamageRangeSpeed(data.readDouble(), data.readFloat(), data.readDouble());
		setColor(data.readInt());
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public boolean getIsCritical() {
		return false;
	}

	@Override
	public void setIsCritical(boolean critical) {

	}

	@Override
	public void onUpdate() {
		// super.onUpdate();
		onEntityUpdate();

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * (180.0D / Math.PI));
			prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, (double) f) * (180.0D / Math.PI));
		}

		BlockPos blockpos = new BlockPos(posX, posY, posZ);
		IBlockState iblockstate = world.getBlockState(blockpos);

		if (iblockstate.getMaterial() != Material.AIR) {// check if hit block
			AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(world, blockpos);
			if (axisalignedbb != Block.NULL_AABB && (axisalignedbb != null && axisalignedbb.offset(blockpos).contains(new Vec3d(posX, posY, posZ)))) {
				blockHit(iblockstate, blockpos);
				setDead();
				return;
			}
		}

		// Traveling
		++ticksInAir;
		if (maxRange >= 0) {
			if (startingPoint.distanceTo(getPositionVector()) >= maxRange)
				setDead();
		} else if (ticksInAir > 20 * 10)
			setDead();

		if (!world.isRemote)
			for (Entity e : world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(speed * 2F))) {
				if (e == this || e == shootingEntity || !canHitEntity(e))
					continue;
				onHit(e);
			}

		posX += motionX * speed;
		posY += motionY * speed;
		posZ += motionZ * speed;
		float f4 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * (180.0D / Math.PI));

		rotationPitch = (float) (MathHelper.atan2(motionY, (double) f4) * (180D / Math.PI));
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
			setDead();
			for (int l = 0; l < 4; ++l) {
				f4 = 0.25F;
				world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * (double) f4, posY - motionY * (double) f4, posZ - motionZ * (double) f4, motionX, motionY, motionZ);
			}
			f1 = 0.6F;
		}

		if (isWet())
			extinguish();

		motionX *= (double) f1;
		motionY *= (double) f1;
		motionZ *= (double) f1;
		motionY -= (double) f2;
		setPosition(posX, posY, posZ);
		doBlockCollisions();
	}

	protected boolean canHitEntity(Entity entity) {
		IAoVCapability cap = CapabilityList.getCap(shootingEntity, CapabilityList.AOV);
		return entity instanceof EntityLivingBase && (cap == null || IAoVCapability.selectiveTarget(shootingEntity, cap, (EntityLivingBase) entity));
	}

	protected abstract DamageSource getDamageSource();

	protected abstract float getDamageAmp(double damage, Entity shooter, Entity target);

	@Override
	protected void onHit(RayTraceResult raytraceResultIn) {
		// NO-OP
	}

	protected void onHit(Entity entity) {
		DamageSource damagesource = getDamageSource();

		if (isBurning() && !(entity instanceof EntityEnderman)) {
			entity.setFire(5);
		}

		if (entity.attackEntityFrom(damagesource, getDamageAmp(damage, shootingEntity, entity))) {
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

				arrowHit(entitylivingbase);

				if (shootingEntity != null && entitylivingbase != shootingEntity && entitylivingbase instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP) {
					((EntityPlayerMP) shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
				}
			}

			if (!(entity instanceof EntityEnderman)) {
				setDead();
			}
		} else {
			motionX *= -0.10000000149011612D;
			motionY *= -0.10000000149011612D;
			motionZ *= -0.10000000149011612D;
			rotationYaw += 180.0F;
			prevRotationYaw += 180.0F;
			ticksInAir = 0;

			if (!world.isRemote && motionX * motionX + motionY * motionY + motionZ * motionZ < 0.0010000000474974513D) {
				if (pickupStatus == EntityArrow.PickupStatus.ALLOWED) {
					entityDropItem(getArrowStack(), 0.1F);
				}

				setDead();
			}
		}
	}

	@Override
	protected abstract void arrowHit(EntityLivingBase entity);

	protected abstract void blockHit(IBlockState state, BlockPos pos);

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("damage", damage);
		nbt.setFloat("range", range);
		nbt.setDouble("speed", speed);
		nbt.setInteger("maxRange", maxRange);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		damage = nbt.getDouble("damage");
		range = nbt.getFloat("range");
		speed = nbt.getDouble("speed");
		maxRange = nbt.getInteger("maxRange");
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(@Nonnull EntityPlayer p_70100_1_) {

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

}