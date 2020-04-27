package tamaized.aov.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.client.ClientHelpers;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.universal.InvokeMass;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Dist.CLIENT)
public class AoVUIBar {

	public static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
	private static final Minecraft mc = Minecraft.getInstance();
	public static int slotLoc = 0;

	@SubscribeEvent
	public static void disableHotbar(RenderGameOverlayEvent e) {
		if (AoV.config_client.renderBarOverHotbar.get() && e.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && ClientHelpers.barToggle)
			e.setCanceled(true);
	}

	public static void render(int xpos, int ypos) {
		if (AoV.config_client.renderBarOverHotbar.get() && !ClientHelpers.barToggle)
			return;
		if (mc.player == null)
			return;
		IAoVCapability cap = CapabilityList.getCap(mc.player, CapabilityList.AOV);
		if (cap == null)
			return;
		RenderSystem.pushMatrix();
		{
			MainWindow sr = mc.getMainWindow();
			if (AoV.config_client.renderBarOverHotbar.get()) {
				xpos = 0;
				ypos = 0;
				RenderSystem.translated(0, sr.getScaledHeight() - 23, 0);
			}
			float alpha = 0.2f;
			if (ClientHelpers.barToggle)
				alpha = 1.0f;
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
			mc.getTextureManager().bindTexture(widgetsTexPath);
			int i = sr.getScaledWidth() / 2;
			AoVOverlay.drawTexturedModalRect(xpos + i - 91, ypos + 1, 0, 0, 182, 22);
			AoVOverlay.drawTexturedModalRect(xpos + i - 91 - 1 + slotLoc * 20, ypos, 0, 22, 24, 22);
			RenderSystem.pushMatrix();
			{
				RenderSystem.translated(0.01f, 0, 0);
				RenderSystem.translated(-20.01f, 0, 0);
				for (int j = 0; j < 9; ++j) {
					RenderSystem.translated(20.01f, 0, 0);
					Ability ability = cap.getSlot(j);
					if (ability == null)
						continue;
					int k = sr.getScaledWidth() / 2 - 90 + 2;
					int l = 4;
					RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
					renderHotbarIcon(cap, j, xpos + k, ypos + l, ability.getAbility().getIcon(), (ability.getAbility() instanceof InvokeMass) && cap.getInvokeMass());
					if (ability.getCooldown() > 0)
						renderCooldown(mc.fontRenderer, xpos + k, ypos + l, ability.getCooldownPerc(), ability.getCooldown());
				}
			}
			RenderSystem.popMatrix();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		RenderSystem.popMatrix();
	}

	public static void renderHotbarIcon(IAoVCapability cap, int index, int xPos, int yPos, ResourceLocation icon, boolean active) {
		if (icon == null)
			return;
		RenderSystem.pushMatrix();
		{
			float f1 = 1.0F / 16.0F;
			RenderSystem.translated((float) (xPos), (float) (yPos), 0.0F);
			RenderSystem.scalef(1.0F * f1, 1.0f * f1, 1.0F);
			RenderSystem.pushMatrix();
			{
				RenderSystem.enableBlend();
				renderIcon(icon);
				if (active)
					AbstractGui.fill(0, 0, 256, 256, 0x7700FFFF);
				Ability ability = cap == null ? null : cap.getSlot(index);
				if (ability != null && (!ability.canUse(cap) || (ability.isOnCooldown(cap) && !ability.getAbility().canUseOnCooldown(cap, mc.player))))
					AbstractGui.fill(0, 0, 256, 256, 0x77FF0000);
				RenderSystem.disableBlend();
			}
			RenderSystem.popMatrix();

		}
		RenderSystem.popMatrix();
	}

	private static void renderIcon(ResourceLocation icon) {
		Minecraft.getInstance().getTextureManager().bindTexture(icon);
		AoVOverlay.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
	}

	private static void renderCooldown(FontRenderer fr, int xPos, int yPos, float perc, int timeLeft) {
		RenderSystem.pushMatrix();
		{
			float f1 = 1.0F / 16.0F;
			RenderSystem.translated((float) (xPos), (float) (yPos), 0.0F);
			RenderSystem.scalef(1.0F * f1, 1.0f * f1, 1.0F);
			RenderSystem.scalef(16.0f, 16.0f, 1.0f);
			renderRadial(0, 0, perc);
			AoVOverlay.drawCenteredString(fr, String.valueOf(timeLeft), 8, 4, 0xFFFF00);
		}
		RenderSystem.popMatrix();
	}

	public static void renderRadial(int x, int y, float perc) { // TODO: clean up
		RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, false);
		//		GL11.glEnable(GL11.GL_STENCIL_TEST);
		RenderSystem.colorMask(false, false, false, false);
		RenderSystem.depthMask(false);
		//		GL11.glStencilFunc(GL11.GL_NEVER, 13, 0xFF);
		//		GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP);
		//		GL11.glStencilMask(0xFF);

		int r = 10;
		int centerX = x + 8;
		int centerY = y + 8;
		float a = 0.5f;

		RenderSystem.disableLighting();
		RenderSystem.disableTexture();
		RenderSystem.shadeModel(GL11.GL_SMOOTH);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.colorMask(true, true, true, true);
		RenderSystem.depthMask(true);
		//		GL11.glStencilMask(0x00);
		//		GL11.glStencilFunc(GL11.GL_EQUAL, 13, 0xFF);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		RenderSystem.color4f(0F, 0.0F, 0.0F, a);
		GL11.glVertex2i(centerX, centerY);
		RenderSystem.color4f(0F, 0F, 0.0F, a);
		int v = (int) ((405f) * perc);
		int v1 = v > 45 ? -45 : -v;
		int v2 = v > 135 ? -135 : -v;
		int v3 = v > 225 ? -225 : -v;
		int v4 = v > 315 ? -315 : -v;
		int v5 = v > 405 ? -405 : -v;
		double rad = (v1) / 180F * Math.PI;
		GL11.glVertex2d(centerX + Math.cos((-45) / 180F * Math.PI) * r, centerY + Math.sin(rad) * r);
		rad = (v2) / 180F * Math.PI;
		if (v > 45)
			GL11.glVertex2d(centerX + Math.cos(rad) * r, centerY + Math.sin((-135) / 180F * Math.PI) * r);
		rad = (v3) / 180F * Math.PI;
		if (v > 135)
			GL11.glVertex2d(centerX + Math.cos((-225) / 180F * Math.PI) * r, centerY + Math.sin(rad) * r);
		rad = (v4) / 180F * Math.PI;
		if (v > 225)
			GL11.glVertex2d(centerX + Math.cos(rad) * r, centerY + Math.sin((-315) / 180F * Math.PI) * r);
		rad = (v5) / 180F * Math.PI;
		if (v > 315)
			GL11.glVertex2d(centerX + Math.cos((-405) / 180F * Math.PI) * r, centerY + Math.sin(rad) * r);

		GL11.glVertex2i(centerX, centerY);
		GL11.glEnd();
		RenderSystem.disableBlend();
		RenderSystem.enableTexture();
		RenderSystem.shadeModel(GL11.GL_FLAT);
		//		GL11.glDisable(GL11.GL_STENCIL_TEST);
	}
}
