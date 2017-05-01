package Tamaized.AoV.client;

import org.lwjgl.opengl.GL11;

import Tamaized.AoV.AoV;
import Tamaized.TamModized.particles.ParticleHelper;
import Tamaized.TamModized.particles.FX.ParticleFluff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderPlayer {

	private static final ResourceLocation texture = new ResourceLocation(AoV.modid, "textures/entity/wing.png");

	@SubscribeEvent
	public void render(RenderPlayerEvent.Pre e) {

	}

	@SubscribeEvent
	public void render(RenderPlayerEvent.Post e) {
		if(true) return; // This is dev stuff
		EntityPlayer player = e.getEntityPlayer();
		PotionEffect pot = player.getActivePotionEffect(AoV.potions.slowFall);
//		if (pot == null) return;
		GlStateManager.pushMatrix();
		{
			// GlStateManager.disableAlpha();
			// GlStateManager.disableDepth();
			// GlStateManager.depthMask(false);
			GlStateManager.enableBlend();
			// GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.disableCull();
//			float perc = ((float) pot.getDuration()) / (15.0F * 20.0F);
			float perc = 1.0F;
			GlStateManager.color(1, 1, 1, perc);
			float scale = 1;
			GlStateManager.scale(scale, scale, scale);
			GlStateManager.rotate(-player.renderYawOffset, 0, 1, 0);
			Vec3d pos = player.getPositionVector();
			// GlStateManager.translate(-1, -2.0, -0.25);
			if (player.isSneaking()) {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			}
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			Tessellator tess = Tessellator.getInstance();
			VertexBuffer vertexbuffer = tess.getBuffer();
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
			// GlStateManager.depthMask(true);
			// GlStateManager.enableDepth();
			// GlStateManager.enableAlpha();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			if (player.world != null && player.world.rand.nextInt(100 - ((int) (perc * 100)) + 1) == 0) {
				double yaw = Math.toRadians(player.renderYawOffset - 50);
				ParticleHelper.spawnParticle(new ParticleFluff(player.world, pos.addVector((player.world.rand.nextDouble() - 0.5D) -Math.cos(yaw), y2, -Math.sin(yaw)), new Vec3d(0, 0, 0), player.world.rand.nextInt(20 * 2) + 2, 0.1F, 1.0F, 0xFFFF00FF));
			}
		}
		GlStateManager.popMatrix();
	}

}
