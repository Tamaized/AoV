package Tamaized.AoV.common.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVCore;
import Tamaized.AoV.core.AoVData;

public class AoVOverlay {
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void RenderAoVData(RenderGameOverlayEvent e){
		if(AoV.clientAoVCore == null) return;
		AoVCore core = AoV.clientAoVCore;
		AoVData data = core.getPlayer(null);
		if(data != null){
			FontRenderer fontRender = mc.fontRendererObj;
			ScaledResolution res = new ScaledResolution(mc);
			int width = res.getScaledWidth();
			int height = res.getScaledHeight();
			fontRender.drawStringWithShadow("ASDF", width/2, height/2, 0xFFFFFF);
		}
	}

}
