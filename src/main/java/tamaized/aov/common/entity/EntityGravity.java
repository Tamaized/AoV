package tamaized.aov.common.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.tammodized.common.helper.CapabilityHelper;
import tamaized.tammodized.common.helper.RayTraceHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityGravity extends Entity {

	private final List<Entity> alreadyHit = Lists.newArrayList();
	public float spinnyBoi;
	private Entity caster;
	private float damage = 1;
	private float damageMod = 1F;

	public EntityGravity(World worldIn) {
		super(worldIn);
		ignoreFrustumCheck = true;
	}

	public EntityGravity(World world, Entity entity, float dmg, int r) {
		this(world);
		caster = entity;
		damage = dmg;
		if (caster instanceof EntityPlayer) {
			RayTraceResult ray = RayTraceHelper.tracePath(world, (EntityPlayer) caster, r, 1, Sets.newHashSet(caster));
			if (ray != null) {
				BlockPos pos = ray.typeOfHit == RayTraceResult.Type.BLOCK ? ray.getBlockPos() : ray.entityHit.getPosition();
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
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {

	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (world.isRemote)
			return;
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

}