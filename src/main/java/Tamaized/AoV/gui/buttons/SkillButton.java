package Tamaized.AoV.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.gui.client.AoVUIBar;

public class SkillButton extends GuiButton {
	
	public final AoVSkill skill;
	public boolean isObtained;
	public boolean notEnoughPoints = false;

	/**
	 * 
	 * @param buttonId
	 * @param x
	 * @param y
	 * @param skillName
	 */
	public SkillButton(int buttonId, int x, int y, String skillName) {
		super(buttonId, x, y, 18, 18, "");
		skill = AoVSkill.getSkillFromName(skillName);
		updateVar();
	}
	
	public void updateVar(){
		AoVData data = AoV.clientAoVCore.getPlayer(null);
		this.enabled = true;
		notEnoughPoints = false;
		
		if(skill.parent == null) {
			notEnoughPoints = true;
			this.enabled = false;
			if(data.getCurrentSkillPoints() >= skill.pointCost && data.getLevel() >= skill.minLevel && data.getSpentSkillPoints() >= skill.minPointsSpent){
				notEnoughPoints = false;
				this.enabled = true;
			}
		}
		else if(data.hasSkill(skill.parent)) {
			notEnoughPoints = true;
			this.enabled = false;
			if(data.getCurrentSkillPoints() >= skill.pointCost && data.getLevel() >= skill.minLevel && data.getSpentSkillPoints() >= skill.minPointsSpent){
				notEnoughPoints = false;
				this.enabled = true;
			}
		}else{
			notEnoughPoints = true;
			this.enabled = false;
		}
		
		if(data.hasSkillCore() && skill.isCore){
			this.enabled = false;
			notEnoughPoints = true;
		}
		if(data.hasSkill(skill)){
			this.enabled = false;
			isObtained = true;
		}else{
			isObtained = false;
		}
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY){
        if (this.visible){
            FontRenderer fontrenderer = mc.fontRendererObj;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            //this.drawRect(this.xPosition + this.width / 2, this.yPosition, this.width / 2, this.height, 0xFFFFFFFF);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 0xBBFFFFFF;

            if (packedFGColour != 0)
            {
                //j = packedFGColour;
            }
            else
            if (!this.enabled)
            {
                j = 0xFF888888;
            }
            else if (this.hovered)
            {
                j = 0xFFFFFFFF;
            }
            if(notEnoughPoints) j = 0xAAFF0000;
            if(isObtained) j = 0xFF00FF00;

            this.drawRect(this.xPosition, this.yPosition, xPosition+this.width, yPosition+this.height, j);
            float alpha = (float)(j >> 24 & 255) / 255.0F;
            GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
            AoVUIBar.renderHotbarIcon(this, null, 0, xPosition+1, yPosition+1, 0, skill == null ? null : skill.getIcon(), false);
        }
    }

}
