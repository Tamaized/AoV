package tamaized.aov.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntityCelestialOpposition;

import javax.annotation.Nonnull;

public class RenderCelestialOpposition<T extends EntityCelestialOpposition> extends EntityRenderer<T> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.MODID, "textures/entity/celestialopposition.png");

	public RenderCelestialOpposition(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		RenderSystem.pushMatrix();
		RenderSystem.disableLighting();
		RenderSystem.enableBlend();
		RenderSystem.disableCull();
		RenderSystem.color4f(0, 0.6F, 1, 1.0F);
		RenderSystem.translated(x, y, z);
		float scale = (entity.tickBoi += Minecraft.getInstance().isGamePaused() ? 0 : ((entity.tickBoi < 280F ? 360F : 30F) / (float) Minecraft.getDebugFPS())) / 80F;
		RenderSystem.scalef(scale, scale, scale);
		RenderSystem.rotatef(90F, 1.0F, 0.0F, 0.0F);
		RenderSystem.rotatef((entity.tickBoi * 0.25F) % 360, 0, 0, 1);
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_ALPHA);

		bindTexture(TEXTURE);
		//		RenderGravity.SPHERE.setTextureFlag(true); TODO
		RenderGravity.renderSphere(1F);

		bindTexture(RenderCombust.RING);
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buf = tess.getBuffer();
		RenderSystem.scalef(3.0F, 3.0F, 3.0F);
		RenderSystem.translated(-0.5F, -0.5F, -0.05F);
		buf.begin(7, DefaultVertexFormats.POSITION_TEX);

		buf.pos(1, 1, 0).tex(1, 1).endVertex();
		buf.pos(1, 0, 0).tex(1, 0).endVertex();
		buf.pos(0, 0, 0).tex(0, 0).endVertex();
		buf.pos(0, 1, 0).tex(0, 1).endVertex();

		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.color4f(0, 0.6F, 1, 1F - entity.ticksExisted / 100F);
		RenderSystem.disableDepthTest();
		tess.draw();
		RenderSystem.enableDepthTest();

		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableCull();
		RenderSystem.disableBlend();
		RenderSystem.enableLighting();
		RenderSystem.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(@Nonnull T entity) {
		return TEXTURE;
	}
}