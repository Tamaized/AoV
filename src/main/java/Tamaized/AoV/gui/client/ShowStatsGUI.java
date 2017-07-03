package Tamaized.AoV.gui.client;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.AoVCapabilityHandler;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.gui.GuiHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class ShowStatsGUI extends GuiScreenClose {

	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_BACK = 1;

	@Override
	public void initGui() {
		int margin = 20;
		int padding = 100;
		float workW = width - padding;
		int loc1 = (int) (workW * .25) + margin;
		int loc2 = (int) (workW * .75) + margin;
		buttonList.add(new GuiButton(BUTTON_BACK, loc1, height - 25, 80, 20, "Back"));
		buttonList.add(new GuiButton(BUTTON_CLOSE, loc2, height - 25, 80, 20, "Close"));

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			switch (button.id) {
				case BUTTON_CLOSE:
					mc.displayGuiScreen((GuiScreen) null);
					break;
				case BUTTON_BACK:
					GuiHandler.openGUI(GuiHandler.GUI_SKILLS);
					break;
				default:
					break;
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {

	}

	@Override
	public void updateScreen() {
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, "Angel of Vengeance: Stats", this.width / 2, 15, 16777215);
		IAoVCapability cap = mc == null || mc.player == null ? null : mc.player.getCapability(CapabilityList.AOV, null);
		String s = cap == null ? "null" : cap.getLevel() >= cap.getMaxLevel() ? "Max Experience Achieved" : (cap == null ? "null" : cap.getExp()) + "/" + (cap == null ? "null" : AoVCapabilityHandler.getExpForLevel(cap.getLevel() + 1));
		this.drawCenteredString(fontRenderer, "Experience: " + s, width / 2, 50, 0xFFFF00);
		this.drawCenteredString(fontRenderer, "Level: " + (cap == null ? "null" : cap.getLevel()), width / 2, 60, 0xFFFF00);
		this.drawCenteredString(fontRenderer, "Extra Charges: " + (cap == null ? "null" : cap.getExtraCharges()), width / 2, 70, 0x00BBFF);
		this.drawCenteredString(fontRenderer, "Spell Power: " + (cap == null ? "null" : (int) cap.getSpellPower()), width / 2, 80, 0x00FF00);
		this.drawCenteredString(fontRenderer, "Dodge: " + (cap == null ? "null" : (int) cap.getDodge()) + "%", width / 2, 90, 0x00FF00);
		this.drawCenteredString(fontRenderer, "DoubleStrike: " + (cap == null ? "null" : (int) cap.getDoubleStrike()) + "%", width / 2, 100, 0x00FF00);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
