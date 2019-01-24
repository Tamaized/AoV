package tamaized.aov.client.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntityMalefic;

import javax.annotation.Nonnull;

public class RenderMalefic<T extends EntityMalefic> extends Render<T> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.modid, "textures/entity/malefic.png");

	public RenderMalefic(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(@Nonnull T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		bindEntityTexture(entity);
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.translate(x - 0.5F, y - 0.5F, z - 0.0F);
		//		GlStateManager.rotate(180F - getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();

		for (int i = 0; i < 8; i++) {
			GlStateManager.rotate((45 * i + entity.ticksExisted * 2) % 360, 1, 1, 0);
			//			GlStateManager.translate(-0.25F + ((0.125F * i) % 4), -0.25F + (0.125F * ((i + 2) % 4)), -0.25F + (0.125F * ((i - 2) % 4)));
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
			vertexbuffer.pos(1, 1, 0).tex(1, 1).endVertex();
			vertexbuffer.pos(1, 0, 0).tex(1, 0).endVertex();
			vertexbuffer.pos(0, 0, 0).tex(0, 0).endVertex();
			vertexbuffer.pos(0, 1, 0).tex(0, 1).endVertex();
			tessellator.draw();
		}
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(@Nonnull T entity) {
		return TEXTURE;
	}
}