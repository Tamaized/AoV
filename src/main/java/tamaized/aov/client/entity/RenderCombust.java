package tamaized.aov.client.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntityCombust;

import javax.annotation.Nonnull;

public class RenderCombust<T extends EntityCombust> extends EntityRenderer<T> {

	public static final ResourceLocation RING = new ResourceLocation(AoV.MODID, "textures/entity/combust_ring.png");
	private static final ResourceLocation BOOM = new ResourceLocation(AoV.MODID, "textures/entity/combust_boom.png");

	public RenderCombust(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(@Nonnull T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.translated(x, y, z);
		GlStateManager.rotatef(180F - getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.translated(-0.5F, 0, 0);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();

		GlStateManager.pushMatrix();
		bindTexture(BOOM);
		float s = entity.scale / 90F;
		GlStateManager.color4f(0, 0, MathHelper.cos((float) Math.toRadians(entity.ticksExisted % 360)), 1F - s);
		GlStateManager.translated(0.5F, 0.5F, 0F);
		s *= 2.5F;
		GlStateManager.scalef(s, s, s);
		GlStateManager.translated(-0.5F, -0.5F, 0F);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(1, 1, 0).tex(1, 1).endVertex();
		vertexbuffer.pos(1, 0, 0).tex(1, 0).endVertex();
		vertexbuffer.pos(0, 0, 0).tex(0, 0).endVertex();
		vertexbuffer.pos(0, 1, 0).tex(0, 1).endVertex();
		tessellator.draw();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		bindTexture(RING);
		float c = MathHelper.abs(MathHelper.cos((float) Math.toRadians(entity.initalScale)));
		GlStateManager.color4f(c, c, 1F, MathHelper.clamp(1F - (entity.ticksExisted / 45F), 0, 1));
		float sc = (1F - c) * 2F;
		GlStateManager.translated(0.5F, 0.5F, 0F);
		GlStateManager.scalef(sc, sc, sc);
		GlStateManager.translated(-0.5F, -0.5F, 0F);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(1, 1, 0).tex(1, 1).endVertex();
		vertexbuffer.pos(1, 0, 0).tex(1, 0).endVertex();
		vertexbuffer.pos(0, 0, 0).tex(0, 0).endVertex();
		vertexbuffer.pos(0, 1, 0).tex(0, 1).endVertex();
		tessellator.draw();
		GlStateManager.popMatrix();

		GlStateManager.color4f(1, 1, 1, 1);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.popMatrix();

		if (!Minecraft.getInstance().isGamePaused() && entity.initalScale < 270F)
			entity.initalScale += (120F / (float) Minecraft.getDebugFPS());
		if (!Minecraft.getInstance().isGamePaused() && entity.scale < 90F)
			entity.scale += (120F / (float) Minecraft.getDebugFPS());
		if (entity.ticksExisted % 40 == 0)
			entity.scale = 0;

	}

	@Override
	protected ResourceLocation getEntityTexture(@Nonnull T entity) {
		return RING;
	}
}