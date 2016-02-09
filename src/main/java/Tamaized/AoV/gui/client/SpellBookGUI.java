package Tamaized.AoV.gui.client;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.client.ClientProxy;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.gui.GuiHandler;
import Tamaized.AoV.gui.buttons.BlankButton;
import Tamaized.AoV.gui.buttons.SpellButton;

public class SpellBookGUI extends GuiScreen {
	
	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_BACK = 1;
	
	public static final int BUTTON_SPELL = 2;

	public static final int BUTTON_BAR_SLOT_0 = 3;
	public static final int BUTTON_BAR_SLOT_1 = 4;
	public static final int BUTTON_BAR_SLOT_2 = 5;
	public static final int BUTTON_BAR_SLOT_3 = 6;
	public static final int BUTTON_BAR_SLOT_4 = 7;
	public static final int BUTTON_BAR_SLOT_5 = 8;
	public static final int BUTTON_BAR_SLOT_6 = 9;
	public static final int BUTTON_BAR_SLOT_7 = 10;
	public static final int BUTTON_BAR_SLOT_8 = 11;

	@Override
	public void initGui(){
		int margin = 20;
		int padding = 100;
		float workW = width-padding;
		int loc1 = (int) (workW*.25) + margin;
		int loc2 = (int) (workW*.75) + margin;
		buttonList.add(new GuiButton(BUTTON_BACK, loc1, height-25, 80, 20, "Back"));
		buttonList.add(new GuiButton(BUTTON_CLOSE, loc2, height-25, 80, 20, "Close"));
		
		int id = 0;
		int xLoc = 50;
		int yLoc = 50;
		
		ArrayList<String> spellList = new ArrayList<String>();
		for(AoVSkill skill : AoV.clientAoVCore.getPlayer(null).getAllObtainedSkills()){
			for(String s : skill.abilities){
				AbilityBase spell = AbilityBase.fromName(s);
				if(spellList.contains(s) || spell == null) continue;
				spellList.add(s);
				buttonList.add(new SpellButton(BUTTON_SPELL, xLoc, yLoc*(id+1), spell));
				id++;
			}
		}
		spellList.clear();
		spellList = null;
		
		buttonList.add(new BlankButton(BUTTON_BAR_SLOT_0, 122, 210, 16, 16, false));
		buttonList.add(new BlankButton(BUTTON_BAR_SLOT_1, 142, 210, 16, 16, false));
		buttonList.add(new BlankButton(BUTTON_BAR_SLOT_2, 162, 210, 16, 16, false));
		buttonList.add(new BlankButton(BUTTON_BAR_SLOT_3, 182, 210, 16, 16, false));
		buttonList.add(new BlankButton(BUTTON_BAR_SLOT_4, 202, 210, 16, 16, false));
		buttonList.add(new BlankButton(BUTTON_BAR_SLOT_5, 222, 210, 16, 16, false));
		buttonList.add(new BlankButton(BUTTON_BAR_SLOT_6, 242, 210, 16, 16, false));
		buttonList.add(new BlankButton(BUTTON_BAR_SLOT_7, 262, 210, 16, 16, false));
		buttonList.add(new BlankButton(BUTTON_BAR_SLOT_8, 282, 210, 16, 16, false));
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
				case BUTTON_SPELL:
					ClientProxy.bar.addToNearestSlot(((SpellButton) button).spell);
					break;
				case BUTTON_BAR_SLOT_0:
					ClientProxy.bar.removeSlot(0);
					break;
				case BUTTON_BAR_SLOT_1:
					ClientProxy.bar.removeSlot(1);
					break;
				case BUTTON_BAR_SLOT_2:
					ClientProxy.bar.removeSlot(2);
					break;
				case BUTTON_BAR_SLOT_3:
					ClientProxy.bar.removeSlot(3);
					break;
				case BUTTON_BAR_SLOT_4:
					ClientProxy.bar.removeSlot(4);
					break;
				case BUTTON_BAR_SLOT_5:
					ClientProxy.bar.removeSlot(5);
					break;
				case BUTTON_BAR_SLOT_6:
					ClientProxy.bar.removeSlot(6);
					break;
				case BUTTON_BAR_SLOT_7:
					ClientProxy.bar.removeSlot(7);
					break;
				case BUTTON_BAR_SLOT_8:
					ClientProxy.bar.removeSlot(8);
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
		renderBar(partialTicks);
		for(GuiButton b : buttonList){
			if(!b.isMouseOver()) continue;
			if(b instanceof SpellButton){
				SpellButton sb = (SpellButton) b;
				if(sb.spell != null && sb.spell.description != null) this.drawHoveringText(sb.spell.description, mouseX, mouseY);
			}
		}
	}
	
	private void renderBar(float partialTicks){
		ScaledResolution sr = new ScaledResolution(mc);
		float alpha = 1.0f;
		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        mc.getTextureManager().bindTexture(AoVUIBar.widgetsTexPath);
        int i = sr.getScaledWidth() / 2;
        this.drawTexturedModalRect(i - 91, sr.getScaledHeight() - 50, 0, 0, 182, 22);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
		GlStateManager.translate(0.01f, 0, 0);
		GlStateManager.translate(-20.01f, 0, 0);
        for (int j = 0; j < 9; ++j)
        {
			GlStateManager.translate(20.01f, 0, 0);
        	if(ClientProxy.bar.getSlot(j) == null) continue;
            int k = sr.getScaledWidth() / 2 - 90 + 2;
            int l = sr.getScaledHeight() - 47;
			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);	
            AoVUIBar.renderHotbarIcon(this, j, k, l, partialTicks, ClientProxy.bar.getSlot(j) == null ? null : ClientProxy.bar.getSlot(j).getIcon());
        }
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
	}

}
