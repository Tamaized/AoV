package tamaized.aov.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tamaized.aov.AoV;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Dist.CLIENT)
public class FloatyTextOverlay extends AbstractGui {

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
		MainWindow window = Minecraft.getInstance().getMainWindow();
		int sW = window.getScaledWidth() / 2;

		RenderSystem.pushMatrix();
		{
			RenderSystem.scalef(0.5F, 0.5F, 0F);
			for (int i = 0; i <= 5; i++) {
				FloatyText ft = floatyText[i];
				if (ft == null)
					continue;
				fontRender.drawStringWithShadow(ft.text, (sW * 4) - 230, -5 + ft.pos, 0xFFFF00);
			}

		}
		RenderSystem.popMatrix();

	}

	private static class FloatyText {

		public final String text;
		public int pos = 0;

		public FloatyText(String s) {
			text = s;
		}
	}

}