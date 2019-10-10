package tamaized.aov.common.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.helper.RayTraceHelper;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class EntityGravity extends Entity {

	private final List<Entity> alreadyHit = Lists.newArrayList();
	public float spinnyBoi;
	private Entity caster;
	private float damage = 1;
	private float damageMod = 1F;

	public EntityGravity(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entitygravity.get()), worldIn);
		ignoreFrustumCheck = true;
	}

	public EntityGravity(World world, Entity entity, float dmg, int r) {
		this(world);
		caster = entity;
		damage = dmg;
		if (caster instanceof PlayerEntity) {
			RayTraceResult ray = RayTraceHelper.tracePath(caster, world, (PlayerEntity) caster, r, 1, Sets.newHashSet(caster));
			if (ray != null) {
				BlockPos pos = ray instanceof BlockRayTraceResult ? ((BlockRayTraceResult) ray).getPos() : ray instanceof EntityRayTraceResult ? ((EntityRayTraceResult) ray).getEntity().getPosition() : caster.getPosition();
				setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
			}
		}
	}

	public EntityGravity(World world, Entity entity, float dmg, Vec3d pos) {
		this(world);
		caster = entity;
		damage = dmg;
		setPositionAndUpdate(pos.x, pos.y, pos.z);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
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
		super.tick();
		if (world.isRemote)
			return;
		if (ticksExisted >= 50) {
			remove();
			return;
		}
		int range = 4;
		for (LivingEntity e : world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(getPosition().add(-range, -range, -range), getPosition().add(range, range, range)))) {
			if (e == caster || alreadyHit.contains(e))
				continue;
			doDamage(e);
			alreadyHit.add(e);
		}
	}

	private void doDamage(LivingEntity e) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap != null) {
			if (IAoVCapability.selectiveTarget(caster, cap, e)) {
				e.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, caster), damage * damageMod);
				cap.addExp(caster, 20, Abilities.gravity);
			}
		} else
			e.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, caster), damage * damageMod);
		if (damageMod > 0.5F)
			damageMod -= 0.1F;

	}

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}