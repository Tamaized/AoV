package tamaized.aov.client.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntityCelestialOpposition;

import javax.annotation.Nonnull;

public class RenderCelestialOpposition<T extends EntityCelestialOpposition> extends Render<T> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.modid, "textures/entity/celestialopposition.png");

	public RenderCelestialOpposition(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(@Nonnull T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		GlStateManager.color(0, 0.6F, 1, 1.0F);
		GlStateManager.translate(x, y, z);
		float scale = (entity.tickBoi += Minecraft.getMinecraft().isGamePaused() ? 0 : ((entity.tickBoi < 280F ? 360F : 30F) / (float) Minecraft.getDebugFPS())) / 80F;
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.rotate(90F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate((entity.tickBoi * 0.25F) % 360, 0, 0, 1);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_ALPHA);

		bindTexture(TEXTURE);
		RenderGravity.SPHERE.setTextureFlag(true);
		RenderGravity.renderSphere(1F);

		bindTexture(RenderCombust.RING);
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buf = tess.getBuffer();
		GlStateManager.scale(3.0F, 3.0F, 3.0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.05F);
		buf.begin(7, DefaultVertexFormats.POSITION_TEX);

		buf.pos(1, 1, 0).tex(1, 1).endVertex();
		buf.pos(1, 0, 0).tex(1, 0).endVertex();
		buf.pos(0, 0, 0).tex(0, 0).endVertex();
		buf.pos(0, 1, 0).tex(0, 1).endVertex();

		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(0, 0.6F, 1, 1F - entity.ticksExisted / 100F);
		GlStateManager.disableDepth();
		tess.draw();
		GlStateManager.enableDepth();

		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(@Nonnull T entity) {
		return TEXTURE;
	}
}