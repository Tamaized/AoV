package tamaized.aov.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntityAlignmentAoE;

import javax.annotation.Nonnull;
import java.util.Random;

public class RenderAlignmentAoE<T extends EntityAlignmentAoE> extends EntityRenderer<T> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.MODID, "textures/entity/chaoshammer.png");
	private static final ModelChaosHammer MODEL = new ModelChaosHammer(RenderType::getEntityTranslucent);
	private static final Random rand = new Random();

	public RenderAlignmentAoE(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		/*final float ticks = entity.ticksExisted + Minecraft.getInstance().getRenderPartialTicks(); TODO
		switch (entity.getAlignment()) {
			case OrdersWrath: {
				final int alpha = MathHelper.clamp((int) (((entity.ticksExisted > 25 ? 50 - ticks : ticks) / 25F) * 0xFF), 0x0, 0xFF);
				final int color = 0xFFFFFF00 | alpha;
				final float scale = 0.2F;
				RenderSystem.disableTexture();
				RenderSystem.disableLighting();
				RenderSystem.enableBlend();
				RenderSystem.glMultiTexCoord2f(GL13.GL_TEXTURE1, 200, 200);
				RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
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
				RenderSystem.disableBlend();
				RenderSystem.enableLighting();
				RenderSystem.enableTexture();
			}
			break;
			case ChaosHammer: {
				RenderSystem.pushMatrix();
				RenderSystem.translated(x, y, z);
				RenderSystem.translated(0.5F, 2.1F, -1.8F);
				final float translate = 2.3F;
				RenderSystem.translated(0, 0, translate);
				RenderSystem.rotatef(-entityYaw, 0, 1, 0);
				{
					float t = ticks >= 10F ? ticks - 10F : 0F;
					float f = 1F - MathHelper.clamp(t / 5F, 0F, 1F);
					RenderSystem.rotatef(-45F * f, 1, 0, 0);
					RenderSystem.translated(0, f * 3F, 0);
				}
				RenderSystem.translated(0, 0, -translate);
				final float scale = 0.1F;
				RenderSystem.scalef(scale, scale, scale);
				bindEntityTexture(entity);
				{
					float alpha;
					if (ticks <= 15F) {
						alpha = ticks / 15F * 0.85F;
					} else {
						alpha = (60F - ticks) / 45F * 0.85F;
					}
					RenderSystem.color4f(0.2F, 0.3F, 0.1F, alpha);
					RenderSystem.disableLighting();
					RenderSystem.enableBlend();
					RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
					RenderSystem.alphaFunc(GL11.GL_GREATER, 0F);
					MODEL.render(entity, 0, 0, entity.ticksExisted, 0, 0, 1F);
					RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
					RenderSystem.disableBlend();
					RenderSystem.enableLighting();
					RenderSystem.color4f(1F, 1F, 1F, 1F);
				}
				RenderSystem.popMatrix();
			}
			break;
		}*/
	}

	@Nonnull
	@Override
	public ResourceLocation getEntityTexture(@Nonnull T entity) {
		return TEXTURE;
	}
}