package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EntitySpellVanillaParticles extends Entity {

	private static final DataParameter<IParticleData> PARTICLE = EntityDataManager.createKey(EntitySpellVanillaParticles.class, DataSerializers.PARTICLE_DATA);
	private static final DataParameter<Integer> RATE = EntityDataManager.createKey(EntitySpellVanillaParticles.class, DataSerializers.VARINT);
	private Entity target;
	private int tick = 0;

	public EntitySpellVanillaParticles(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entityspellvanillaparticles.get()), worldIn);
	}

	public EntitySpellVanillaParticles(World world, Entity entity, IParticleData particle, int rate) {
		this(world);
		target = entity;
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
		tick = rand.nextInt(30) + 20;
		setParticle(particle);
		dataManager.set(RATE, rate);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
	}

	@Override
	protected void registerData() {
		dataManager.register(PARTICLE, ParticleTypes.WITCH);
		dataManager.register(RATE, 10);
	}

	public IParticleData getParticle() {
		return dataManager.get(PARTICLE);
	}

	public void setParticle(IParticleData particle) {
		dataManager.set(PARTICLE, particle);
	}

	@Override
	protected void readAdditional(@Nonnull CompoundNBT compound) {
		Objects.requireNonNull(Registry.PARTICLE_TYPE.getValue(new ResourceLocation(compound.getString("particle")))).ifPresent(particleType -> dataManager.set(PARTICLE, (IParticleData) particleType));
	}

	@Override
	protected void writeAdditional(@Nonnull CompoundNBT compound) {
		compound.putString("particle", Objects.requireNonNull(getParticle().getType().getRegistryName()).toString());
	}

	@Override
	public void tick() {
		if (world.isRemote) {
			for (int index = 0; index < dataManager.get(RATE); index++) {
				Vec3d vec = getLook(1.0F).rotatePitch(rand.nextInt(360)).rotateYaw(rand.nextInt(360));
				Vec3d pos = getPositionVector().add(0, 0.65F, 0).add(vec);
				world.addParticle(getParticle(), pos.x, pos.y, pos.z, 0, 0.25F, 0);
			}
			return;
		}
		if (tick-- <= 0 || target == null || !target.isAlive()) {
			remove();
			return;
		}
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
	}

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}