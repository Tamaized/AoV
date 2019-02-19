package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.aov.registry.AoVDamageSource;

import javax.annotation.Nonnull;

public class EntityCombust extends Entity {

	public float scale;
	public float initalScale = 135F;
	private Entity caster;
	private Entity target;
	private float damage = 2F;

	public EntityCombust(World worldIn) {
		super(worldIn);
	}

	public EntityCombust(World world, Entity caster, Entity target, float dmg) {
		this(world);
		this.caster = caster;
		this.target = target;
		damage = dmg;
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
	}

	@Override
	protected void entityInit() {

	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (world.isRemote) {
			Vec3d vec = getLook(1.0F).rotatePitch(rand.nextInt(360)).rotateYaw(rand.nextInt(360));
			world.spawnParticle(EnumParticleTypes.END_ROD, 0, 0, 0, vec.x, 5.0E-4F + vec.y, vec.z);
			return;
		}
		if (ticksExisted >= 18 * 20 || target == null || target.isDead) {
			setDead();
			return;
		} else if (ticksExisted % 40 == 0)
			target.attackEntityFrom(AoVDamageSource.createEntityDamageSource(AoVDamageSource.COSMIC, caster), damage);
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
	}

	@Override
	protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {

	}
}
