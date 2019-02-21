package tamaized.aov.client.events;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.proxy.ClientProxy;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Side.CLIENT)
public class RenderLivingHook {

	@SubscribeEvent
	public static void render(RenderLivingEvent.Pre<? extends EntityLivingBase> e) {
		if (e.getEntity() != ClientProxy.getTarget())
			return;
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(e.getX(), e.getY() + (e.getEntity().isSneaking() ? 0.1F : 0F), e.getZ());
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			GlStateManager.disableCull();
			GlStateManager.disableLighting();

			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);

			int segs = 50;
			float r = 2F;
			float width = e.getEntity().width;
			drawArc(buffer, segs, r, 1, segs / 2 - 1, 0.4F * width, 0.41F * width);
			GlStateManager.pushMatrix();
			{
				GlStateManager.rotate(-((e.getEntity().ticksExisted + e.getPartialRenderTick()) % 360) * 16F, 0, 1, 0);
				tessellator.draw();
			}
			GlStateManager.popMatrix();
			buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
			drawArc(buffer, segs, r, 1, segs / 2 - 1, 0.5F * width, 0.51F * width);
			GlStateManager.pushMatrix();
			{
				GlStateManager.rotate(180 + ((e.getEntity().ticksExisted + e.getPartialRenderTick()) % 360) * 16F, 0, 1, 0);
				tessellator.draw();
			}
			GlStateManager.popMatrix();

			GlStateManager.enableLighting();
			GlStateManager.enableCull();
			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
		}
		GlStateManager.popMatrix();
	}

	private static void drawArc(BufferBuilder buffer, int segs, float radius, int start, int end, float inner, float outer) {
		float c = 1F;
		float iter = (end - start) / 2F;
		for (int ii = start; ii <= end; ++ii) {
			float theta = (float) (2.0f * Math.PI * ((float) ii / (float) segs));
			float x = radius * MathHelper.cos(theta);
			float y = radius * MathHelper.sin(theta);
			buffer.pos(inner * x, 0.04F, inner * y).color(1F, 1F, 1F, 1F).endVertex();
			if (ii - start < iter)
				c -= 1F / iter;
			else
				c += 1F / iter;
			buffer.pos(outer * x, 0.04F, outer * y).color(1F, 1F, c <= 0F ? 0F : c, 1F).endVertex();

		}
	}

}
