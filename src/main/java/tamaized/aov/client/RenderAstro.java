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
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.astro.AstroCapabilityHandler;
import tamaized.aov.common.capabilities.astro.IAstroCapability;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Side.CLIENT)
public class RenderAstro {

	private static final ResourceLocation TEXTURE_CARDS = new ResourceLocation(AoV.MODID, "textures/entity/cards.png");
	private static final ResourceLocation TEXTURE_RUNE = new ResourceLocation(AoV.MODID, "textures/entity/rune.png");

	public static float testVarPleaseIgnore = 0F;

	@SubscribeEvent
	public static void render(RenderPlayerEvent.Post e) {
		EntityPlayer player = e.getEntityPlayer();
		if (!player.hasCapability(CapabilityList.ASTRO, null))
			return;

		GlStateManager.pushMatrix();
		GlStateManager.translate(e.getX(), e.getY(), e.getZ());
		IAstroCapability cap = CapabilityList.getCap(player, CapabilityList.ASTRO);
		AstroCapabilityHandler handler = cap instanceof AstroCapabilityHandler ? (AstroCapabilityHandler) cap : null;


		/*GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		GlStateManager.color4f(0, 0.6F, 1, 1.0F);
		GlStateManager.rotate(90F, 1.0F, 0.0F, 0.0F);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_ALPHA);

		e.getRenderer().bindTexture(RenderCombust.RING);
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buf = tess.getBuffer();
		buf.begin(7, DefaultVertexFormats.POSITION_TEX);

		buf.pos(1, 1, 0).tex(1, 1).endVertex();
		buf.pos(1, 0, 0).tex(1, 0).endVertex();
		buf.pos(0, 0, 0).tex(0, 0).endVertex();
		buf.pos(0, 1, 0).tex(0, 1).endVertex();

		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableDepth();
		tess.draw();
		GlStateManager.enableDepth();

		GlStateManager.color4f(1, 1, 1, 1);
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();*/

		if (cap != null) {
			for (IAstroCapability.IAnimation animation : cap.getAnimations())
				if (animation != null)
					switch (animation) {
						case Draw:
							renderDraw(0, e, cap, handler != null ? handler.lastDraw : cap.getDraw());
							break;
						case Spread:
							renderSpread(2, e, cap, handler != null ? handler.lastSpread : cap.getSpread());
							break;
						case Burn:
							renderBurn(1, e, cap);
							break;
						case Activate:
							renderBurn(3, e, cap);
							break;
						case Redraw:
							renderRedraw(4, e, cap);
							break;
						default:
							break;
					}
		}
		GlStateManager.color4f(1, 1, 1, 1);
		GlStateManager.popMatrix();
	}

