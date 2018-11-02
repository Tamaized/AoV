package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class EntitySpellLightningStorm extends Entity {

	private float damage = 1F;
	private EntityLivingBase caster;
	private UUID casterID;
	private int nextMod = 30;

	public EntitySpellLightningStorm(World worldIn) {
		super(worldIn);
		setSize(12F, 0.1F);
	}

	public EntitySpellLightningStorm(World worldIn, EntityLivingBase caster, float damage) {
		this(worldIn);
		this.caster = caster;
		this.damage = damage;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!world.isRemote) {
			if (ticksExisted % nextMod == 0) {
				nextMod = 20 + rand.nextInt(30);
				if (caster == null && casterID != null)
					for (Entity e : world.loadedEntityList)
						if (e instanceof EntityLivingBase && e.getUniqueID().equals(casterID))
							caster = (EntityLivingBase) e;
				final double size = width / 2F;
				List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX - size, posY - 36F, posZ - size, posX + size, posY + 3F, posZ + size));
				list.removeIf(e -> e == caster || !IAoVCapability.selectiveTarget(caster, CapabilityHelper.getCap(caster, CapabilityList.AOV, null), e));
				EntityLivingBase entity = list.isEmpty() ? null : list.size() == 1 ? list.get(0) : list.get(rand.nextInt(list.size()));
				EntitySpellLightningBolt strike = new EntitySpellLightningBolt(world, caster, damage);
				Vec3d vec;
				if (entity != null)
					vec = entity.getPositionVector();
				else
					vec = getNextPos();
				strike.setPosition(vec.x, vec.y, vec.z);
				world.spawnEntity(strike);
			}
		}
		if (ticksExisted % 400 == 0)
			setDead();
	}

	private double getNextCoord() {
		return rand.nextDouble() * width - (width / 2F);
	}

	private Vec3d getNextPos() {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(new BlockPos(getNextCoord() + posX, posY, getNextCoord() + posZ));
		while (pos.getY() > 1 && world.isAirBlock(pos))
			pos.setPos(pos.down());
		return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
		damage = Math.max(1F, compound.getFloat("damage"));
		if (compound.hasUniqueId("casterID"))
			casterID = compound.getUniqueId("casterID");
	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
		if (caster != null)
			compound.setUniqueId("casterID", caster.getUniqueID());
		compound.setFloat("damage", damage);
	}

}
