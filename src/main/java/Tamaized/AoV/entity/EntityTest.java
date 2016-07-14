package Tamaized.AoV.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityTest extends Entity {
	
	private final int range = 10;
	private final int life = 20*10;
	private int tick = 0;

	public EntityTest(World worldIn, BlockPos pos) {
		super(worldIn);
		this.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
	}

	@Override
	protected void entityInit() {
		
	}
	
	public void onEntityUpdate(){
		if(tick >= life) this.setDead();
		super.onEntityUpdate();
		if(tick % 20 == 0){
			for(int x = -range; x<range; x++){
				for(int z = -range; z<range; z++){
					if(Math.floor(Math.random()*2) == 0){
						double d0 = (double) ((float) (posX+x) + 0.4F + rand.nextFloat() * 0.2F);
						double d1 = (double) ((float) (posY) + 0.0F + rand.nextFloat() * 0.3F);
						double d2 = (double) ((float) (posZ+z) + 0.4F + rand.nextFloat() * 0.2F);
						worldObj.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, 0, 0.5, 0);
					}
				}
			}
		}else{
			
		}
		tick++;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readFromNBT(tagCompund);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
	}

}