	@SubscribeEvent
	public static void tick(TickEvent.RenderTickEvent e) { // Lets tick our frame data while in first person
		if (e.phase == TickEvent.Phase.START)
			return;
		Minecraft mc = Minecraft.getInstance();
		if (mc.gameSettings.thirdPersonView != 0 || mc.player == null || !mc.player.hasCapability(CapabilityList.ASTRO, null))
			return;
		IAstroCapability cap = CapabilityList.getCap(mc.player, CapabilityList.ASTRO);
		if (cap == null)
			return;

		// Burn and Activate
		float timer;
		for (int index = 1; index <= 3; index += 2) {
			timer = cap.getFrameData()[index][3];
			cap.getFrameData()[index][1] = Math.max(0, cap.getFrameData()[index][1] - ((240F * (cap.getFrameData()[1][1] / 90F)) / (float) Minecraft.getDebugFPS()));
			if (cap.getFrameData()[index][0] > 0 && !Minecraft.getInstance().isGamePaused() && timer < 20)
				cap.getFrameData()[index][0] = Math.max(0, cap.getFrameData()[index][0] - (240F / (float) Minecraft.getDebugFPS()));
		}
		// Spread
		if (!Minecraft.getInstance().isGamePaused()) {
			cap.getFrameData()[2][4] += (240F / (float) Minecraft.getDebugFPS()) % 360;
			cap.getFrameData()[2][5] += (cap.getFrameData()[2][5] >= 90 && cap.getFrameData()[2][3] > 35 ? 0 : 60F) / (float) Minecraft.getDebugFPS();
		}
		// Draw
		for (int index = 0; index <= 2; index += 2) {
			timer = cap.getFrameData()[index][3];
			if (timer < 60 && !Minecraft.getInstance().isGamePaused() && cap.getFrameData()[index][2] > 0)
				cap.getFrameData()[index][2] = Math.max(0, cap.getFrameData()[index][2] - (240F / (float) Minecraft.getDebugFPS()));
			if (timer < 90 && !Minecraft.getInstance().isGamePaused() && cap.getFrameData()[index][1] > 0)
				cap.getFrameData()[index][1] = Math.max(0, cap.getFrameData()[index][1] - (160F / (float) Minecraft.getDebugFPS()));
			if (cap.getFrameData()[index][0] > 0 && !Minecraft.getInstance().isGamePaused() && timer < 25)
				cap.getFrameData()[index][0] = Math.max(0, cap.getFrameData()[index][0] - (240F / (float) Minecraft.getDebugFPS()));
		}
		// Redraw
		if (!Minecraft.getInstance().isGamePaused() && cap.getFrameData()[4][0] > 0)
			cap.getFrameData()[4][0] -= (240F / (float) Minecraft.getDebugFPS());
		if (!Minecraft.getInstance().isGamePaused() && cap.getFrameData()[4][1] > 0)
			cap.getFrameData()[4][1] -= (120F / (float) Minecraft.getDebugFPS());
		if (!Minecraft.getInstance().isGamePaused() && cap.getFrameData()[4][0] <= 0)
			cap.getFrameData()[4][4] -= (60F / (float) Minecraft.getDebugFPS());
		if (!Minecraft.getInstance().isGamePaused() && cap.getFrameData()[4][4] <= 0)
			cap.getFrameData()[4][2] -= (300F / (float) Minecraft.getDebugFPS());
	}

	private static void renderRedraw(int index, RenderPlayerEvent.Post e, IAstroCapability cap) {
		float timer = cap.getFrameData()[index][3];
		if (timer > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.disableCull();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.rotate(-e.getEntityPlayer().renderYawOffset, 0, 1, 0);

			GlStateManager.color4f(1, 1, 1, 1);
			GlStateManager.translate(0F, 1.5F, 1F);
			if (!Minecraft.getInstance().isGamePaused() && cap.getFrameData()[index][0] > 0)
				cap.getFrameData()[index][0] -= (240F / (float) Minecraft.getDebugFPS());
			GlStateManager.rotate(cap.getFrameData()[index][0] % 360, 0, 0, 1);
			if (!Minecraft.getInstance().isGamePaused() && cap.getFrameData()[index][1] > 0)
				cap.getFrameData()[index][1] -= (120F / (float) Minecraft.getDebugFPS());
			float s = (80F - cap.getFrameData()[index][1]) / 80F;
			GlStateManager.scale(s, s, s);
			float f = 1.5F;//ftimer > 0 ? ((ftimer / 80f) * 1.5F) : 0F;
			float scale = 0.3F;

			e.getRenderer().bindTexture(TEXTURE_CARDS);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder vertexbuffer = tessellator.getBuffer();

			GlStateManager.pushMatrix();
			if (!Minecraft.getInstance().isGamePaused() && cap.getFrameData()[index][0] <= 0)
				cap.getFrameData()[index][4] -= (60F / (float) Minecraft.getDebugFPS());
			if (!Minecraft.getInstance().isGamePaused() && cap.getFrameData()[index][4] <= 0)
				cap.getFrameData()[index][2] -= (300F / (float) Minecraft.getDebugFPS());
			for (int i = 0; i <= 12; i++) {
				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
				if (i > 0)
					GlStateManager.rotate(30, 0, 0, 1);
				float xpos = -0.15F;
				float ypos = -0.435F;
				float uScale = 125F / 12F;
				float u = cap.getFrameData()[index][2] > (i - 1) * uScale ? (Math.max(0, ((i) * uScale) - cap.getFrameData()[index][2])) / uScale : 1F;
				vertexbuffer.pos(xpos + scale, ypos + 1F, 0).tex(0, 0.5).endVertex();
				vertexbuffer.pos(xpos + scale, ypos + 1F + (f * scale), 0).tex(0, 0.5F - (0.5F * (f / 1.5F))).endVertex();
				vertexbuffer.pos(xpos + (scale * u), ypos + 1F + (f * scale), 0).tex(0.25 * (1F - u), 0.5F - (0.5F * (f / 1.5F))).endVertex();
				vertexbuffer.pos(xpos + (scale * u), ypos + 1F, 0).tex(0.25 * (1F - u), 0.5).endVertex();
				tessellator.draw();
			}
			GlStateManager.popMatrix();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GlStateManager.enableCull();
			GlStateManager.popMatrix();
		}
	}

