package tamaized.aov.common.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.abilities.favoredsoul.AlignmentAoE;
import tamaized.tammodized.common.helper.CapabilityHelper;
import tamaized.tammodized.common.helper.RayTraceHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityAlignmentAoE extends Entity {

	public static final DataParameter<Integer> ALIGNMENT_TYPE = EntityDataManager.createKey(EntityAlignmentAoE.class, DataSerializers.VARINT);
	private final List<Entity> alreadyHit = Lists.newArrayList();
	private AlignmentAoE.Type alignment;
	private Entity caster;
	private float damage = 1;
	private float damageMod = 1F;

	public EntityAlignmentAoE(World worldIn) {
		super(worldIn);
		ignoreFrustumCheck = true;
	}

	public EntityAlignmentAoE(World world, AlignmentAoE.Type type, Entity entity, float dmg, int r) {
		this(world);
		setAlignment(alignment = type);
		caster = entity;
		rotationYaw = caster.rotationYaw;
		damage = dmg;
		if (caster instanceof EntityPlayer) {
			RayTraceResult ray = RayTraceHelper.tracePath(world, (EntityPlayer) caster, r, 1, Sets.newHashSet(caster));
			if (ray != null) {
				BlockPos pos = ray.typeOfHit == RayTraceResult.Type.BLOCK ? ray.getBlockPos() : ray.entityHit.getPosition();
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
	protected void entityInit() {
		dataManager.register(ALIGNMENT_TYPE, 0);
	}

	public AlignmentAoE.Type getAlignment() {
		return AlignmentAoE.Type.getTypeFromID(dataManager.get(ALIGNMENT_TYPE));
	}

	public void setAlignment(AlignmentAoE.Type alignment) {
		dataManager.set(ALIGNMENT_TYPE, alignment.ordinal());
	}

	@Override
	protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
		alignment = AlignmentAoE.Type.getTypeFromID(compound.getInteger("alignment"));
		if (alignment != null)
			setAlignment(alignment);
	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
		compound.setInteger("alignment", alignment.ordinal());
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (world.isRemote && getAlignment() == AlignmentAoE.Type.ChaosHammer) {
			if (ticksExisted >= 14) {
				for (int i = 0; i <= 7; i++)
					world.spawnParticle(EnumParticleTypes.CRIT,

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
			setDead();
			return;
		}
		int range = 4;
		for (EntityLivingBase e : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPosition().add(-range, -2, -range), getPosition().add(range, 2, range)))) {
			if (e == caster || alreadyHit.contains(e))
				continue;
			doDamage(e);
			alreadyHit.add(e);
		}
	}

	private void doDamage(EntityLivingBase e) {
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