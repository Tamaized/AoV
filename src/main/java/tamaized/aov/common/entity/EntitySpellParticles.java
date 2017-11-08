package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EntitySpellParticles extends Entity {

	private static final EnumParticleTypes[] particles = EnumParticleTypes.values();
	private static final DataParameter<Integer> PARTICLE = EntityDataManager.createKey(EntitySpellParticles.class, DataSerializers.VARINT);
	private Entity target;
	private int tick = 0;

	public EntitySpellParticles(World worldIn) {
		super(worldIn);
	}

	public EntitySpellParticles(World world, Entity entity, EnumParticleTypes particle) {
		this(world);
		target = entity;
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
		tick = rand.nextInt(30) + 20;
		setParticle(particle);
	}

	@Override
	protected void entityInit() {
		dataManager.register(PARTICLE, EnumParticleTypes.SPELL_WITCH.ordinal());
	}

	public EnumParticleTypes getParticle() {
		int index = dataManager.get(PARTICLE);
		return index >= 0 && index < particles.length ? particles[index] : EnumParticleTypes.SPELL_WITCH;
	}

	public void setParticle(EnumParticleTypes particle) {
		dataManager.set(PARTICLE, particle.ordinal());
	}

	@Override
	protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
		dataManager.set(PARTICLE, compound.getInteger("particle"));
	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
		compound.setInteger("particle", dataManager.get(PARTICLE));
	}

	@Override
	public void onUpdate() {
		if (world.isRemote) {
			for (int index = 0; index < 10; index++) {
				Vec3d vec = getLook(1.0F).rotatePitch(rand.nextInt(360)).rotateYaw(rand.nextInt(360));
				Vec3d pos = getPositionVector().addVector(0, 0.65F, 0).add(vec);
				world.spawnParticle(getParticle(), pos.x, pos.y, pos.z, 0, 0.25F, 0);
			}
			return;
		}
		if (tick-- <= 0 || target == null || !target.isEntityAlive()) {
			setDead();
			return;
		}
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
	}

}