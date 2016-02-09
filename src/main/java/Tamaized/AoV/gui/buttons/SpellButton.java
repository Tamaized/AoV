package Tamaized.AoV.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.gui.client.AoVUIBar;
import Tamaized.AoV.gui.client.SpellBookGUI;

public class SpellButton extends GuiButton{
	
	public final AbilityBase spell;

	public SpellButton(int buttonId, int x, int y, AbilityBase theSpell) {
		super(buttonId, x, y, 65, 18, "");
		spell = theSpell;
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

            this.drawRect(this.xPosition, this.yPosition, xPosition+this.width, yPosition+this.height, j);
            AoVUIBar.renderHotbarIcon(this, 0, xPosition+1, yPosition+1, 0, spell == null ? null : spell.getIcon());
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5f, 0.5f, 0.0f);
            this.drawString(fontrenderer, spell.getName(), xPosition+85, yPosition+63, 0xFFFF00);
            GlStateManager.popMatrix();
        }
    }

}
