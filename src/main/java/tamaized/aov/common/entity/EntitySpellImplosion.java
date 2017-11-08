package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.tammodized.client.particles.ParticleFluff;

import javax.annotation.Nonnull;

public class EntitySpellImplosion extends Entity {

	private Entity target;
	private int tick = 0;

	public EntitySpellImplosion(World worldIn) {
		super(worldIn);
	}

	public EntitySpellImplosion(World world, Entity entity) {
		this(world);
		target = entity;
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
		tick = rand.nextInt(80);
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {

	}

	@Override
	public void onUpdate() {
		if (world.isRemote) {
			for (int index = 0; index < 10; index++) {
				Vec3d vec = getLook(1.0F).rotatePitch(rand.nextInt(360)).rotateYaw(rand.nextInt(360));
				float speed = 0.08F;
				net.minecraft.client.Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleFluff(world, getPositionVector().addVector(0, 0.65F, 0).add(vec), new Vec3d(-vec.x * speed, -vec.y * speed, -vec.z * speed), 7, 0, rand.nextFloat() * 0.90F + 0.10F, 0x7700FFFF));
			}
			return;
		}
		if (target == null || !target.isEntityAlive()) {
			setDead();
			return;
		}
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
		tick++;
		if (tick % (20 * 5) == 0)
			target.attackEntityFrom(AoVDamageSource.destruction, Integer.MAX_VALUE);
	}

}