package Tamaized.AoV.entity.projectile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class ProjectileBase extends Entity implements IProjectile{
	
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private Block inTile;
    private int inData;
    private boolean inGround;
	
	private EntityLivingBase caster;
    private int ticksInGround;
    private int ticksInAir;

	public ProjectileBase(World worldIn, EntityLivingBase shooter) {
		super(worldIn);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
		caster = shooter;
        this.setLocationAndAngles(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 2.0f * 1.5F, 1.0F);
	}
	
	public void onUpdate(){
        super.onUpdate();
        
        if(this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F){
        	float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        	this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
        	this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }
        
        BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
        IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        
        if (block.getMaterial() != Material.air){
        	block.setBlockBoundsBasedOnState(this.worldObj, blockpos);
        	AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(this.worldObj, blockpos, iblockstate);
        	if (axisalignedbb != null && axisalignedbb.isVecInside(new Vec3(this.posX, this.posY, this.posZ))){
        		this.inGround = true;
        	}
        }
        
        if (this.inGround){
        	int j = block.getMetaFromState(iblockstate);
        	if (block == this.inTile && j == this.inData){
        		updateInGround();
        	}else{
        		this.inGround = false;
        		this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
        		this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
        		this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
        		this.ticksInGround = 0;
        		this.ticksInAir = 0;
        	}
        }else{
        	++this.ticksInAir;
        	Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
        	Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        	MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
        	vec31 = new Vec3(this.posX, this.posY, this.posZ);
        	vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);	

        	if(movingobjectposition != null) vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        	
        	Entity entity = null;
        	List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
        	double d0 = 0.0D;
        	
        	for (int i = 0; i < list.size(); ++i){
        		Entity entity1 = (Entity)list.get(i);
        		
        		if (entity1.canBeCollidedWith() && (entity1 != this.caster || this.ticksInAir >= 5)){	
        			float f1 = 0.3F;
        			AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
        			MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
        			
        			if (movingobjectposition1 != null){
        				double d1 = vec31.squareDistanceTo(movingobjectposition1.hitVec);
        				
        				if (d1 < d0 || d0 == 0.0D){
        					entity = entity1;
        					d0 = d1;
        				}
        			}
        		}
        	}
        	
        	if (entity != null){
        		movingobjectposition = new MovingObjectPosition(entity);
        	}
        	
        	if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer){
        		EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;
        		if (entityplayer.capabilities.disableDamage || this.caster instanceof EntityPlayer && !((EntityPlayer)this.caster).canAttackPlayer(entityplayer)){
        			movingobjectposition = null;
        		}
        	}
        	
        	if(movingobjectposition != null){
        		if(movingobjectposition.entityHit != null){
        			damageEntity(movingobjectposition.entityHit, caster == null ? this : caster);
        		}else{
        			BlockPos blockpos1 = movingobjectposition.getBlockPos();
        			this.xTile = blockpos1.getX();
        			this.yTile = blockpos1.getY();
        			this.zTile = blockpos1.getZ();
        			IBlockState iblockstate1 = this.worldObj.getBlockState(blockpos1);
        			this.inTile = iblockstate1.getBlock();
        			this.inData = this.inTile.getMetaFromState(iblockstate1);
        			this.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - this.posX));
        			this.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - this.posY));
        			this.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - this.posZ));
        			float f5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        			this.posX -= this.motionX / (double)f5 * 0.05000000074505806D;
        			this.posY -= this.motionY / (double)f5 * 0.05000000074505806D;
        			this.posZ -= this.motionZ / (double)f5 * 0.05000000074505806D;
        			this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F)); //TODO
        			this.inGround = true;
        			if (this.inTile.getMaterial() != Material.air){
        				this.inTile.onEntityCollidedWithBlock(this.worldObj, blockpos1, iblockstate1, this);
        			}
        		}
        	}
    		this.posX += this.motionX;
    		this.posY += this.motionY;
    		this.posZ += this.motionZ;
    		float f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
    		this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

    		for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f3) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F){
    			;
    		}
    		
    		while (this.rotationPitch - this.prevRotationPitch >= 180.0F){
    			this.prevRotationPitch += 360.0F;
    		}
    		
    		while (this.rotationYaw - this.prevRotationYaw < -180.0F){
    			this.prevRotationYaw -= 360.0F;
    		}
    		
    		while (this.rotationYaw - this.prevRotationYaw >= 180.0F){
    			this.prevRotationYaw += 360.0F;
    		}
    		
    		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
    		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
    		float f4 = 0.99F;
    		float f6 = 0.05F;
        	updateMotion(f4, f6);
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
	}
	
	protected void updateMotion(float f4, float f6){
		if(this.isInWater()){
			for (int i1 = 0; i1 < 4; ++i1){
				float f8 = 0.25F;
				this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f8, this.posY - this.motionY * (double)f8, this.posZ - this.motionZ * (double)f8, this.motionX, this.motionY, this.motionZ, new int[0]);
			}
			
			f4 = 0.6F;
		}
		
		this.motionX *= (double)f4;
		this.motionY *= (double)f4;
		this.motionZ *= (double)f4;
        this.motionY -= (double)f6;
	}
	
	protected void updateInGround(){
		++this.ticksInGround;
		if (this.ticksInGround >= 1200){
			this.setDead();
		}
	}
	
	protected abstract void damageEntity(Entity hit, Entity source);

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		
	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
		float f = MathHelper.sqrt_double(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)inaccuracy;
        y = y + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)inaccuracy;
        z = z + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt_double(x * x + z * z);
        this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * 180.0D / Math.PI);
        this.ticksInGround = 0;
	}
	
	public static DamageSource causeDamage(ProjectileBase p, Entity indirectEntityIn, DamageSource ds)
    {
        return (new EntityDamageSourceIndirect(ds.getDamageType(), p, indirectEntityIn)).setProjectile();
    }

}
