package tamaized.aov.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.astro.AstroCapabilityHandler;
import tamaized.aov.common.capabilities.astro.IAstroCapability;

@Mod.EventBusSubscriber(modid = AoV.modid, value = Side.CLIENT)
public class RenderAstro {

	private static final ResourceLocation TEXTURE_CARDS = new ResourceLocation(AoV.modid, "textures/entity/cards.png");
	private static final ResourceLocation TEXTURE_RUNE = new ResourceLocation(AoV.modid, "textures/entity/rune.png");

	@SubscribeEvent
	public static void render(RenderPlayerEvent.Post e) {
		EntityPlayer player = e.getEntityPlayer();
		if (!player.hasCapability(CapabilityList.ASTRO, null))
			return;

		GlStateManager.pushMatrix();
		IAstroCapability cap = player.getCapability(CapabilityList.ASTRO, null);
		AstroCapabilityHandler handler = cap instanceof AstroCapabilityHandler ? (AstroCapabilityHandler) cap : null;

		if (cap != null) {
			for (IAstroCapability.IAnimation animation : cap.getAnimations())
				if (animation != null)
					switch (animation) {
						case Draw:
							renderDraw(0, e, cap, handler != null ? handler.lastDraw : cap.getDraw());
							break;
						case Burn:
							renderBurn(1, e);
							break;
						case Spread:
							renderSpread(2, e, cap, handler != null ? handler.lastSpread : cap.getSpread());
							break;
						default:
							break;
					}
		}
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.popMatrix();
	}

	private static void renderBurn(int index, RenderPlayerEvent.Post e) {

	}

	private static void renderDraw(int index, RenderPlayerEvent.Post e, IAstroCapability cap, IAstroCapability.ICard card) {
		float timer = cap.getFrameData()[index][3];
		if (timer > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.disableCull();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.rotate((180F + cap.getFrameData()[index][2] - e.getRenderer().getRenderManager().playerViewY), 0.0F, 1.0F, 0.0F);
			if (timer < 60 && !Minecraft.getMinecraft().isGamePaused() && cap.getFrameData()[index][2] > 0) {
				cap.getFrameData()[index][2] = Math.max(0, cap.getFrameData()[index][2] - (240F / (float) Minecraft.getDebugFPS()));
			}
			GlStateManager.translate(-0.5F, 1.0F, 0.0F);
			if (timer < 90 && !Minecraft.getMinecraft().isGamePaused() && cap.getFrameData()[index][1] > 0) {
				cap.getFrameData()[index][1] = Math.max(0, cap.getFrameData()[index][1] - (160F / (float) Minecraft.getDebugFPS()));
			}
			GlStateManager.color(1.0F, 1.0F, 1.0F, (80F - cap.getFrameData()[index][1]) / 80F);
			float ftimer = cap.getFrameData()[index][0];
			if (cap.getFrameData()[index][0] > 0 && !Minecraft.getMinecraft().isGamePaused() && timer < 25)
				cap.getFrameData()[index][0] = Math.max(0, cap.getFrameData()[index][0] - (240F / (float) Minecraft.getDebugFPS()));
			float f = ftimer > 0 ? ((ftimer / 80f) * 1.5F) : 0F;

			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder vertexbuffer = tessellator.getBuffer();
			e.getRenderer().bindTexture(TEXTURE_CARDS);
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);

			vertexbuffer.pos(1, 1, -0.0001).tex(0, 0.5).endVertex();
			vertexbuffer.pos(1, 1 + f, -0.0001).tex(0, 0.5F - (0.5F * (f / 1.5F))).endVertex();
			vertexbuffer.pos(0, 1 + f, -0.0001).tex(0.25, 0.5F - (0.5F * (f / 1.5F))).endVertex();
			vertexbuffer.pos(0, 1, -0.0001).tex(0.25, 0.5).endVertex();

			int cardID = IAstroCapability.ICard.getCardID(card) + 1;

			double uv = 0.5F * Math.floor(cardID / 4);
			vertexbuffer.pos(1, 1, 0).tex((0.25 * cardID % 4), 0.5F + uv).endVertex();
			vertexbuffer.pos(1, 1 + f, 0).tex((0.25 * cardID % 4), (0.5F - (0.5F * (f / 1.5F))) + uv).endVertex();
			vertexbuffer.pos(0, 1 + f, 0).tex(0.25 + (0.25 * cardID % 4), (0.5F - (0.5F * (f / 1.5F))) + uv).endVertex();
			vertexbuffer.pos(0, 1, 0).tex(0.25 + (0.25 * cardID % 4), 0.5F + uv).endVertex();

			tessellator.draw();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GlStateManager.enableCull();
			GlStateManager.popMatrix();
		}
	}

	private static void renderSpread(int index, RenderPlayerEvent.Post e, IAstroCapability cap, IAstroCapability.ICard card) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GlStateManager.rotate((90F - e.getRenderer().getRenderManager().playerViewY), 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.2F, 2.2F, 0.05F);
		GlStateManager.rotate(25, 0, 0, 1);
		GlStateManager.rotate(60, 1, 0, 0);
		if (!Minecraft.getMinecraft().isGamePaused())
			cap.getFrameData()[index][4] += (240F / (float) Minecraft.getDebugFPS()) % 360;
		GlStateManager.rotate(cap.getFrameData()[index][4], 0, 0, 1);
		GlStateManager.translate(-1.45F, -1.55F, 0);
		if (!Minecraft.getMinecraft().isGamePaused())
			cap.getFrameData()[index][5] += (cap.getFrameData()[index][5] >= 90 && cap.getFrameData()[index][3] > 35 ? 0 : 60F) / (float) Minecraft.getDebugFPS();
		GlStateManager.color(1, 1, 1, MathHelper.sin((float) Math.toRadians(Math.min(cap.getFrameData()[index][5], 180))));

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		e.getRenderer().bindTexture(TEXTURE_RUNE);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);

		vertexbuffer.pos(3, 3, 0).tex(0, 0).endVertex();
		vertexbuffer.pos(3, 0, 0).tex(0, 1).endVertex();
		vertexbuffer.pos(0, 0, 0).tex(1, 1).endVertex();
		vertexbuffer.pos(0, 3, 0).tex(1, 0).endVertex();

		tessellator.draw();
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		renderDraw(index, e, cap, card);
	}

}
