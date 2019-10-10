package tamaized.aov.common.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombiePigmanEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.registry.AoVEntities;

import java.util.Objects;

public class EntityDruidicWolf extends WolfEntity {

	private float damage = 1F;
	private int life;

	public EntityDruidicWolf(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entitydruidicwolf.get()), worldIn);
	}

	public EntityDruidicWolf(World worldIn, PlayerEntity caster, float dmg) {
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
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, 10, false, false, e -> !(e instanceof ZombiePigmanEntity)));
	}

	@Override
	public void livingTick() {
		super.livingTick();
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
				world.addParticle(ParticleTypes.END_ROD, posX, posY + getHeight() / 2F, posZ, result.x, result.y, result.z);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		IAoVCapability cap = CapabilityList.getCap(getOwner(), CapabilityList.AOV);
		if (cap != null)
			cap.addExp(getOwner(), 20, Abilities.formPack);
		return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
	}

	@Override
	public boolean processInteract(PlayerEntity player, Hand hand) {
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
	public WolfEntity createChild(AgeableEntity ageable) {
		return null;
	}

	@Override
	public boolean canMateWith(AnimalEntity otherAnimal) {
		return false;
	}

	@Override
	public boolean isBegging() {
		return false;
	}
}
