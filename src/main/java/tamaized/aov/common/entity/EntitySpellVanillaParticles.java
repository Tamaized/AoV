package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.init.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EntitySpellVanillaParticles extends Entity {

	private static final DataParameter<IParticleData> PARTICLE = EntityDataManager.createKey(EntitySpellVanillaParticles.class, DataSerializers.PARTICLE_DATA);
	private static final DataParameter<Integer> RATE = EntityDataManager.createKey(EntitySpellVanillaParticles.class, DataSerializers.VARINT);
	private Entity target;
	private int tick = 0;

	public EntitySpellVanillaParticles(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entityspellvanillaparticles), worldIn);
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
		dataManager.register(PARTICLE, Particles.WITCH);
		dataManager.register(RATE, 10);
	}

	public IParticleData getParticle() {
		return dataManager.get(PARTICLE);
	}

	public void setParticle(IParticleData particle) {
		dataManager.set(PARTICLE, particle);
	}

	@Override
	protected void readAdditional(@Nonnull NBTTagCompound compound) {
		dataManager.set(PARTICLE, (IParticleData) Objects.requireNonNull(IRegistry.field_212632_u.func_212608_b(new ResourceLocation(compound.getString("particle")))));
	}

	@Override
	protected void writeAdditional(@Nonnull NBTTagCompound compound) {
		compound.setString("particle", getParticle().getType().getId().toString());
	}

	@Override
	public void tick() {
		if (world.isRemote) {
			for (int index = 0; index < dataManager.get(RATE); index++) {
				Vec3d vec = getLook(1.0F).rotatePitch(rand.nextInt(360)).rotateYaw(rand.nextInt(360));
				Vec3d pos = getPositionVector().add(0, 0.65F, 0).add(vec);
				world.spawnParticle(getParticle(), pos.x, pos.y, pos.z, 0, 0.25F, 0);
			}
			return;
		}
		if (tick-- <= 0 || target == null || !target.isAlive()) {
			remove();
			return;
		}
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
	}

}