package Tamaized.AoV.gui.buttons;

import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.gui.client.AoVUIBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class SpellButton extends GuiButton {

	private final Ability spell;

	public SpellButton(int buttonId, int x, int y, Ability theSpell) {
		super(buttonId, x, y, 80, 18, "");
		spell = theSpell;
	}

	public Ability getSpell() {
		return spell;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (visible) {
			FontRenderer fontrenderer = mc.fontRendererObj;
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			int i = getHoverState(hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);
			// drawRect(xPosition + width / 2, yPosition, width / 2, height, 0xFFFFFFFF);
			mouseDragged(mc, mouseX, mouseY);
			int j = 0xBBFFFFFF;

			if (packedFGColour != 0) {
				// j = packedFGColour;
			} else if (!enabled) {
				j = 0xFF888888;
			} else if (hovered) {
				j = 0xFFFFFFFF;
			}

			drawRect(xPosition, yPosition, xPosition + width, yPosition + height, j);
			AoVUIBar.renderHotbarIcon(this, null, 0, xPosition + 1, yPosition + 1, 0, spell == null ? null : spell.getAbility().getIcon(), false);
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5f, 0.5f, 0.0f);
			drawString(fontrenderer, spell.getAbility().getName(), xPosition * 2 + 38, yPosition * 2 + 14, 0xFFFF00);
			GlStateManager.popMatrix();
		}
	}

}