	private static void renderBurn(int index, RenderPlayerEvent.Post e, IAstroCapability cap) {
		float timer = cap.getFrameData()[index][3];
		if (timer > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.disableCull();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.rotate(180F - e.getRenderer().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);

			GlStateManager.translate(-0.125F, 0.5F + MathHelper.cos((float) Math.toRadians(cap.getFrameData()[index][1])), -0.125F);
			cap.getFrameData()[index][1] = Math.max(0, cap.getFrameData()[index][1] - ((240F * (cap.getFrameData()[index][1] / 90F)) / (float) Minecraft.getDebugFPS()));

			float ftimer = cap.getFrameData()[index][0];
			if (cap.getFrameData()[index][0] > 0 && !Minecraft.getInstance().isGamePaused() && timer < 20)
				cap.getFrameData()[index][0] = Math.max(0, cap.getFrameData()[index][0] - (240F / (float) Minecraft.getDebugFPS()));
			float f = ftimer > 0 ? ((ftimer / 80f) * 1.5F) : 0F;
			float scale = 0.25F;

			e.getRenderer().bindTexture(TEXTURE_CARDS);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder vertexbuffer = tessellator.getBuffer();
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);

			vertexbuffer.pos(1F * scale, 1F, 0.0001).tex(0, 0.5).endVertex();
			vertexbuffer.pos(1F * scale, 1F + (f * scale), 0.0001).tex(0, 0.5F - (0.5F * (f / 1.5F))).endVertex();
			vertexbuffer.pos(0, 1F + (f * scale), 0.0001).tex(0.25, 0.5F - (0.5F * (f / 1.5F))).endVertex();
			vertexbuffer.pos(0, 1F, 0.0001).tex(0.25, 0.5).endVertex();

			tessellator.draw();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GlStateManager.enableCull();
			GlStateManager.popMatrix();
		}
	}

	private static void renderDraw(int index, RenderPlayerEvent.Post e, IAstroCapability cap, IAstroCapability.ICard card) {
		float timer = cap.getFrameData()[index][3];
		if (timer > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.disableCull();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.rotate((180F + cap.getFrameData()[index][2] - e.getRenderer().getRenderManager().playerViewY), 0.0F, 1.0F, 0.0F);
			if (timer < 60 && !Minecraft.getInstance().isGamePaused() && cap.getFrameData()[index][2] > 0) {
				cap.getFrameData()[index][2] = Math.max(0, cap.getFrameData()[index][2] - (240F / (float) Minecraft.getDebugFPS()));
			}
			GlStateManager.translate(-0.5F, 1.0F, 0);
			if (timer < 90 && !Minecraft.getInstance().isGamePaused() && cap.getFrameData()[index][1] > 0) {
				cap.getFrameData()[index][1] = Math.max(0, cap.getFrameData()[index][1] - (160F / (float) Minecraft.getDebugFPS()));
			}
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, (80F - cap.getFrameData()[index][1]) / 80F);
			float ftimer = cap.getFrameData()[index][0];
			if (cap.getFrameData()[index][0] > 0 && !Minecraft.getInstance().isGamePaused() && timer < 25)
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
		if (!Minecraft.getInstance().isGamePaused())
			cap.getFrameData()[index][4] += (240F / (float) Minecraft.getDebugFPS()) % 360;
		GlStateManager.rotate(cap.getFrameData()[index][4], 0, 0, 1);
		GlStateManager.translate(-1.45F, -1.55F, 0);
		if (!Minecraft.getInstance().isGamePaused())
			cap.getFrameData()[index][5] += (cap.getFrameData()[index][5] >= 90 && cap.getFrameData()[index][3] > 35 ? 0 : 60F) / (float) Minecraft.getDebugFPS();
		GlStateManager.color4f(1, 1, 1, MathHelper.sin((float) Math.toRadians(Math.min(cap.getFrameData()[index][5], 180))));

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
