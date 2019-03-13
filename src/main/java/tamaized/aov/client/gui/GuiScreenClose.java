package tamaized.aov.client.gui;

import net.minecraft.client.Minecraft;
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

}
