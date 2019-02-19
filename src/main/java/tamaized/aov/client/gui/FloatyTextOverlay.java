package tamaized.aov.client.gui;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tamaized.aov.AoV;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Dist.CLIENT)
public class FloatyTextOverlay extends Gui {

	private static Minecraft mc = Minecraft.getInstance();

	private static List<String> textSpooler = new ArrayList<>();
	private static volatile FloatyText[] floatyText = new FloatyText[11];

	public static void addFloatyText(String s) {
		textSpooler.add(I18n.format(s));
	}

	@SubscribeEvent
	public static void update(TickEvent.ClientTickEvent e) {
		if (!mc.isGamePaused()) {
			for (int i = 5; i >= 0; i--) {
				if (floatyText[i] == null)
					continue;
				floatyText[i].pos += 1;
				if (floatyText[i].pos % 8 == 0) {
					FloatyText newText = floatyText[i];
					floatyText[i] = null;
					if (i != 5)
						floatyText[i + 1] = newText;
				}
			}
			if (!textSpooler.isEmpty() && floatyText[0] == null) {
				floatyText[0] = new FloatyText(textSpooler.get(0));
				textSpooler.remove(0);
			}
		}
	}

	@SubscribeEvent
	public static void render(RenderGameOverlayEvent e) {
		if (e.isCancelable() || e.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
			return;
		FontRenderer fontRender = mc.fontRenderer;
		MainWindow window = Minecraft.getInstance().mainWindow;
		int sW = window.getScaledWidth() / 2;

		GlStateManager.pushMatrix();
		{
			GlStateManager.scalef(0.5f, 0.5f, 0f);
			for (int i = 0; i <= 5; i++) {
				FloatyText ft = floatyText[i];
				if (ft == null)
					continue;
				fontRender.drawStringWithShadow(ft.text, (sW * 4) - 230, -5 + ft.pos, 0xFFFF00);
			}

		}
		GlStateManager.popMatrix();

	}

	private static class FloatyText {

		public final String text;
		public int pos = 0;

		public FloatyText(String s) {
			text = s;
		}
	}

}