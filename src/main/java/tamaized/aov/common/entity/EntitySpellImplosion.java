package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.proxy.CommonProxy;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nonnull;

public class EntitySpellImplosion extends Entity {

	private Entity caster;
	private EntityLivingBase target;
	private int tick = 0;

	public EntitySpellImplosion(World worldIn) {
		super(worldIn);
	}

	public EntitySpellImplosion(World world, Entity caster, EntityLivingBase entity) {
		this(world);
		this.caster = caster;
		target = entity;
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
		tick = rand.nextInt(80);
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
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
	}

	@Override
	public void onUpdate() {
		if (world.isRemote) {
			for (int index = 0; index < 10; index++) {
				Vec3d vec = getLook(1.0F).rotatePitch(rand.nextInt(360)).rotateYaw(rand.nextInt(360));
				float speed = 0.08F;
				AoV.proxy.spawnParticle(CommonProxy.ParticleType.Fluff, world, getPositionVector().add(0, 0.65F, 0).add(vec), new Vec3d(-vec.x * speed, -vec.y * speed, -vec.z * speed), 7, 0, rand.nextFloat() * 0.90F + 0.10F, 0x7700FFFF);
			}
			return;
		}
		if (target == null || !target.isEntityAlive()) {
			setDead();
			return;
		}
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
		tick++;
		if (tick % (20 * 5) == 0) {
			float damage = rand.nextFloat() * target.getMaxHealth();
			IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
			if (cap != null)
				damage *= (1f + (cap.getSpellPower() / 100f));
			target.attackEntityFrom(AoVDamageSource.createEntityDamageSource(AoVDamageSource.DESTRUCTION, caster), damage);
			if (cap != null)
				cap.addExp(caster, 25, Abilities.implosion);
			setDead();
		}
	}

}