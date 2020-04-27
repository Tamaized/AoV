package tamaized.aov.client.events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.client.ClientHelpers;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Dist.CLIENT)
public class RenderLivingHook {

	@SubscribeEvent
	public static void render(RenderLivingEvent.Pre<? extends LivingEntity, ? extends EntityModel<?>> e) {
		if (e.getEntity() != ClientHelpers.getTarget())
			return;
		RenderSystem.pushMatrix();
		{
			RenderSystem.translated(0, e.getEntity().isSneaking() ? 0.1F : 0F, 0);
			RenderSystem.disableTexture();
			RenderSystem.enableBlend();
			RenderSystem.disableCull();
			RenderSystem.disableLighting();

			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);

			int segs = 50;
			float r = 2F;
			float width = e.getEntity().getWidth();
			drawArc(buffer, segs, r, 1, segs / 2 - 1, 0.4F * width, 0.41F * width);
			RenderSystem.pushMatrix();
			{
				RenderSystem.rotatef(-((e.getEntity().ticksExisted + e.getPartialRenderTick()) % 360) * 16F, 0, 1, 0);
				tessellator.draw();
			}
			RenderSystem.popMatrix();
			buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
			drawArc(buffer, segs, r, 1, segs / 2 - 1, 0.5F * width, 0.51F * width);
			RenderSystem.pushMatrix();
			{
				RenderSystem.rotatef(180 + ((e.getEntity().ticksExisted + e.getPartialRenderTick()) % 360) * 16F, 0, 1, 0);
				tessellator.draw();
			}
			RenderSystem.popMatrix();

			RenderSystem.enableLighting();
			RenderSystem.enableCull();
			RenderSystem.disableBlend();
			RenderSystem.enableTexture();
		}
		RenderSystem.popMatrix();
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
