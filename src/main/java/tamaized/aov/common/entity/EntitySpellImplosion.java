package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.registry.AoVDamageSource;

import javax.annotation.Nonnull;

public class EntitySpellImplosion extends Entity {

	private Entity caster;
	private EntityLivingBase target;
	private int tick = 0;

	public EntitySpellImplosion(World worldIn) {
		super(worldIn);
	}

	public EntitySpellImplosion(World world, Entity caster, EntityLivingBase entity) {
		this(world);
		this.caster = caster;
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
				AoV.proxy.spawnFluffParticle(world, getPositionVector().addVector(0, 0.65F, 0).add(vec), new Vec3d(-vec.x * speed, -vec.y * speed, -vec.z * speed), 7, 0, rand.nextFloat() * 0.90F + 0.10F, 0x7700FFFF);
			}
			return;
		}
		if (target == null || !target.isEntityAlive()) {
			setDead();
			return;
		}
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
		tick++;
		if (tick % (20 * 5) == 0) {
			float damage = target.getRNG().nextInt((int) Math.floor(target.getHealth())) <= 14 ? target.getMaxHealth() : target.getMaxHealth() / 2F;
			IAoVCapability cap = caster != null && caster.hasCapability(CapabilityList.AOV, null) ? caster.getCapability(CapabilityList.AOV, null) : null;
			if (cap != null)
				damage *= (1f + (cap.getSpellPower() / 100f));
			target.attackEntityFrom(AoVDamageSource.destruction, damage);
			if (cap != null)
				cap.addExp(caster, 25, Abilities.implosion);
			setDead();
		}
	}

}