package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ProjectileFlameStrike extends Entity implements IProjectile, IEntityAdditionalSpawnData {

	private Entity attacker;
	private float damage = 2;

	public ProjectileFlameStrike(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.projectileflamestrike.get()), worldIn);
		setFire(100);
		setMotion(0, -0.8, 0);
		setRotation(0, 90);
	}

	public ProjectileFlameStrike(World world, Entity attacker) {
		this(world);
		this.attacker = attacker;
	}

	public ProjectileFlameStrike(World world, Entity attacker, float dmg) {
		this(world, attacker);
		damage = dmg;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
	}

	public void setDamage(float d) {
		damage = d;
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeDouble(posX);
		buffer.writeDouble(posY);
		buffer.writeDouble(posZ);
	}

	@Override
	public void readSpawnData(PacketBuffer data) {
		setPosition(data.readDouble(), data.readDouble(), data.readDouble());

	}

	@Override
	public void tick() {
		baseTick();
		if (world.isRemote) {
			Vec3d vec = getLook(1.0F);
			for (int index = 0; index < 20; index++)
				world.addParticle(ParticleTypes.FLAME,

						posX,

						posY,

						posZ,

						-((0.015 * vec.x) + ((rand.nextFloat() * 0.5) - 0.25)),

						((0.015 * vec.y) + ((rand.nextFloat() * 0.5) - 0.25)),

						-((0.015 * vec.z) + ((rand.nextFloat() * 0.5) - 0.25))

				);
		}
		Vec3d vec3d1 = new Vec3d(posX, posY, posZ);
		Vec3d vec3d = vec3d1.add(getMotion());
		RayTraceResult ray = world.rayTraceBlocks(new RayTraceContext(vec3d1, vec3d, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
		switch (ray.getType()) {
			case BLOCK:
			case ENTITY:
				explode();
				return;
			case MISS:
			default:
				break;
		}
		posY += getMotion().y;
	}

	private void explode() {
		remove();
		for (LivingEntity entity : world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(getPosition().add(-10, -1, -10), getPosition().add(10, 5, 10)))) {
			if (attacker != null) {
				IAoVCapability cap = CapabilityList.getCap(attacker, CapabilityList.AOV);
				if (entity == attacker || (cap != null && !IAoVCapability.selectiveTarget(attacker, cap, entity)))
					continue;
				if (cap != null)
					cap.addExp(attacker, 20, Abilities.flameStrike);
			}
			entity.setFire(15);
			entity.attackEntityFrom(AoVDamageSource.createEntityDamageSource(DamageSource.IN_FIRE, attacker), damage);
		}
		for (int i = 0; i <= 80; i++)
			world.addParticle(ParticleTypes.FLAME, posX, posY, posZ, (world.rand.nextFloat() * 1F) - 0.5F, world.rand.nextFloat() * 0.04F + 0.01F, (world.rand.nextFloat() * 1F) - 0.5F);
		for (int i = 0; i <= 100; i++)
			world.addParticle(ParticleTypes.LARGE_SMOKE, posX, posY, posZ, (world.rand.nextFloat() * 0.75F) - 0.375F, world.rand.nextFloat() * 0.04F + 0.01F, (world.rand.nextFloat() * 0.75F) - 0.375F);
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x = x / (double) f;
		y = y / (double) f;
		z = z / (double) f;
		x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		x = x * (double) velocity;
		y = y * (double) velocity;
		z = z * (double) velocity;
		setMotion(x, y, z);
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(y, (double) f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}

	@Override
	protected void registerData() {

	}

	@Override
	protected void readAdditional(@Nonnull CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(@Nonnull CompoundNBT compound) {

	}

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
