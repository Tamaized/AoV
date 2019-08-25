package tamaized.aov.common.entity;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.network.SpawnEntityPacket;
import tamaized.aov.registry.AoVEntities;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class EntitySpellLightningStorm extends Entity {

	public final List<Cloud> clouds = Lists.newArrayList();
	private float damage = 1F;
	private LivingEntity caster;
	private UUID casterID;
	private int nextMod = 30;

	public EntitySpellLightningStorm(World worldIn) {
		super(Objects.requireNonNull(AoVEntities.entityspelllightningstorm), worldIn);
		ignoreFrustumCheck = true;
	}

	public EntitySpellLightningStorm(World worldIn, LivingEntity caster, float damage) {
		this(worldIn);
		this.caster = caster;
		this.damage = damage;
	}

	@Override
	public void tick() {
		super.tick();
		if (!world.isRemote) {
			if (ticksExisted % nextMod == 0) {
				nextMod = 20 + rand.nextInt(30);
				if (caster == null && casterID != null && world instanceof ServerWorld)
					for (Entity e : ((ServerWorld) world).getEntities().collect(Collectors.toList()))
						if (e instanceof LivingEntity && e.getUniqueID().equals(casterID))
							caster = (LivingEntity) e;
				final double size = getWidth() / 2F;
				List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(posX - size, posY - 36F, posZ - size, posX + size, posY + 3F, posZ + size));
				list.removeIf(e -> e == caster || !IAoVCapability.selectiveTarget(caster, CapabilityList.getCap(caster, CapabilityList.AOV, null), e));
				LivingEntity entity = list.isEmpty() ? null : list.size() == 1 ? list.get(0) : list.get(rand.nextInt(list.size()));
				EntitySpellLightningBolt strike = new EntitySpellLightningBolt(world, caster, damage, Abilities.lightningStorm);
				Vec3d vec;
				if (entity != null)
					vec = entity.getPositionVector();
				else
					vec = getNextPos();
				strike.setPosition(vec.x, vec.y, vec.z);
				world.addEntity(strike);
			}
		}
		if (ticksExisted >= 400)
			remove();
	}

	private double getNextCoord() {
		return rand.nextDouble() * getWidth() - (getWidth() / 2F);
	}

	private Vec3d getNextPos() {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(new BlockPos(getNextCoord() + posX, posY, getNextCoord() + posZ));
		while (pos.getY() > 1 && world.isAirBlock(pos))
			pos.setPos(pos.down());
		return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	protected void registerData() {

	}

	@Override
	protected void readAdditional(@Nonnull CompoundNBT compound) {
		damage = Math.max(1F, compound.getFloat("damage"));
		if (compound.hasUniqueId("casterID"))
			casterID = compound.getUniqueId("casterID");
	}

	@Override
	protected void writeAdditional(@Nonnull CompoundNBT compound) {
		if (caster != null)
			compound.putUniqueId("casterID", caster.getUniqueID());
		compound.putFloat("damage", damage);
	}

	public static class Cloud {

		static final Random RAND = new Random();
		public Vec3d offset = new Vec3d(getNextOffset(), getNextOffset(), getNextOffset());
		float life = RAND.nextInt(20 * 4) + 20 * 3;
		float maxLife = life;
		float width = 10F + (RAND.nextFloat() * 5F - 2.5F);
		float height = 5F + (RAND.nextFloat() * 2.5F - 1.25F);
		float rot = (RAND.nextFloat() * 45F - 22.5F) + (RAND.nextBoolean() ? 180 : 0);
		float alpha = 0F;

		static double getNextOffset() {
			return RAND.nextDouble() * 5D - 2.5D;
		}

		@OnlyIn(Dist.CLIENT)
		public boolean render(@Nonnull EntitySpellLightningStorm entity, double ox, double oy, double oz, float partialTicks, EntityRendererManager renderManager) {
			if (!Minecraft.getInstance().isGamePaused()) {
				life -= partialTicks;
				alpha += (life < maxLife / 2F ? -1 : 1) * partialTicks * (1F / (maxLife / 2F));
				alpha = MathHelper.clamp(alpha, 0, 1);
			}
			GlStateManager.pushMatrix();
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
			{

				double x = ox - width / 2F + offset.x;
				double y = oy - height / 2F + offset.y;
				double z = oz + offset.z;

				GlStateManager.translated(ox, oy, oz);
				GlStateManager.rotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotatef((float) (renderManager.options.thirdPersonView == 2 ? -1 : 1) * -renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotatef(rot, 0F, 0F, 1F);
				GlStateManager.translated(-ox, -oy, -oz);

				buffer.pos(x, y, z).tex(0, 0).color(0.45F, 0.45F, 0.45F, alpha).normal(0F, 1F, 0F).endVertex();
				buffer.pos(x + width, y, z).tex(1, 0).color(0.45F, 0.45F, 0.45F, alpha).normal(0F, 1F, 0F).endVertex();
				buffer.pos(x + width, y + height, z).tex(1, 1).color(0.05F, 0.15F, 0.05F, alpha).normal(0F, 1F, 0F).endVertex();
				buffer.pos(x, y + height, z).tex(0, 1).color(0.05F, 0.15F, 0.05F, alpha).normal(0F, 1F, 0F).endVertex();

			}
			tessellator.draw();
			GlStateManager.popMatrix();
			return life <= 0;
		}

	}

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return new SpawnEntityPacket(this);
	}

}
