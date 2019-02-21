package tamaized.aov.client.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import tamaized.aov.client.gui.AoVUIBar;
import tamaized.aov.common.core.abilities.AbilityBase;

public class SpellButton extends GuiButton {

	private final AbilityBase spell;

	public SpellButton(int buttonId, int x, int y, AbilityBase theSpell) {
		super(buttonId, x, y, 90, 18, "");
		spell = theSpell;
	}

	public AbilityBase getSpell() {
		return spell;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float p_191745_4_) {
		if (visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
			int i = getHoverState(hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);
			// drawRect(x + width / 2, y, width / 2, height, 0xFFFFFFFF);
			mouseDragged(mc, mouseX, mouseY);
			int j = 0xBBFFFFFF;

			if (packedFGColour != 0) {
				// j = packedFGColour;
			} else if (!enabled) {
				j = 0xFF888888;
			} else if (hovered) {
				j = 0xFFFFFFFF;
			}

			drawRect(x, y, x + width, y + height, j);
			if (spell == null)
				return;
			AoVUIBar.renderHotbarIcon(this, null, 0, x + 1, y + 1, spell.getIcon(), false);
			GlStateManager.pushMatrix();
			GlStateManager.scalef(0.5f, 0.5f, 0.0f);
			drawString(fontrenderer, spell.getName(), x * 2 + 38, y * 2 + 14, 0xFFFF00);
			GlStateManager.popMatrix();
		}
	}

}
