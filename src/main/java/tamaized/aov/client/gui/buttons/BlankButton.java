package tamaized.aov.client.gui.buttons;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.widget.button.Button;
import tamaized.aov.client.gui.RenderUtils;

public class BlankButton extends Button {

	private boolean debug = false;

	public BlankButton(int x, int y, int w, int h, IPressable function) {
		super(x, y, w, h, "", function);
	}

	public BlankButton markDebug() {
		debug = true;
		return this;
	}

	@Override
	public void render(int mouseX, int mouseY, float p_191745_4_) {
		if (debug) {
			GlStateManager.pushMatrix();
			GlStateManager.translated(0, 0, 1);
			RenderUtils.setup(blitOffset);
			RenderUtils.renderRect(this.x, this.y, x + this.width, y + this.height, false, 0xFF00AA00);
			GlStateManager.popMatrix();
		}
	}

}
