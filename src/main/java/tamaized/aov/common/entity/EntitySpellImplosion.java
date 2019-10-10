package tamaized.aov.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EntitySpellImplosion extends Entity implements IEntityAdditionalSpawnData {

	private Entity caster;
	private LivingEntity target;
	private int tick = 0;

	public EntitySpellImplosion(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entityspellimplosion.get()), worldIn);
	}

	public EntitySpellImplosion(World world, Entity caster, LivingEntity entity) {
		this(world);
		this.caster = caster;
		target = entity;
		setPositionAndUpdate(target.posX, target.posY, target.posZ);
		tick = rand.nextInt(80);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeInt(target.getEntityId());
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		Entity e = world.getEntityByID(additionalData.readInt());
		if (e instanceof LivingEntity)
			target = (LivingEntity) e;
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

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getBrightnessForRender() {
		return 0xF000F0;
	}

	@Override
	public void tick() {
		if (world.isRemote) {
			if (target != null)
				for (int i = 0; i < 20; i++) {
					Vec3d vec = getLook(1.0F).rotatePitch(rand.nextInt(360)).rotateYaw(rand.nextInt(360));
					float speed = 0.08F;
					Vec3d pos = getPositionVector().add(0, target.getHeight() / 2F, 0).add(vec);
					//					Minecraft.getInstance().particles.addEffect(new ParticleImplosion(world, pos.x, pos.y, pos.z, -vec.x * speed, -vec.y * speed, -vec.z * speed)); TODO
				}
			return;
		}
		if (target == null || !target.isAlive()) {
			remove();
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
			remove();
		}
	}
}