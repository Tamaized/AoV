package Tamaized.AoV.gui.client;

import java.util.ArrayList;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.common.client.ClientProxy;
import Tamaized.AoV.common.client.ClientTicker;
import Tamaized.AoV.core.abilities.Ability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AoVOverlay extends Gui {

	private Minecraft mc = Minecraft.getMinecraft();

	private static ArrayList<String> textSpooler = new ArrayList<String>();
	private FloatyText[] floatyText = new FloatyText[11];
	private int tick = 0;

	public static void addFloatyText(String s) {
		textSpooler.add(s);
	}

	@SubscribeEvent
	public void RenderAoVData(RenderGameOverlayEvent e) {
		if (e.isCancelable() || e.getType() != e.getType().EXPERIENCE || !mc.player.hasCapability(CapabilityList.AOV, null)) return;
		ClientTicker.update();
		IAoVCapability cap = mc.player.getCapability(CapabilityList.AOV, null);
		FontRenderer fontRender = mc.fontRendererObj;
		ScaledResolution sr = new ScaledResolution(mc);
		int sW = sr.getScaledWidth() / 2;

		if (cap.hasCoreSkill()) {

			if (ClientProxy.barToggle) {
				for (int i = 0; i < 9; i++) {
					int x = sW - 90 + (20 * i);
					int y = 1 + ClientTicker.charges.getValue(i);
					renderCharges(x, y, fontRender, cap, i);
				}
			}

			ClientProxy.bar.render(this, e.getPartialTicks());

			GlStateManager.pushMatrix();
			{
				GlStateManager.scale(0.5f, 0.5f, 0f);
				if (!mc.isGamePaused()) {
					for (int i = 5; i >= 0; i--) {
						if (floatyText[i] == null) continue;
						floatyText[i].pos += 1;
						if (floatyText[i].pos % 8 == 0) {
							FloatyText newText = floatyText[i];
							floatyText[i] = null;
							if (i != 5) floatyText[i + 1] = newText;
						}
					}
					if (!textSpooler.isEmpty() && floatyText[0] == null) {
						floatyText[0] = new FloatyText(textSpooler.get(0));
						textSpooler.remove(0);
					}
				}

				for (int i = 0; i <= 5; i++) {
					FloatyText ft = floatyText[i];
					if (ft == null) continue;
					float perc = 255 - ((float) i / 10) * 255;
					fontRender.drawStringWithShadow(ft.text, (sW * 4) - 230, -5 + ft.pos, 0xFFFF00);
				}

			}
			GlStateManager.popMatrix();
		}

	}

	private void renderCharges(int x, int y, FontRenderer fontRender, IAoVCapability cap, int index) {
		Ability ability = cap.getSlot(index);
		int val = ability == null ? -1 : ability.getCharges();
		if (val < 0) return;
		int w = 20;
		int h = 20;
		this.drawRect(x, y, x + w, y + h, !cap.canUseAbility(ability) ? 0x77FF0000 : 0x7700BBFF);
		this.drawCenteredStringNoShadow(fontRender, String.valueOf(val), x + 10, y + 10, 0x000000);
	}

	private class FloatyText {

		public final String text;
		public int pos = 0;

		public FloatyText(String s) {
			text = s;
		}
	}

	private void drawCenteredStringNoShadow(FontRenderer fontRendererIn, String text, int x, int y, int color) {
		fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, false);
	}

}
