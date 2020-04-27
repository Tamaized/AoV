package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.AoV;
import tamaized.aov.client.ParticleHelper;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EntitySpellAoVParticles extends Entity {
	private static final ParticleHelper.ParticleType[] particles = ParticleHelper.ParticleType.values();
	private static final DataParameter<Integer> PARTICLE = EntityDataManager.createKey(EntitySpellAoVParticles.class, DataSerializers.VARINT);
	private static final DataParameter<Integer[]> COLOR = EntityDataManager.createKey(EntitySpellAoVParticles.class, AoV.VARINTS);
	private static final DataParameter<Integer> RATE = EntityDataManager.createKey(EntitySpellAoVParticles.class, DataSerializers.VARINT);

	private Entity target;
	private int tick = 0;

	public EntitySpellAoVParticles(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entityspellaovparticles.get()), worldIn);
	}

	public EntitySpellAoVParticles(World world, Entity entity, ParticleHelper.ParticleType particle, int rate, Integer... colors) {
		this(world);
		target = entity;
		setPositionAndUpdate(target.getPosX(), target.getPosY(), target.getPosZ());
		tick = rand.nextInt(10) + 20;
		setParticle(particle);
		setColors(colors);
		dataManager.set(RATE, rate);
	}

	@Override
	public float getBrightness() {
		return 1.0F;
	}

	@Override
	protected void registerData() {
		dataManager.register(PARTICLE, ParticleHelper.ParticleType.Fluff.ordinal());
		dataManager.register(COLOR, new Integer[]{0xFFFFFFFF});
		dataManager.register(RATE, 10);
	}

	public Integer[] getColors() {
		return dataManager.get(COLOR);
	}

	public void setColors(Integer... color) {
		dataManager.set(COLOR, color);
	}

	public ParticleHelper.ParticleType getParticle() {
		int index = dataManager.get(PARTICLE);
		return index >= 0 && index < particles.length ? particles[index] : ParticleHelper.ParticleType.Fluff;
	}

	public void setParticle(ParticleHelper.ParticleType particle) {
		dataManager.set(PARTICLE, particle.ordinal());
	}

	@Override
	protected void readAdditional(@Nonnull CompoundNBT compound) {
		dataManager.set(PARTICLE, compound.getInt("particle"));
	}

	@Override
	protected void writeAdditional(@Nonnull CompoundNBT compound) {
		compound.putInt("particle", dataManager.get(PARTICLE));
	}

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void tick() {
		if (world.isRemote) {
			for (int index = 0; index < dataManager.get(RATE); index++) {
				Vec3d vec = getLook(1.0F).rotatePitch(rand.nextInt(360)).rotateYaw(rand.nextInt(360));
				Vec3d pos = getPositionVector().add(0, 0.65F, 0).add(vec);
				ParticleHelper.spawnParticle(getParticle(), world, pos, new Vec3d(0, 0.0625F, 0), 16, 0, 1, getColors());
			}
			return;
		}
		if (tick-- <= 0 || target == null || !target.isAlive()) {
			remove();
			return;
		}
		setPositionAndUpdate(target.getPosX(), target.getPosY(), target.getPosZ());
	}

}