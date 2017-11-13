package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EntityCelestialOpposition extends Entity {

	public float tickBoi;

	public EntityCelestialOpposition(World worldIn) {
		super(worldIn);
		ignoreFrustumCheck = true;
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
		super.onUpdate();
		if (ticksExisted >= 20 * 5)
			setDead();
	}
}