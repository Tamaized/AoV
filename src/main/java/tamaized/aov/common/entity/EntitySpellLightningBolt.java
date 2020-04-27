package tamaized.aov.common.entity;

import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class EntitySpellLightningBolt extends Entity {

	public long boltVertex = this.rand.nextLong();
	private int lightningState = 2;
	private int boltLivingTime = this.rand.nextInt(3) + 1;
	private LivingEntity caster;
	private float damage;
	private AbilityBase source;

	public EntitySpellLightningBolt(World world) {
		super(Objects.requireNonNull(AoVEntities.entityspelllightningbolt.get()), world);
		damage = 3F;
	}

	public EntitySpellLightningBolt(World world, LivingEntity caster, float damage, AbilityBase source) {
		this(world);
		this.caster = caster;
		this.damage = damage;
		this.source = source;
	}

	@Override
	protected void registerData() {

	}

	@Nonnull
	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.WEATHER;
	}

	@Override
	public void tick() {
		super.tick();

		if (this.lightningState == 2) {
			this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
			this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.WEATHER, 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
		}

		--this.lightningState;

		if (this.lightningState < 0) {
			if (this.boltLivingTime == 0) {
				this.remove();
			} else if (this.lightningState < -this.rand.nextInt(10)) {
				--this.boltLivingTime;
				this.lightningState = 1;

				if (!this.world.isRemote) {
					this.boltVertex = this.rand.nextLong();
					BlockPos blockpos = new BlockPos(this);

					if (this.world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK) && this.world.isAreaLoaded(blockpos, 10) && this.world.getBlockState(blockpos).getMaterial() == Material.AIR && Blocks.FIRE.getDefaultState().isValidPosition(this.world, blockpos)) {
						this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
					}
				}
			}
		}

		if (this.lightningState >= 0) {
			List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.getPosX() - 3.0D, this.getPosY() - 3.0D, this.getPosZ() - 3.0D, this.getPosX() + 3.0D, this.getPosY() + 6.0D + 3.0D, this.getPosZ() + 3.0D));

			IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
			for (Entity entity : list)
				if (!(entity instanceof LivingEntity) || IAoVCapability.selectiveTarget(caster, cap, (LivingEntity) entity)) {
					if (entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, damage) && cap != null)
						cap.addExp(caster, 20, source == null ? Abilities.litStrike : source);
				}
		}
	}

	@Override
	protected void readAdditional(@Nonnull CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(@Nonnull CompoundNBT compound) {

	}

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
