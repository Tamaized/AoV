package tamaized.aov.client.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class BlankButton extends GuiButton {

	private boolean debug = false;

	public BlankButton(int buttonId, int x, int y, int w, int h, boolean b) {
		super(buttonId, x, y, w, h, "");
		debug = b;
	}


	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float p_191745_4_) {
		if (debug) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 0, 1);
			this.drawRect(this.x, this.y, x + this.width, y + this.height, 0xFF00AA00);
			GlStateManager.popMatrix();
		}
	}

}
