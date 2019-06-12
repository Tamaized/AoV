package tamaized.aov.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;

public class GuiScreenClose extends Screen {

	@Override
	public boolean keyReleased(int key, int scan, int mods) {
		if (mc.gameSettings.keyBindInventory.getKey().getKeyCode() == key) {
			if (mc.player == null)
				Minecraft.getInstance().displayGuiScreen(null);
			else
				this.mc.player.closeScreen();
		}
		return super.keyReleased(key, scan, mods);
	}

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		boolean flag = false;
		for (Button button : buttons) {
			if (button.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
				flag = true;
				break;
			}
		}
		return flag || super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}
}
