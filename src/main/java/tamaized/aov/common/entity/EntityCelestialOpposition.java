package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EntityCelestialOpposition extends Entity {

	public float tickBoi;

	public EntityCelestialOpposition(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entitycelestialopposition), worldIn);
		ignoreFrustumCheck = true;
	}

	@Override
	protected void registerData() {

	}

	@Override
	protected void readAdditional(@Nonnull NBTTagCompound compound) {

	}

	@Override
	protected void writeAdditional(@Nonnull NBTTagCompound compound) {

	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
	}

	@Override
	public void tick() {
		super.tick();
		if (ticksExisted >= 20 * 5)
			remove();
	}
}