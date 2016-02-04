package Tamaized.AoV.gui.client;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

import Tamaized.AoV.gui.SkillButton;

public class AoVSkillsGUI extends GuiScreen {
	
	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_SKILL_HEALER_CORE = 100;
	private static final int BUTTON_SKILL_DEFENDER_CORE = 200;
	private static final int BUTTON_SKILL_CASTER_CORE = 300;

	public AoVSkillsGUI() {
		super();
	}
	
	@Override
	public void initGui(){
		buttonList.add(new GuiButton(0, (width/2)-50, height-25, 100, 20, "Close"));
		buttonList.add(new SkillButton(0, (width/2), height/2, "HealerSkillCore"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException{
		if (button.enabled){
			switch(button.id){
				case BUTTON_CLOSE:
					mc.displayGuiScreen((GuiScreen)null);
					break;
				case BUTTON_SKILL_HEALER_CORE:
					break;
				case BUTTON_SKILL_DEFENDER_CORE:
					break;
				case BUTTON_SKILL_CASTER_CORE:
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
	public void updateScreen(){
		
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Angel of Vengeance: Skills", this.width / 2, 15, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
		for(GuiButton b : buttonList){
			if(!b.isMouseOver()) continue;
			if(b instanceof SkillButton){
				SkillButton sb = (SkillButton) b;
				if(sb.skill != null && sb.skill.description != null) this.drawHoveringText(sb.skill.description, mouseX, mouseY);
				else{
					System.out.println(sb.skill);
					if(sb.skill != null){
						System.out.println(sb.skill.description);
					}
				}
			}
		}
	}
}
