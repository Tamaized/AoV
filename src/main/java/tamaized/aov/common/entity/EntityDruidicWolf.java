package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class EntityDruidicWolf extends EntityWolf {

	private float damage = 1F;
	private int life;

	public EntityDruidicWolf(World worldIn) {
		super(worldIn);
	}

	public EntityDruidicWolf(World worldIn, EntityPlayer caster, float dmg) {
		this(worldIn);
		setTamedBy(caster);
		damage = dmg;
		extendLife();
		heal(getMaxHealth());
	}

	public void extendLife() {
		life = (20 * 80) + rand.nextInt(20 * 20);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(10, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 10, false, false, e -> !(e instanceof EntityPigZombie)));
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!world.isRemote) {
			if (--life <= 0) {
				setHealth(0F);
			}
		}
	}

	@Override
	protected void onDeathUpdate() {
		super.onDeathUpdate();
		if (world.isRemote) {
			for (int index = 0; index < 4; index++) {
				Vec3d result = getLook(1F).rotateYaw(rand.nextFloat() * 360F).rotatePitch(rand.nextFloat() * 360F).scale(0.08F);//.add(getPositionVector());
				world.spawnParticle(EnumParticleTypes.END_ROD, posX, posY + height / 2F, posZ, result.x, result.y, result.z);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		IAoVCapability cap = CapabilityHelper.getCap(getOwner(), CapabilityList.AOV, null);
		if (cap != null)
			cap.addExp(getOwner(), 20, Abilities.formPack);
		return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		return false;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 0;
	}

	@Override
	public boolean isAngry() {
		return true;
	}

	@Override
	public EntityWolf createChild(EntityAgeable ageable) {
		return null;
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		return false;
	}

	@Override
	public boolean isBegging() {
		return false;
	}
}
