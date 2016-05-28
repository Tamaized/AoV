package Tamaized.AoV.gui.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import Tamaized.AoV.AoV;
import Tamaized.AoV.common.client.ClientProxy;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.universal.InvokeMass;

public class AoVUIBar {
	
    public static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
    private Minecraft mc;
    public int slotLoc = 0;
	
	public AoVUIBar(){
		mc = Minecraft.getMinecraft();
	}
	
	public void render(Gui gui, float partialTicks){
		AoVData data = AoV.clientAoVCore.getPlayer(null);
		ScaledResolution sr = new ScaledResolution(mc);
		float alpha = 0.2f;
		if(ClientProxy.barToggle) alpha = 1.0f;
		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
		mc.getTextureManager().bindTexture(widgetsTexPath);
		EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
		int i = sr.getScaledWidth() / 2;
		gui.drawTexturedModalRect(i - 91, 1, 0, 0, 182, 22);
		gui.drawTexturedModalRect(i - 91 - 1 + slotLoc * 20, 0, 0, 22, 24, 22);
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.01f, 0, 0);
		GlStateManager.translate(-20.01f, 0, 0);
		for (int j = 0; j < 9; ++j){
			GlStateManager.translate(20.01f, 0, 0);
			if(data.getSlot(j) == null) continue;
			AbilityBase ab = data.getSlot(j);
			int k = sr.getScaledWidth() / 2 - 90 + 2;
			int l = 4;//sr.getScaledHeight() - 16 - 3;
			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);	
			renderHotbarIcon(gui, data, j, k, l, partialTicks, ab.getIcon(), (data.getSlot(j) instanceof InvokeMass) ? data.invokeMass : false);
			GlStateManager.pushAttrib();
			if(data.getCoolDown(ab) > 0) renderCoolDown(gui, mc.fontRendererObj, k, l, (((float)data.getCoolDown(ab))/ab.getCoolDown()), data.getCoolDown(ab));
			GlStateManager.popAttrib();
		}
		//renderRadial(0, 50, 0.90f);
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
	}
	
	public static void renderHotbarIcon(Gui gui, AoVData data, int index, int xPos, int yPos, float partialTicks, ResourceLocation icon, boolean active){
		if(icon != null){
			GlStateManager.pushMatrix();
			float f1 = 1.0F / 16.0F;
			GlStateManager.translate((float)(xPos), (float)(yPos), 0.0F);
			GlStateManager.scale(1.0F * f1, 1.0f * f1, 1.0F);
			//GlStateManager.translate((float)(-(xPos + 8)), (float)(-(yPos + 12)), 0.0F);
			
			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();
			GlStateManager.enableAlpha();
			GlStateManager.alphaFunc(516, 0.1F);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(770, 771);
			
			renderIcon(gui, icon);
			if(active) gui.drawRect(0, 0, 256, 256, 0x7700FFFF);
			if(data != null){
				AbilityBase ab = data.getSlot(index);
				if(data.getAbilityCharge(ab) < ab.getTrueCost(data)) gui.drawRect(0, 0, 256, 256, 0x77FF0000);
			}
			GlStateManager.disableAlpha();
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableLighting();
			GlStateManager.popMatrix();
			
			GlStateManager.popMatrix();
			//gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, ""+spell, 200, 100, 0xFFFFFFFF);
		}
	}
	
	private static void renderIcon(Gui gui, ResourceLocation icon){
		Minecraft.getMinecraft().getTextureManager().bindTexture(icon);
		gui.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
	}
	
	private static void renderCoolDown(Gui gui, FontRenderer fr, int xPos, int yPos, float perc, int timeLeft){
		GlStateManager.pushMatrix();{
			float f1 = 1.0F / 16.0F;
			GlStateManager.translate((float)(xPos), (float)(yPos), 0.0F);
			GlStateManager.scale(1.0F * f1, 1.0f * f1, 1.0F);
			
			GlStateManager.pushMatrix();{
				GlStateManager.enableRescaleNormal();
				GlStateManager.enableAlpha();
				GlStateManager.alphaFunc(516, 0.1F);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(770, 771);
				
				{
					GlStateManager.scale(16.0f, 16.0f, 1.0f);
					renderRadial(0, 0, perc);
					gui.drawCenteredString(fr, String.valueOf(timeLeft), 8, 4, 0xFFFF00);
				}
				
				GlStateManager.disableAlpha();
				GlStateManager.disableRescaleNormal();
				GlStateManager.disableLighting();
			}
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
	}
	
	/**
	 * Credit to Vazkii here, I used code from Botania :P
	 */
	public static void renderRadial(int x, int y, float perc) {
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glColorMask(false, false, false, false);
		GL11.glDepthMask(false);
		GL11.glStencilFunc(GL11.GL_NEVER, 1, 0xFF);
		GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP);
		GL11.glStencilMask(0xFF);
		
		int r = 10;
		int centerX = x + 8;
		int centerY = y + 8;
		int degs = (int) (360 * perc);
		float a = 0.5f;	
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColorMask(true, true, true, true);
		GL11.glDepthMask(true);
		GL11.glStencilMask(0x00);
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glColor4f(0F, 0.0F, 0.0F, a);
		GL11.glVertex2i(centerX, centerY);
		GL11.glColor4f(0F, 0F, 0.0F, a);
		int v = (int)((405f)*perc);
		//v = 210;
		int v1 = v > 45 ? -45 : -v;
		int v2 = v > 135 ? -135 : -v;
		int v3 = v > 225 ? -225 : -v;
		int v4 = v > 315 ? -315 : -v;
		int v5 = v > 405 ? -405 : -v;
		double rad = (v1) / 180F * Math.PI;
		GL11.glVertex2d(centerX + Math.cos((-45) / 180F * Math.PI) * r, centerY + Math.sin(rad) * r);
		//GL11.glVertex2d(centerX + Math.cos(rad) * r, centerY + Math.sin(rad) * r);
		rad = (v2) / 180F * Math.PI;
		if(v > 45) GL11.glVertex2d(centerX + Math.cos(rad) * r, centerY + Math.sin((-135) / 180F * Math.PI) * r);
		//GL11.glVertex2d(centerX + Math.cos(rad) * r, centerY + Math.sin(rad) * r);
		rad = (v3) / 180F * Math.PI;
		if(v > 135) GL11.glVertex2d(centerX + Math.cos((-225) / 180F * Math.PI) * r, centerY + Math.sin(rad) * r);
		//GL11.glVertex2d(centerX + Math.cos(rad) * r, centerY + Math.sin(rad) * r);
		rad = (v4) / 180F * Math.PI;
		if(v > 225) GL11.glVertex2d(centerX + Math.cos(rad) * r, centerY + Math.sin((-315) / 180F * Math.PI) * r);
		//GL11.glVertex2d(centerX + Math.cos(rad) * r, centerY + Math.sin(rad) * r);
		rad = (v5) / 180F * Math.PI;
		if(v > 315) GL11.glVertex2d(centerX + Math.cos((-405) / 180F * Math.PI) * r, centerY + Math.sin(rad) * r);
		
		//for(int i = degs; i > 0; i--) {
		//	double rad = (i - 90) / 180F * Math.PI;
		//	GL11.glVertex2d(centerX + Math.cos(rad) * r, centerY + Math.sin(rad) * r);
		//}
		GL11.glVertex2i(centerX, centerY);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_STENCIL_TEST);
	}
}
