package Tamaized.AoV.gui.client;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.client.ClientProxy;
import Tamaized.AoV.core.AoVCore;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;

public class AoVOverlay extends Gui{
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	private static ArrayList<String> textSpooler = new ArrayList<String>();
	private FloatyText[] floatyText = new FloatyText[11];
	private int tick = 0;
	
	public static void addFloatyText(String s){
		textSpooler.add(s);
	}
	
	@SubscribeEvent
	public void RenderAoVData(RenderGameOverlayEvent e){
		if(e.isCancelable() || e.type != e.type.EXPERIENCE) return;
		if(AoV.clientAoVCore == null) return;
		AoVCore core = AoV.clientAoVCore;
		AoVData data = core.getPlayer(null);
		FontRenderer fontRender = mc.fontRendererObj;
		
		if(data != null && data.getMaxDivinePower() > 0){

			ClientProxy.bar.render(this, e.partialTicks);
			
			GlStateManager.pushMatrix();{
				GlStateManager.scale(0.5f, 0.5f, 0f);
				if(tick % 20 == 0){
					for(int i=2; i>=0; i--){
						if(floatyText[i] == null) continue;
						floatyText[i].pos+=1;
						if(floatyText[i].pos % 8 == 0){
							FloatyText newText = floatyText[i];
							floatyText[i] = null;
							if(i != 2) floatyText[i+1] = newText;
						}
					}
					if(!textSpooler.isEmpty() && floatyText[0] == null){
						floatyText[0] = new FloatyText(textSpooler.get(0));
						textSpooler.remove(0);
					}
					tick = 0;
				}
				
				for(int i = 0; i<=2; i++){
					FloatyText ft = floatyText[i];
					if(ft == null) continue;
					float perc = 255 - ((float)i/10)*255;
					fontRender.drawStringWithShadow(ft.text, 7, 270+ft.pos, 0xFFFF00);
				}
				
			}GlStateManager.popMatrix();
			
			tick++;
			
			GlStateManager.pushMatrix();
			{
				GlStateManager.scale(0.5f, 0.5f, 0f);
				int cT = 0xFFFFFFFF;
				int cB = 0xDDFFFFFF;
				AbilityBase spell = ClientProxy.bar.getCurrentSlot();
				if(spell != null && spell.getTrueCost(data) > data.getCurrentDivinePower()){
					cT = 0xFFFF0000;
					cB = 0xDDFF0000;
				}
				
				this.drawString(fontRender, "Divine", 5, 180, cT);
				this.drawString(fontRender, "Power", 5, 190, cT);
				
				{
					int x = 5;
					int y = 200;
					int w = 25;
					int h = 80;
					
					this.drawRect(x, y, x+w, y+h, 0x558800FF);
				}
				
				{
					int x = 7;
					int y = 202;
					int w = 21;
					int h = 76;
					float perc = (float)data.getCurrentDivinePower()/(float)data.getMaxDivinePower();
					int var = Math.round(h*perc);
					y = y + (h - var);
					h = var;
					
					this.drawRect(x, y, x+w, y+h, cB);
				}
				
				fontRender.drawString(String.valueOf(data.getCurrentDivinePower()), 9, 230, 0x000000);
				{
					int x = 8;
					int y = 240;
					int w = 19;
					int h = 1;
					
					this.drawRect(x, y, x+w, y+h, 0xFF000000);
				}
				fontRender.drawString(String.valueOf(data.getMaxDivinePower()), 9, 243, 0x000000);
				
				
			}
			GlStateManager.popMatrix();
		}
		
		
	}
	
	private class FloatyText{
		
		public final String text;
		public int pos = 0;
		
		public FloatyText(String s){
			text = s;
		}
		
	}

}
