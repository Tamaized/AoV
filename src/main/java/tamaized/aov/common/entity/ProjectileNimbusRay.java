package tamaized.aov.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.client.particle.ParticleColorSpark;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.AoVEntities;

import java.util.Objects;

public class ProjectileNimbusRay extends ProjectileBase {

	public ProjectileNimbusRay(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.projectilenimbusray.get()), worldIn);
		setDamageRangeSpeed(2.0F, 0, 0.0F);
	}

	public ProjectileNimbusRay(World world, PlayerEntity shooter, double x, double y, double z) {
		super(Objects.requireNonNull(AoVEntities.projectilenimbusray.get()), world, shooter, x, y, z);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
	}

	@Override
	protected DamageSource getDamageSource() {
		return AoVDamageSource.createEntityDamageSource(AoVDamageSource.NIMBUS, shootingEntity);
	}

	@Override
	protected float getDamageAmp(double damage, Entity shooter, Entity target) {
		return (float) (damage * (target instanceof MonsterEntity && ((MonsterEntity) target).isEntityUndead() ? 2 : 1));
	}

	@Override
	protected void arrowHit(LivingEntity entity) {
		if (shootingEntity != null) {
			IAoVCapability cap = CapabilityList.getCap(shootingEntity, CapabilityList.AOV);
			if (cap != null)
				cap.addExp(shootingEntity, 20, getSpell());
		}
	}

	@Override
	protected void blockHit(BlockState state, BlockPos pos) {

	}

	@Override
	protected void postTick() {
		if (world.isRemote) {
			Vec3d vec = getLook(1.0F);
			if (!Minecraft.getInstance().isGamePaused())
				for (int i = 0; i < 2; i++)
					Minecraft.getInstance().particles.addEffect(ParticleColorSpark.makeSpark(

							world,

							posX,

							posY,

							posZ,

							-((0.015 * vec.x) + ((rand.nextFloat() * 0.125) - 0.0625)),

							((0.015 * vec.y) + ((rand.nextFloat() * 0.125) - 0.0625)),

							-((0.015 * vec.z) + ((rand.nextFloat() * 0.125) - 0.0625)),

							Minecraft.getInstance().particles,

							getColor() >> 8

					));
		}
	}
}
