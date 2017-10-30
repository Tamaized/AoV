package tamaized.aov.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.astro.IAstroCapability;

@Mod.EventBusSubscriber(modid = AoV.modid, value = Side.CLIENT)
public class RenderAstro {

	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.modid, "textures/entity/cards.png");

	@SubscribeEvent
	public static void render(RenderPlayerEvent.Post e) {
		EntityPlayer player = e.getEntityPlayer();
		if (!player.hasCapability(CapabilityList.ASTRO, null))
			return;
		Minecraft mc = Minecraft.getMinecraft();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();

		GlStateManager.pushMatrix();
		IAstroCapability cap = player.getCapability(CapabilityList.ASTRO, null);
		if (cap != null) {
			IAstroCapability.IAnimation animation = cap.getAnimation();
			if (animation != null)
				switch (animation) {
					case Draw:
						float timer = cap.getFrameData()[3];
						if (timer > 0) {
							GlStateManager.disableCull();
							GlStateManager.disableLighting();
							GlStateManager.enableBlend();
							GlStateManager.rotate((180F + cap.getFrameData()[2] - e.getRenderer().getRenderManager().playerViewY), 0.0F, 1.0F, 0.0F);
							if (timer < 160 && !Minecraft.getMinecraft().isGamePaused() && cap.getFrameData()[2] > 0) {
								cap.getFrameData()[2] = Math.max(0, cap.getFrameData()[2] - (240F / (float) Minecraft.getDebugFPS()));
							}
							GlStateManager.translate(-0.5F, 1.0F, 0.0F);
							if (timer < 190 && cap.getFrameData()[1] > 0) {
								cap.getFrameData()[1] = Math.max(0, cap.getFrameData()[1] - (160F / (float) Minecraft.getDebugFPS()));
							}
							GlStateManager.color(1.0F, 1.0F, 1.0F, (80F - cap.getFrameData()[1]) / 80F);
							float ftimer = cap.getFrameData()[0];
							if (cap.getFrameData()[0] > 0 && timer < 125)
								cap.getFrameData()[0] = Math.max(0, cap.getFrameData()[0] - (240F / (float) Minecraft.getDebugFPS()));
							float f = ftimer > 0 ? ((ftimer / 80f) * 1.5F) : 0F;
							e.getRenderer().bindTexture(TEXTURE);
							vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);

							vertexbuffer.pos(1, 1, -0.0001).tex(0, 0.5).endVertex();
							vertexbuffer.pos(1, 1 + f, -0.0001).tex(0, 0.5F - (0.5F * (f / 1.5F))).endVertex();
							vertexbuffer.pos(0, 1 + f, -0.0001).tex(0.25, 0.5F - (0.5F * (f / 1.5F))).endVertex();
							vertexbuffer.pos(0, 1, -0.0001).tex(0.25, 0.5).endVertex();

							int cardID = IAstroCapability.ICard.getCardID(cap.getDraw()) + 1;

							double uv = 0.5F * Math.floor(cardID / 4);
							vertexbuffer.pos(1, 1, 0).tex((0.25 * cardID % 4), 0.5F + uv).endVertex();
							vertexbuffer.pos(1, 1 + f, 0).tex((0.25 * cardID % 4), (0.5F - (0.5F * (f / 1.5F))) + uv).endVertex();
							vertexbuffer.pos(0, 1 + f, 0).tex(0.25 + (0.25 * cardID % 4), (0.5F - (0.5F * (f / 1.5F))) + uv).endVertex();
							vertexbuffer.pos(0, 1, 0).tex(0.25 + (0.25 * cardID % 4), 0.5F + uv).endVertex();

							tessellator.draw();
							GlStateManager.disableBlend();
							GlStateManager.enableLighting();
							GlStateManager.enableCull();
						}
						break;
					default:
						break;
				}
		}
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.popMatrix();
	}
}
