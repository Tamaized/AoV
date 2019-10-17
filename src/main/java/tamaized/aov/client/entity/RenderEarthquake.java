package tamaized.aov.client.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntityEarthquake;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

public class RenderEarthquake extends EntityRenderer<EntityEarthquake> {

	private static final ResourceLocation SHADOW_TEXTURES = new ResourceLocation("textures/misc/shadow.png");
	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.MODID, "textures/entity/earthquake.png");

	public RenderEarthquake(EntityRendererManager renderManager) {
		super(renderManager);
	}

	// [Vanilla Copy] Render#renderShadow
	private static void renderShadow(EntityRendererManager renderManager, Entity entityIn, double x, double y, double z, float shadowSize, float shadowAlpha, float partialTicks) {
		GlStateManager.enableBlend();
		GlStateManager.color4f(1F, 1F, 1F, 1F);
		renderManager.textureManager.bindTexture(SHADOW_TEXTURES);
		IWorldReader iworldreader = renderManager.world;
		GlStateManager.depthMask(false);
		float f = shadowSize;
		if (entityIn instanceof MobEntity) {
			MobEntity mobentity = (MobEntity) entityIn;
			if (mobentity.isChild()) {
				f *= 0.5F;
			}
		}

		double d5 = MathHelper.lerp(partialTicks, entityIn.lastTickPosX, entityIn.posX);
		double d0 = MathHelper.lerp(partialTicks, entityIn.lastTickPosY, entityIn.posY);
		double d1 = MathHelper.lerp(partialTicks, entityIn.lastTickPosZ, entityIn.posZ);
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

		for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(i, k, i1), new BlockPos(j, l, j1))) {
			BlockPos blockpos1 = blockpos.down();
			BlockState blockstate = iworldreader.getBlockState(blockpos1);
			if (blockstate.getRenderType() != BlockRenderType.INVISIBLE && iworldreader.getLight(blockpos) > 3) {
				func_217759_a(renderManager, blockstate, iworldreader, blockpos1, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
			}
		}

		tessellator.draw();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
	}

	// [Vanilla Copy] Render#renderShadowSingle
	private static void func_217759_a(EntityRendererManager renderManager, BlockState p_217759_1_, IWorldReader p_217759_2_, BlockPos p_217759_3_, double p_217759_4_, double p_217759_6_, double p_217759_8_, BlockPos p_217759_10_, float p_217759_11_, float p_217759_12_, double p_217759_13_, double p_217759_15_, double p_217759_17_) {
		if (p_217759_1_.func_224756_o(p_217759_2_, p_217759_3_)) {
			VoxelShape voxelshape = p_217759_1_.getShape(renderManager.world, p_217759_10_.down());
			if (!voxelshape.isEmpty()) {
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuffer();
				double d0 = ((double) p_217759_11_ - (p_217759_6_ - ((double) p_217759_10_.getY() + p_217759_15_)) / 2.0D) * 0.5D * (double) renderManager.world.getBrightness(p_217759_10_);
				if (!(d0 < 0.0D)) {
					if (d0 > 1.0D) {
						d0 = 1.0D;
					} else if (d0 < 0.5D)
						d0 = 0;

					AxisAlignedBB axisalignedbb = voxelshape.getBoundingBox();
					double d1 = (double) p_217759_10_.getX() + axisalignedbb.minX + p_217759_13_;
					double d2 = (double) p_217759_10_.getX() + axisalignedbb.maxX + p_217759_13_;
					double d3 = (double) p_217759_10_.getY() + axisalignedbb.minY + p_217759_15_ + 0.015625D;
					double d4 = (double) p_217759_10_.getZ() + axisalignedbb.minZ + p_217759_17_;
					double d5 = (double) p_217759_10_.getZ() + axisalignedbb.maxZ + p_217759_17_;
					float f = (float) ((p_217759_4_ - d1) / 2.0D / (double) p_217759_12_ + 0.5D);
					float f1 = (float) ((p_217759_4_ - d2) / 2.0D / (double) p_217759_12_ + 0.5D);
					float f2 = (float) ((p_217759_8_ - d4) / 2.0D / (double) p_217759_12_ + 0.5D);
					float f3 = (float) ((p_217759_8_ - d5) / 2.0D / (double) p_217759_12_ + 0.5D);
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
		GL11.glStencilFunc(GL11.GL_ALWAYS, AoV.config_client.stencil.get() + 6, 0xFF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
		{
			renderShadow(renderManager, entity, x, y, z, 3F, 1F, partialTicks);
		}
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
		GL11.glStencilMask(0x00);
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
	}

	private void renderQuakes(@Nonnull EntityEarthquake entity, double x, double y, double z, float partialTicks) {
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilFunc(GL11.GL_EQUAL, AoV.config_client.stencil.get() + 6, 0xFF);
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
