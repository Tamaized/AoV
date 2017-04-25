package Tamaized.AoV.gui.client;

import java.io.IOException;

import net.minecraft.client.gui.GuiScreen;

public class GuiScreenClose extends GuiScreen { //TODO: Put this in TamModized

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
			this.mc.player.closeScreen();
		}
	}

}
