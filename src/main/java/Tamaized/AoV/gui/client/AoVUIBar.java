package Tamaized.AoV.gui.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.client.ClientProxy;
import Tamaized.AoV.core.abilities.AbilityBase;

public class AoVUIBar {
	
    public static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
    private Minecraft mc;
    
    public int slotLoc = 0;
	
	private AbilityBase[] slots = new AbilityBase[10];
	
	public AoVUIBar(){
		mc = Minecraft.getMinecraft();
	}
	
	public void setSlot(AbilityBase spell, int slot){
		if(slot < 0 || slot > 9){
			AoV.logger.error("ISSUE! An Ability was attempted to be set out of bounds on AoVUIBar!");
			return;
		}
		slots[slot] = spell;
	}
	
	public AbilityBase getSlot(int slot){
		return slots[slot];
	}
	
	public AbilityBase getCurrentSlot(){
		return slots[slotLoc];
	}
	
	public boolean contains(AbilityBase spell){
		for(AbilityBase ab : slots){
			if(ab == spell) return true;
		}
		return false;
	}
	
	public void addToNearestSlot(AbilityBase spell){
		if(contains(spell)) return;
		for(int i=0; i<9; i++){
			if(slots[i] == null){
				slots[i] = spell;
				break;
			}
		}
	}
	
	public void addToSlot(int i, AbilityBase spell){
		slots[i] = spell;
	}
	
	public void removeSlot(int i){
		slots[i] = null;
	}
	
	public void clearAllSlots(){
		for(int i=0; i<9; i++){
			slots[i] = null;
		}
	}
	
	public void render(Gui gui, float partialTicks){
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
        for (int j = 0; j < 9; ++j)
        {
			GlStateManager.translate(20.01f, 0, 0);
        	if(slots[j] == null) continue;
            int k = sr.getScaledWidth() / 2 - 90 + 2;
            int l = 4;//sr.getScaledHeight() - 16 - 3;
			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);	
            renderHotbarIcon(gui, j, k, l, partialTicks, slots[j] == null ? null : slots[j].getIcon());
        }
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
	}
	
	public static void renderHotbarIcon(Gui gui, int index, int xPos, int yPos, float partialTicks, ResourceLocation icon){
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
}
