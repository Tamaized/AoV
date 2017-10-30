package tamaized.aov.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.aov.AoV;
import tamaized.aov.client.handler.ClientTicker;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.proxy.ClientProxy;

public class AoVOverlay extends Gui {

	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final ResourceLocation TEXTURE_ASTRO = new ResourceLocation(AoV.modid, "textures/gui/astro.png");

	@SubscribeEvent
	public void RenderAoVData(RenderGameOverlayEvent.Post e) {
		if (e.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
			return;
		ClientTicker.update();
		IAoVCapability cap = mc.player.getCapability(CapabilityList.AOV, null);
		FontRenderer fontRender = mc.fontRenderer;
		ScaledResolution sr = new ScaledResolution(mc);
		int sW = sr.getScaledWidth() / 2;

		if (cap != null && cap.hasCoreSkill()) {
			if (ClientProxy.barToggle) {
				GlStateManager.pushMatrix();
				{
					if (ConfigHandler.barPos == ConfigHandler.BarPos.BOTTOM)
						GlStateManager.translate(0, sr.getScaledHeight() - 23, 0);
					for (int i = 0; i < 9; i++) {
						int x = sW - 90 + (20 * i);
						int y = ConfigHandler.barPos == ConfigHandler.BarPos.BOTTOM ? 1 - ClientTicker.charges.getValue(i) : 1 + ClientTicker.charges.getValue(i);
						renderCharges(x, y, fontRender, cap, i);
					}
				}
				GlStateManager.popMatrix();
			}

			AoVUIBar.render(this);
			if (cap.getCoreSkill() == AoVSkills.astro_core_1)
				renderAstro(mc.player, sr);
		}
	}

	private void renderCharges(int x, int y, FontRenderer fontRender, IAoVCapability cap, int index) {
		Ability ability = cap.getSlot(index);
		int val = ability == null ? -1 : ability.getCharges();
		if (val < 0)
			return;
		int w = 20;
		int h = 20;
		drawRect(x, y, x + w, y + h, !cap.canUseAbility(ability) ? 0x77FF0000 : 0x7700BBFF);
		drawCenteredStringNoShadow(fontRender, String.valueOf(val), x + 10, y + (ConfigHandler.barPos == ConfigHandler.BarPos.BOTTOM ? 3 : 10), 0x000000);
	}

	private void drawCenteredStringNoShadow(FontRenderer fontRendererIn, String text, int x, int y, int color) {
		fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, false);
	}

	private void renderAstro(EntityPlayer player, ScaledResolution sr) {
		if (!player.hasCapability(CapabilityList.ASTRO, null))
			return;
		IAstroCapability cap = player.getCapability(CapabilityList.ASTRO, null);
		if (cap == null)
			return;
		GlStateManager.pushMatrix();
		{
			GlStateManager.color(1, 1, 1, 1);
			mc.getTextureManager().bindTexture(TEXTURE_ASTRO);
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buffer = tess.getBuffer();
			buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

			float x = sr.getScaledWidth() * 2 / 3;
			float y = sr.getScaledHeight() / 5;

			float scale = 0.35F;
			buffer.pos(x, y + 143F * scale, 0).tex(0, 0.5F).endVertex();
			buffer.pos(x + 235F * scale, y + 143F * scale, 0).tex(1, 0.5F).endVertex();
			buffer.pos(x + 235F * scale, y, 0).tex(1, 0).endVertex();
			buffer.pos(x, y, 0).tex(0, 0).endVertex();

			if (cap.getDraw() != null) {
				renderAstroIcon(IAstroCapability.ICard.getCardID(cap.getDraw()), buffer, x + 33.5F, y + 17F, scale);
				drawCenteredString(mc.fontRenderer, "" + cap.getDrawTime(), (int) (x + 43), (int) (y + 50), 0xbd7e10);
				mc.getTextureManager().bindTexture(TEXTURE_ASTRO);
				GlStateManager.color(1, 1, 1, 1);
			}

			if (cap.getSpread() != null)
				renderAstroIcon(IAstroCapability.ICard.getCardID(cap.getSpread()), buffer, x + 14F, y + 12.5F, scale * 0.8F);

			tess.draw();
		}
		GlStateManager.popMatrix();
	}

	private void renderAstroIcon(int index, BufferBuilder buffer, float x, float y, float scale) {
		scale = scale / 4F;
		float xOffset = 0.25F * (index % 4);
		float yOffset = 0.25F * (float) Math.floor(index / 4);
		buffer.pos(x, y + 286F * scale, 0).tex(xOffset, 0.75F + yOffset).endVertex();
		buffer.pos(x + 235F * scale, y + 286F * scale, 0).tex(0.25F + xOffset, 0.75F + yOffset).endVertex();
		buffer.pos(x + 235F * scale, y, 0).tex(0.25F + xOffset, 0.5F + yOffset).endVertex();
		buffer.pos(x, y, 0).tex(xOffset, 0.5F + yOffset).endVertex();
	}

}
