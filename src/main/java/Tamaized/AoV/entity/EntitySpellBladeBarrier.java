package Tamaized.AoV.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.core.abilities.AbilityBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntitySpellBladeBarrier extends Entity implements IEntityAdditionalSpawnData {

	private Entity caster;
	private int life = 20 * 30;
	private int tick = life + 1;

	private float damage = 1;
	private int range = 10;

	private List<EntityLivingBase> entityList = new ArrayList<EntityLivingBase>();

	public EntitySpellBladeBarrier(World worldIn) {
		super(worldIn);
		ignoreFrustumCheck = true;
	}

	public EntitySpellBladeBarrier(World world, Entity entity, float dmg, int r) {
		this(world);
		caster = entity;
		damage = dmg;
		range = r;
		setPositionAndUpdate(caster.posX, caster.posY, caster.posZ);
		tick = 0;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(tick);
		buffer.writeInt(range);
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		tick = data.readInt();
		range = data.readInt();
	}

	public int getRange() {
		return range;
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
		if (world.isRemote) return;
		if (caster == null || !caster.isEntityAlive() || tick >= life) {
			setDead();
			return;
		}
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(caster, new AxisAlignedBB(getPosition().add(-range, -2, -range), getPosition().add(range, 2, range)));
		Iterator<EntityLivingBase> iter = entityList.iterator();
		while (iter.hasNext()) {
			EntityLivingBase e = iter.next();
			if (!list.contains(e)) {
				doDamage(e);
				iter.remove();
			}
		}
		for (Entity e : list) {
			if (e instanceof EntityLivingBase && !entityList.contains(e)) {
				doDamage((EntityLivingBase) e);
				entityList.add((EntityLivingBase) e);
			}
		}
		tick++;
	}

	private void doDamage(EntityLivingBase e) {
		e.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, caster), damage);
		if (caster.hasCapability(CapabilityList.AOV, null)) {
			caster.getCapability(CapabilityList.AOV, null).addExp(20, AbilityBase.bladeBarrier);
		}
	}

}