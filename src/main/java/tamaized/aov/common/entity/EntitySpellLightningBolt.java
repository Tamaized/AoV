package tamaized.aov.common.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class EntitySpellLightningBolt extends Entity {

	public long boltVertex = this.rand.nextLong();
	private int lightningState = 2;
	private int boltLivingTime = this.rand.nextInt(3) + 1;
	private EntityLivingBase caster;
	private float damage;

	public EntitySpellLightningBolt(World world){
		super(world);
		damage = 3F;
	}

	public EntitySpellLightningBolt(World world, EntityLivingBase caster, float damage) {
		this(world);
		this.caster = caster;
		this.damage = damage;
	}

	@Override
	protected void entityInit() {

	}

	@Nonnull
	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.WEATHER;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.lightningState == 2) {
			this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
			this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.WEATHER, 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
		}

		--this.lightningState;

		if (this.lightningState < 0) {
			if (this.boltLivingTime == 0) {
				this.setDead();
			} else if (this.lightningState < -this.rand.nextInt(10)) {
				--this.boltLivingTime;
				this.lightningState = 1;

				if (!this.world.isRemote) {
					this.boltVertex = this.rand.nextLong();
					BlockPos blockpos = new BlockPos(this);

					if (this.world.getGameRules().getBoolean("doFireTick") && this.world.isAreaLoaded(blockpos, 10) && this.world.getBlockState(blockpos).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(this.world, blockpos)) {
						this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
					}
				}
			}
		}

		if (this.lightningState >= 0) {
			List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - 3.0D, this.posY - 3.0D, this.posZ - 3.0D, this.posX + 3.0D, this.posY + 6.0D + 3.0D, this.posZ + 3.0D));

			for (Entity entity : list)
				if (!(entity instanceof EntityLivingBase) || IAoVCapability.selectiveTarget(caster, CapabilityHelper.getCap(caster, CapabilityList.AOV, null), (EntityLivingBase) entity))
					entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, damage);
		}
	}

	@Override
	protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {

	}

}
