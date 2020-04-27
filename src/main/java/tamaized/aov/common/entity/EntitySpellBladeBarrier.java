package tamaized.aov.common.entity;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class EntitySpellBladeBarrier extends Entity implements IEntityAdditionalSpawnData {

	private Entity caster;
	private int life = 20 * 30;
	private int tick = life + 1;

	private float damage = 1;
	private int range = 10;

	private List<LivingEntity> entityList = Lists.newArrayList();

	public EntitySpellBladeBarrier(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entityspellbladebarrier.get()), worldIn);
		ignoreFrustumCheck = true;
	}

	public EntitySpellBladeBarrier(World world, Entity entity, float dmg, int r) {
		this(world);
		caster = entity;
		damage = dmg;
		range = r;
		setPositionAndUpdate(caster.getPosX(), caster.getPosY(), caster.getPosZ());
		tick = 0;
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeInt(tick);
		buffer.writeInt(range);
	}

	@Override
	public void readSpawnData(PacketBuffer data) {
		tick = data.readInt();
		range = data.readInt();
	}

	public int getRange() {
		return range;
	}

	@Override
	protected void registerData() {

	}

	@Override
	protected void readAdditional(@Nonnull CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(@Nonnull CompoundNBT compound) {

	}

	@Override
	public void tick() {
		if (world.isRemote)
			return;
		if (caster == null || !caster.isAlive() || tick >= life) {
			remove();
			return;
		}
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(caster, new AxisAlignedBB(getPosition().add(-range, -2, -range), getPosition().add(range, 2, range)));
		Iterator<LivingEntity> iter = entityList.iterator();
		while (iter.hasNext()) {
			LivingEntity e = iter.next();
			if (!list.contains(e)) {
				doDamage(e);
				iter.remove();
			}
		}
		for (Entity e : list) {
			if (e instanceof LivingEntity && !entityList.contains(e)) {
				doDamage((LivingEntity) e);
				entityList.add((LivingEntity) e);
			}
		}
		tick++;
	}

	private void doDamage(LivingEntity e) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap != null) {
			if (IAoVCapability.selectiveTarget(caster, cap, e)) {
				e.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, caster), damage);
				cap.addExp(caster, 20, Abilities.bladeBarrier);
			}
		} else
			e.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, caster), damage);
	}

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}