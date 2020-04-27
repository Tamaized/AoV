package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EntityCombust extends Entity {

	public float scale;
	public float initalScale = 135F;
	private Entity caster;
	private Entity target;
	private float damage = 2F;

	public EntityCombust(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entitycombust.get()), worldIn);
	}

	public EntityCombust(World world, Entity caster, Entity target, float dmg) {
		this(world);
		this.caster = caster;
		this.target = target;
		damage = dmg;
		setPositionAndUpdate(target.getPosX(), target.getPosY(), target.getPosZ());
	}

	@Override
	protected void registerData() {

	}

	@Override
	public float getBrightness() {
		return 1.0F;
	}

	@Override
	public void tick() {
		super.tick();
		if (world.isRemote) {
			Vec3d vec = getLook(1.0F).rotatePitch(rand.nextInt(360)).rotateYaw(rand.nextInt(360));
			world.addParticle(ParticleTypes.END_ROD, 0, 0, 0, vec.x, 5.0E-4F + vec.y, vec.z);
			return;
		}
		if (ticksExisted >= 18 * 20 || target == null || target.removed) {
			remove();
			return;
		} else if (ticksExisted % 40 == 0)
			target.attackEntityFrom(AoVDamageSource.createEntityDamageSource(AoVDamageSource.COSMIC, caster), damage);
		setPositionAndUpdate(target.getPosX(), target.getPosY(), target.getPosZ());
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
