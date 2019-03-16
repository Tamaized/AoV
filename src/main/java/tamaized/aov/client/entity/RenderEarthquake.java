package tamaized.aov.client.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.aov.common.entity.EntityEarthquake;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

public class RenderEarthquake extends Render<EntityEarthquake> {

	private static final ResourceLocation SHADOW_TEXTURES = new ResourceLocation("textures/misc/shadow.png");
	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.MODID, "textures/entity/earthquake.png");

	public RenderEarthquake(RenderManager renderManager) {
		super(renderManager);
	}

	// [Vanilla Copy] Render#renderShadow
	private static void renderShadow(RenderManager renderManager, Entity entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
		renderManager.textureManager.bindTexture(SHADOW_TEXTURES);
		World world = renderManager.world;
		GlStateManager.depthMask(false);
		float f = 3F;

		if (entityIn instanceof EntityLiving) {
			EntityLiving entityliving = (EntityLiving) entityIn;
			f *= entityliving.getRenderSizeModifier();

			if (entityliving.isChild()) {
				f *= 0.5F;
			}
		}

		double d5 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double) partialTicks;
		double d0 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double) partialTicks;
		double d1 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double) partialTicks;
		int i = MathHelper.floor(d5 - (double) f);
		int j = MathHelper.floor(d5 + (double) f);
		int k = MathHelper.floor(d0 - (double) f);
		int l = MathHelper.floor(d0);
		int i1 = MathHelper.floor(d1 - (double) f);
		int j1 = MathHelper.floor(d1 + (double) f);
		double d2 = x - d5;
		double d3 = y - d0;
		double d4 = z - d1;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

		for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(i, l, i1), new BlockPos(j, l, j1))) {
			IBlockState iblockstate = world.getBlockState(blockpos.down());

			if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE && world.getLightFromNeighborsFor(EnumLightType.SKY, blockpos) > 3) {
				renderShadowSingle(renderManager, iblockstate, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
			}
		}

		tessellator.draw();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.depthMask(true);
	}

	// [Vanilla Copy] Render#renderShadowSingle
	private static void renderShadowSingle(RenderManager renderManager, IBlockState state, double p_188299_2_, double p_188299_4_, double p_188299_6_, BlockPos p_188299_8_, float p_188299_9_, float p_188299_10_, double p_188299_11_, double p_188299_13_, double p_188299_15_) {
		if (state.isFullCube()) {
			VoxelShape voxelshape = state.getShape(renderManager.world, p_188299_8_.down());
			if (!voxelshape.isEmpty()) {
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuffer();
				double d0 = ((double) p_188299_9_ - (p_188299_4_ - ((double) p_188299_8_.getY() + p_188299_13_)) / 2.0D) * 0.5D * (double) renderManager.world.getBrightness(p_188299_8_);
				if (!(d0 < 0.0D)) {
					if (d0 > 1.0D) {
						d0 = 1.0D;
					}

					AxisAlignedBB axisalignedbb = voxelshape.getBoundingBox();
					double d1 = (double) p_188299_8_.getX() + axisalignedbb.minX + p_188299_11_;
					double d2 = (double) p_188299_8_.getX() + axisalignedbb.maxX + p_188299_11_;
					double d3 = (double) p_188299_8_.getY() + axisalignedbb.minY + p_188299_13_ + 0.015625D;
					double d4 = (double) p_188299_8_.getZ() + axisalignedbb.minZ + p_188299_15_;
					double d5 = (double) p_188299_8_.getZ() + axisalignedbb.maxZ + p_188299_15_;
					float f = (float) ((p_188299_2_ - d1) / 2.0D / (double) p_188299_10_ + 0.5D);
					float f1 = (float) ((p_188299_2_ - d2) / 2.0D / (double) p_188299_10_ + 0.5D);
					float f2 = (float) ((p_188299_6_ - d4) / 2.0D / (double) p_188299_10_ + 0.5D);
					float f3 = (float) ((p_188299_6_ - d5) / 2.0D / (double) p_188299_10_ + 0.5D);
					bufferbuilder.pos(d1, d3, d4).tex((double) f, (double) f2).color(1.0F, 1.0F, 1.0F, (float) d0).endVertex();
					bufferbuilder.pos(d1, d3, d5).tex((double) f, (double) f3).color(1.0F, 1.0F, 1.0F, (float) d0).endVertex();
					bufferbuilder.pos(d2, d3, d5).tex((double) f1, (double) f3).color(1.0F, 1.0F, 1.0F, (float) d0).endVertex();
					bufferbuilder.pos(d2, d3, d4).tex((double) f1, (double) f2).color(1.0F, 1.0F, 1.0F, (float) d0).endVertex();
				}
			}
		}
	}

	@Override
	public void doRender(@Nonnull EntityEarthquake entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		renderMask(entity, x, y, z, partialTicks);
		renderQuakes(entity, x, y, z, partialTicks);
	}

	private void renderMask(@Nonnull EntityEarthquake entity, double x, double y, double z, float partialTicks) {
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ZERO);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);
		GL11.glStencilFunc(GL11.GL_ALWAYS, AoV.config.stencil.get() + 6, 0xFF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
		{
			renderShadow(renderManager, entity, x, y, z, 1F, partialTicks);
		}
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
		GL11.glStencilMask(0x00);
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
	}

	private void renderQuakes(@Nonnull EntityEarthquake entity, double x, double y, double z, float partialTicks) {
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilFunc(GL11.GL_EQUAL, AoV.config.stencil.get() + 6, 0xFF);
		{
			bindTexture(TEXTURE);
			GlStateManager.color4f(1F, 1F, 1F, 1F);
			GlStateManager.enableBlend();
			AtomicReference<Float> offset = new AtomicReference<>(0F);
			entity.quakes.removeIf(q -> q.render(entity, x, y + (offset.updateAndGet(v -> v + 0.0001F)), z, partialTicks));
		}
		GL11.glStencilMask(0x00);
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityEarthquake entity) {
		return TEXTURE;
	}
}
