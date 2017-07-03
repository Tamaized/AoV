package tamaized.aov.client.gui;

import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiScreenClose extends GuiScreen { //TODO: Put this in TamModized

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
			this.mc.player.closeScreen();
		}
	}

}
