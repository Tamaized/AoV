package Tamaized.AoV.entity;

import Tamaized.AoV.AoV;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

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
	protected void readEntityFromNBT(NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {

	}

	@Override
	public void onUpdate() {
		if(world.isRemote) return;
		if (target == null || !target.isEntityAlive()) {
			setDead();
			return;
		}
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
		tick++;
		if (tick % (20*5) == 0) target.attackEntityFrom(AoV.damageSources.destruction, Integer.MAX_VALUE);
	}

}