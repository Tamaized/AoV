package Tamaized.AoV.gui.client;

import java.io.IOException;

import Tamaized.AoV.gui.GuiHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class SpellBookGUI extends GuiScreen {
	
	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_BACK = 1;

	@Override
	public void initGui(){
		int margin = 20;
		int padding = 100;
		float workW = width-padding;
		int loc1 = (int) (workW*.25) + margin;
		int loc2 = (int) (workW*.75) + margin;
		buttonList.add(new GuiButton(BUTTON_BACK, loc1, height-25, 80, 20, "Back"));
		buttonList.add(new GuiButton(BUTTON_CLOSE, loc2, height-25, 80, 20, "Close"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException{
		if (button.enabled){
			switch(button.id){
				case BUTTON_CLOSE:
					mc.displayGuiScreen((GuiScreen)null);
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
	public boolean doesGuiPauseGame(){
		return false;
	}
	

	@Override
	public void onGuiClosed(){
		
	}
	

	@Override
	public void updateScreen(){
		
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Angel of Vengeance: SpellBook", this.width / 2, 15, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
