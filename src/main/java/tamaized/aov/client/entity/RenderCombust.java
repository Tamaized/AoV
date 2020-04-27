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
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		RenderSystem.pushMatrix();
		RenderSystem.disableCull();
		RenderSystem.disableLighting();
		RenderSystem.enableBlend();
		RenderSystem.translated(x, y, z);
		RenderSystem.rotatef(180F - getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		RenderSystem.translated(-0.5F, 0, 0);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();

		RenderSystem.pushMatrix();
		bindTexture(BOOM);
		float s = entity.scale / 90F;
		RenderSystem.color4f(0, 0, MathHelper.cos((float) Math.toRadians(entity.ticksExisted % 360)), 1F - s);
		RenderSystem.translated(0.5F, 0.5F, 0F);
		s *= 2.5F;
		RenderSystem.scalef(s, s, s);
		RenderSystem.translated(-0.5F, -0.5F, 0F);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(1, 1, 0).tex(1, 1).endVertex();
		vertexbuffer.pos(1, 0, 0).tex(1, 0).endVertex();
		vertexbuffer.pos(0, 0, 0).tex(0, 0).endVertex();
		vertexbuffer.pos(0, 1, 0).tex(0, 1).endVertex();
		tessellator.draw();
		RenderSystem.popMatrix();

		RenderSystem.pushMatrix();
		bindTexture(RING);
		float c = MathHelper.abs(MathHelper.cos((float) Math.toRadians(entity.initalScale)));
		RenderSystem.color4f(c, c, 1F, MathHelper.clamp(1F - (entity.ticksExisted / 45F), 0, 1));
		float sc = (1F - c) * 2F;
		RenderSystem.translated(0.5F, 0.5F, 0F);
		RenderSystem.scalef(sc, sc, sc);
		RenderSystem.translated(-0.5F, -0.5F, 0F);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(1, 1, 0).tex(1, 1).endVertex();
		vertexbuffer.pos(1, 0, 0).tex(1, 0).endVertex();
		vertexbuffer.pos(0, 0, 0).tex(0, 0).endVertex();
		vertexbuffer.pos(0, 1, 0).tex(0, 1).endVertex();
		tessellator.draw();
		RenderSystem.popMatrix();

		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.disableBlend();
		RenderSystem.enableLighting();
		RenderSystem.enableCull();
		RenderSystem.popMatrix();

		if (!Minecraft.getInstance().isGamePaused() && entity.initalScale < 270F)
			entity.initalScale += (120F / (float) Minecraft.getDebugFPS());
		if (!Minecraft.getInstance().isGamePaused() && entity.scale < 90F)
			entity.scale += (120F / (float) Minecraft.getDebugFPS());
		if (entity.ticksExisted % 40 == 0)
			entity.scale = 0;

	}

	@Nonnull
	@Override
	public ResourceLocation getEntityTexture(@Nonnull T entity) {
		return RING;
	}
}