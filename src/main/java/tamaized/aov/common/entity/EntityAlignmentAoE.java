package tamaized.aov.common.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
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
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.abilities.favoredsoul.AlignmentAoE;
import tamaized.aov.common.helper.RayTraceHelper;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class EntityAlignmentAoE extends Entity {

	public static final DataParameter<Integer> ALIGNMENT_TYPE = EntityDataManager.createKey(EntityAlignmentAoE.class, DataSerializers.VARINT);
	private final List<Entity> alreadyHit = Lists.newArrayList();
	private AlignmentAoE.Type alignment;
	private Entity caster;
	private float damage = 1;
	private float damageMod = 1F;

	public EntityAlignmentAoE(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entityalignmentaoe), worldIn);
		ignoreFrustumCheck = true;
	}

	public EntityAlignmentAoE(World world, AlignmentAoE.Type type, Entity entity, float dmg, int r) {
		this(world);
		setAlignment(alignment = type);
		caster = entity;
		rotationYaw = caster.rotationYaw;
		damage = dmg;
		if (caster instanceof PlayerEntity) {
			RayTraceResult ray = RayTraceHelper.tracePath(caster, world, (PlayerEntity) caster, r, 1, Sets.newHashSet(caster));
			if (ray != null) {
				BlockPos pos = ray instanceof BlockRayTraceResult ? ((BlockRayTraceResult) ray).getPos() : ray instanceof EntityRayTraceResult ? ((EntityRayTraceResult) ray).getEntity().getPosition() : caster.getPosition();
				setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
			}
		}
	}

	public EntityAlignmentAoE(World world, AlignmentAoE.Type type, Entity entity, float dmg, Vec3d pos) {
		this(world);
		setAlignment(alignment = type);
		caster = entity;
		rotationYaw = caster.rotationYaw;
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
		dataManager.register(ALIGNMENT_TYPE, 0);
	}

	public AlignmentAoE.Type getAlignment() {
		return AlignmentAoE.Type.getTypeFromID(dataManager.get(ALIGNMENT_TYPE));
	}

	public void setAlignment(AlignmentAoE.Type alignment) {
		dataManager.set(ALIGNMENT_TYPE, alignment.ordinal());
	}

	@Override
	protected void readAdditional(@Nonnull CompoundNBT compound) {
		alignment = AlignmentAoE.Type.getTypeFromID(compound.getInt("alignment"));
		if (alignment != null)
			setAlignment(alignment);
	}

	@Override
	protected void writeAdditional(@Nonnull CompoundNBT compound) {
		compound.putInt("alignment", alignment.ordinal());
	}

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void tick() {
		super.tick();
		if (world.isRemote && getAlignment() == AlignmentAoE.Type.ChaosHammer) {
			if (ticksExisted >= 14) {
				for (int i = 0; i <= 7; i++)
					world.addParticle(ParticleTypes.CRIT,

							posX + 0.5F,

							posY + 1F,

							posZ + 0.5F,


							rand.nextFloat() * 6F - 3.0F,

							rand.nextFloat(),

							rand.nextFloat() * 6F - 3.0F);
			}
			return;
		}
		if (ticksExisted >= 50) {
			remove();
			return;
		}
		int range = 4;
		for (LivingEntity e : world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(getPosition().add(-range, -2, -range), getPosition().add(range, 2, range)))) {
			if (e == caster || alreadyHit.contains(e))
				continue;
			doDamage(e);
			alreadyHit.add(e);
		}
	}

	private void doDamage(LivingEntity e) {
		boolean canDamage = false;
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap != null) {
			if (IAoVCapability.selectiveTarget(caster, cap, e)) {
				canDamage = true;
				cap.addExp(caster, 20, Abilities.gravity);
			}
		} else
			canDamage = true;
		if (canDamage) {
			e.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, caster), damage * damageMod);
			IStunCapability stun = CapabilityList.getCap(e, CapabilityList.STUN);
			if (stun != null) {
				if ((e.isEntityUndead() && alignment == AlignmentAoE.Type.OrdersWrath) || (!e.isEntityUndead() && alignment == AlignmentAoE.Type.ChaosHammer))
					stun.setStunTicks(rand.nextInt(20 * 3) + 20 * 2);
			}
			if (damageMod > 0.5F)
				damageMod -= 0.1F;
		}
	}

}