package Tamaized.AoV.gui.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;

public class AoVUIBar {
	
    private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
    private Minecraft mc;
	
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
	
	public void render(Gui gui, float partialTicks){
		ScaledResolution sr = new ScaledResolution(mc);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(widgetsTexPath);
        EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
        int i = sr.getScaledWidth() / 2;
        gui.drawTexturedModalRect(i - 91, 1, 0, 0, 182, 22);
        gui.drawTexturedModalRect(i - 91 - 1 + /* Slot ID */1 * 20, 0, 0, 22, 24, 22);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();

        for (int j = 0; j < 9; ++j)
        {
            int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
            int l = sr.getScaledHeight() - 16 - 3;
            //this.renderHotbarItem(j, k, l, partialTicks, entityplayer);
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
	}

}
