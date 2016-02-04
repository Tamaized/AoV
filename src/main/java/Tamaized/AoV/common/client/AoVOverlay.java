package Tamaized.AoV.common.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVCore;
import Tamaized.AoV.core.AoVData;

public class AoVOverlay extends Gui{
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void RenderAoVData(RenderGameOverlayEvent e){
		if(e.isCancelable() || e.type != e.type.EXPERIENCE) return;
		if(AoV.clientAoVCore == null) return;
		AoVCore core = AoV.clientAoVCore;
		AoVData data = core.getPlayer(null);
		if(data != null){
			FontRenderer fontRender = mc.fontRendererObj;
			
			this.drawString(fontRender, "Divine", 5, 40, 0xAAFFFFFF);
			this.drawString(fontRender, "Power", 5, 50, 0xAAFFFFFF);
			
			{
				int x = 5;
				int y = 60;
				int w = 25;
				int h = 80;
			
				this.drawRect(x, y, x+w, y+h, 0x558800FF);
			}
			
			{
				int x = 7;
				int y = 62;
				int w = 21;
				int h = 76;
			
				this.drawRect(x, y, x+w, y+h, 0x77FFFFFF);
			}

			fontRender.drawString("123", 9, 85, 0x000000);
			{
				int x = 10;
				int y = 95;
				int w = 15;
				int h = 1;
			
				this.drawRect(x, y, x+w, y+h, 0xFF000000);
			}
			fontRender.drawString("123", 9, 98, 0x000000);
		}
	}

}
