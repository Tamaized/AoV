package tamaized.aov.client.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntityAlignmentAoE;

import javax.annotation.Nonnull;
import java.util.Random;

public class RenderAlignmentAoE<T extends EntityAlignmentAoE> extends EntityRenderer<T> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.MODID, "textures/entity/chaoshammer.png");
	private static final ModelChaosHammer MODEL = new ModelChaosHammer();
	private static final Random rand = new Random();

	public RenderAlignmentAoE(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(@Nonnull T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		final float ticks = entity.ticksExisted + partialTicks;
		switch (entity.getAlignment()) {
			case OrdersWrath: {
				final int alpha = MathHelper.clamp((int) (((entity.ticksExisted > 25 ? 50 - ticks : ticks) / 25F) * 0xFF), 0x0, 0xFF);
				final int color = 0xFFFFFF00 | alpha;
				final float scale = 0.2F;
				GlStateManager.disableTexture2D();
				GlStateManager.disableLighting();
				GlStateManager.enableBlend();
				OpenGlHelper.glMultiTexCoord2f(OpenGlHelper.GL_TEXTURE1, 200, 200);
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				for (int tx = -4; tx <= 4; tx += 4)
					for (int tz = -4; tz <= 4; tz += 4)
						RenderGravity.drawBoltSegment(Tessellator.getInstance(), new Vec3d(x + tx, y - 2, z + tz), new Vec3d(x + tx, y + 6, z + tz), scale, color);
				for (int i = -2; i <= 6; i += 4) {
					RenderGravity.drawBoltSegment(Tessellator.getInstance(), new Vec3d(x - 4, y + i, z - 4), new Vec3d(x + 4, y + i, z - 4), scale, color);
					RenderGravity.drawBoltSegment(Tessellator.getInstance(), new Vec3d(x + 4, y + i, z - 4), new Vec3d(x + 4, y + i, z + 4), scale, color);
					RenderGravity.drawBoltSegment(Tessellator.getInstance(), new Vec3d(x + 4, y + i, z + 4), new Vec3d(x - 4, y + i, z + 4), scale, color);
					RenderGravity.drawBoltSegment(Tessellator.getInstance(), new Vec3d(x - 4, y + i, z + 4), new Vec3d(x - 4, y + i, z - 4), scale, color);
					RenderGravity.drawBoltSegment(Tessellator.getInstance(), new Vec3d(x - 4, y + i, z), new Vec3d(x + 4, y + i, z), scale, color);
					RenderGravity.drawBoltSegment(Tessellator.getInstance(), new Vec3d(x, y + i, z - 4), new Vec3d(x, y + i, z + 4), scale, color);
				}
				GlStateManager.disableBlend();
				GlStateManager.enableLighting();
				GlStateManager.enableTexture2D();
			}
			break;
			case ChaosHammer: {
				GlStateManager.pushMatrix();
				GlStateManager.translated(x, y, z);
				GlStateManager.translated(0.5F, 2.1F, -1.8F);
				final float translate = 2.3F;
				GlStateManager.translated(0, 0, translate);
				GlStateManager.rotatef(-entityYaw, 0, 1, 0);
				{
					float t = ticks >= 10F ? ticks - 10F : 0F;
					float f = 1F - MathHelper.clamp(t / 5F, 0F, 1F);
					GlStateManager.rotatef(-45F * f, 1, 0, 0);
					GlStateManager.translated(0, f * 3F, 0);
				}
				GlStateManager.translated(0, 0, -translate);
				final float scale = 0.1F;
				GlStateManager.scalef(scale, scale, scale);
				bindEntityTexture(entity);
				{
					float alpha;
					if (ticks <= 15F) {
						alpha = ticks / 15F * 0.85F;
					} else {
						alpha = (60F - ticks) / 45F * 0.85F;
					}
					GlStateManager.color4f(0.2F, 0.3F, 0.1F, alpha);
					GlStateManager.disableLighting();
					GlStateManager.enableBlend();
					GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
					GlStateManager.alphaFunc(GL11.GL_GREATER, 0F);
					MODEL.render(entity, 0, 0, entity.ticksExisted, 0, 0, 1F);
					GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
					GlStateManager.disableBlend();
					GlStateManager.enableLighting();
					GlStateManager.color4f(1F, 1F, 1F, 1F);
				}
				GlStateManager.popMatrix();
			}
			break;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(@Nonnull T entity) {
		return TEXTURE;
	}
}