package tamaized.aov.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.InputMappings;

public class GuiScreenClose extends GuiScreen {

	@Override
	public boolean charTyped(char typedChar, int keyCode) {
		if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(InputMappings.getInputByCode(keyCode, 0))) {
			if (mc.player == null)
				Minecraft.getInstance().displayGuiScreen(null);
			else
				this.mc.player.closeScreen();
		}
		return super.charTyped(typedChar, keyCode);
	}

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		boolean flag = false;
		for (GuiButton button : buttons) {
			if (button.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
				flag = true;
				break;
			}
		}
		return flag || super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}
}
