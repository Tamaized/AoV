package Tamaized.AoV.entity.projectile.caster.render;

import Tamaized.AoV.AoV;
import Tamaized.AoV.entity.projectile.caster.ProjectileFlameStrike;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class RenderFlameStrike<T extends ProjectileFlameStrike> extends Render<T> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.modid, "textures/entity/ray.png");

	private static final Random rand = new Random();

	public RenderFlameStrike(RenderManager renderManager) {
		super(renderManager);
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.bindEntityTexture(entity);
		int color = 0xFF4801FF;
		float red = ((color >> 24) & 0xFF) / 255F;
		float green = ((color >> 16) & 0xFF) / 255F;
		float blue = ((color >> 8) & 0xFF) / 255F;
		float alpha = ((color) & 0xFF) / 255F;
		GlStateManager.color(red, green, blue, alpha);
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		int i = 0;
		float f = 0.0F;
		float f1 = 0.5F;
		float f2 = 0.0F;
		float f3 = 0.15625F;
		float f4 = 0.0F;
		float f5 = 0.15625F;
		float f6 = 0.15625F;
		float f7 = 0.3125F;
		float f8 = 0.05625F;
		GlStateManager.enableRescaleNormal();

		GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(0.05625F, 0.05625F, 0.05625F);
		GlStateManager.translate(-4.0F, 0.0F, 0.0F);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		GlStateManager.glNormal3f(0.05625F, 0.0F, 0.0F);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(-7.0D, -2.0D, -2.0D).tex(0.0D, 0.15625D).endVertex();
		vertexbuffer.pos(-7.0D, -2.0D, 2.0D).tex(0.15625D, 0.15625D).endVertex();
		vertexbuffer.pos(-7.0D, 2.0D, 2.0D).tex(0.15625D, 0.3125D).endVertex();
		vertexbuffer.pos(-7.0D, 2.0D, -2.0D).tex(0.0D, 0.3125D).endVertex();
		tessellator.draw();
		GlStateManager.glNormal3f(-0.05625F, 0.0F, 0.0F);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(-7.0D, 2.0D, -2.0D).tex(0.0D, 0.15625D).endVertex();
		vertexbuffer.pos(-7.0D, 2.0D, 2.0D).tex(0.15625D, 0.15625D).endVertex();
		vertexbuffer.pos(-7.0D, -2.0D, 2.0D).tex(0.15625D, 0.3125D).endVertex();
		vertexbuffer.pos(-7.0D, -2.0D, -2.0D).tex(0.0D, 0.3125D).endVertex();
		tessellator.draw();

		for (int j = 0; j < 4; ++j) {
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
			vertexbuffer.pos(-8.0D, -2.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
			vertexbuffer.pos(8.0D, -2.0D, 0.0D).tex(0.5D, 0.0D).endVertex();
			vertexbuffer.pos(8.0D, 2.0D, 0.0D).tex(0.5D, 0.15625D).endVertex();
			vertexbuffer.pos(-8.0D, 2.0D, 0.0D).tex(0.0D, 0.15625D).endVertex();
			tessellator.draw();
		}

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		Vec3d vec = entity.getLook(1.0F);
		for (int index = 0; index < 20; index++)
			net.minecraft.client.Minecraft.getMinecraft().effectRenderer.addEffect(new Tamaized.TamModized.particles.FX.ParticleFluff(entity.world, entity.getPositionVector(), new Vec3d(-((0.015 * vec.xCoord) + ((rand.nextFloat() * 0.5) - 0.25)), ((0.015 * vec.yCoord) + ((rand.nextFloat() * 0.5) - 0.25)), -((0.015 * vec.zCoord) + ((rand.nextFloat() * 0.5) - 0.25))), rand.nextInt(10) + 10, 0, (rand.nextFloat() * 0.85F) + 2.15F, color));

	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return TEXTURE;
	}
}