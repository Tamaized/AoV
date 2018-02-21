package tamaized.aov.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.leap.ILeapCapability;

public class RenderPlayer {

	private static final ResourceLocation texture = new ResourceLocation(AoV.modid, "textures/entity/wing.png");

	@SubscribeEvent
	public void render(RenderPlayerEvent.Pre e) {

	}

	@SubscribeEvent
	public void render(RenderPlayerEvent.Post e) {
		EntityPlayer player = e.getEntityPlayer();
		ILeapCapability cap = player.hasCapability(CapabilityList.LEAP, null) ? player.getCapability(CapabilityList.LEAP, null) : null;
		if (cap == null || cap.getLeapDuration() <= 0)
			return;
		GlStateManager.pushMatrix();
		{
			GlStateManager.enableBlend();
			GlStateManager.disableCull();
			float perc = (float) cap.getLeapDuration() / (float) cap.getMaxLeapDuration();
			GlStateManager.color(1, 1, 1, perc);
			float scale = 1;
			GlStateManager.translate(e.getX(), e.getY(), e.getZ());
			GlStateManager.scale(scale, scale, scale);
			GlStateManager.rotate(-player.renderYawOffset, 0, 1, 0);
			if (player.isSneaking()) {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			}
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder vertexbuffer = tess.getBuffer();
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
			double x1 = 0;
			double x2 = x1 + 1;
			double y1 = 1.90;
			double y2 = y1 - 1;
			double z1 = -0.05;
			double z2 = z1 - 0.5;

			vertexbuffer.pos(x1, y1, z1).tex(1, 0).endVertex();
			vertexbuffer.pos(x2, y1, z2).tex(0, 0).endVertex();
			vertexbuffer.pos(x2, y2, z2).tex(0, 1).endVertex();
			vertexbuffer.pos(x1, y2, z1).tex(1, 1).endVertex();

			double offset = -1.0;

			vertexbuffer.pos(x2 + offset, y1, z1).tex(1, 0).endVertex();
			vertexbuffer.pos(x1 + offset, y1, z2).tex(0, 0).endVertex();
			vertexbuffer.pos(x1 + offset, y2, z2).tex(0, 1).endVertex();
			vertexbuffer.pos(x2 + offset, y2, z1).tex(1, 1).endVertex();

			tess.draw();
			GlStateManager.enableCull();
			GlStateManager.disableBlend();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
		GlStateManager.popMatrix();
	}

}
