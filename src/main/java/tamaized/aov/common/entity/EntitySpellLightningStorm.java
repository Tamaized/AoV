package tamaized.aov.common.entity;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EntitySpellLightningStorm extends Entity {

	public final List<Cloud> clouds = Lists.newArrayList();
	private float damage = 1F;
	private EntityLivingBase caster;
	private UUID casterID;
	private int nextMod = 30;

	public EntitySpellLightningStorm(World worldIn) {
		super(worldIn);
		setSize(12F, 0.1F);
		ignoreFrustumCheck = true;
	}

	public EntitySpellLightningStorm(World worldIn, EntityLivingBase caster, float damage) {
		this(worldIn);
		this.caster = caster;
		this.damage = damage;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!world.isRemote) {
			if (ticksExisted % nextMod == 0) {
				nextMod = 20 + rand.nextInt(30);
				if (caster == null && casterID != null)
					for (Entity e : world.loadedEntityList)
						if (e instanceof EntityLivingBase && e.getUniqueID().equals(casterID))
							caster = (EntityLivingBase) e;
				final double size = width / 2F;
				List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX - size, posY - 36F, posZ - size, posX + size, posY + 3F, posZ + size));
				list.removeIf(e -> e == caster || !IAoVCapability.selectiveTarget(caster, CapabilityHelper.getCap(caster, CapabilityList.AOV, null), e));
				EntityLivingBase entity = list.isEmpty() ? null : list.size() == 1 ? list.get(0) : list.get(rand.nextInt(list.size()));
				EntitySpellLightningBolt strike = new EntitySpellLightningBolt(world, caster, damage);
				Vec3d vec;
				if (entity != null)
					vec = entity.getPositionVector();
				else
					vec = getNextPos();
				strike.setPosition(vec.x, vec.y, vec.z);
				world.spawnEntity(strike);
			}
		}
		if (ticksExisted >= 400)
			setDead();
	}

	private double getNextCoord() {
		return rand.nextDouble() * width - (width / 2F);
	}

	private Vec3d getNextPos() {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(new BlockPos(getNextCoord() + posX, posY, getNextCoord() + posZ));
		while (pos.getY() > 1 && world.isAirBlock(pos))
			pos.setPos(pos.down());
		return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
		damage = Math.max(1F, compound.getFloat("damage"));
		if (compound.hasUniqueId("casterID"))
			casterID = compound.getUniqueId("casterID");
	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
		if (caster != null)
			compound.setUniqueId("casterID", caster.getUniqueID());
		compound.setFloat("damage", damage);
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

		@SideOnly(Side.CLIENT)
		public boolean render(@Nonnull EntitySpellLightningStorm entity, double ox, double oy, double oz, float partialTicks, RenderManager renderManager) {
			if (!Minecraft.getMinecraft().isGamePaused()) {
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

				GlStateManager.translate(ox, oy, oz);
				GlStateManager.rotate(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate((float) (renderManager.options.thirdPersonView == 2 ? -1 : 1) * -renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(rot, 0F, 0F, 1F);
				GlStateManager.translate(-ox, -oy, -oz);

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

}
