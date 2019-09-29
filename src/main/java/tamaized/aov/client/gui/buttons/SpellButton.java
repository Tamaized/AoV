package tamaized.aov.client.gui.buttons;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import tamaized.aov.client.gui.AoVUIBar;
import tamaized.aov.client.gui.RenderUtils;
import tamaized.aov.common.core.abilities.AbilityBase;

public class SpellButton extends Button {

	private final AbilityBase spell;

	public SpellButton(int x, int y, AbilityBase theSpell, IPressable function) {
		super(x, y, 90, 18, "", function);
		spell = theSpell;
	}

	public AbilityBase getSpell() {
		return spell;
	}

	@Override
	public void render(int mouseX, int mouseY, float p_191745_4_) {
		Minecraft mc = Minecraft.getInstance();
		if (visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			isHovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);
			// drawRect(x + width / 2, y, width / 2, height, 0xFFFFFFFF);
			mouseDragged(mouseX, mouseY, 0, mouseX, mouseY);
			int j = 0xFFFFFFBB;

			if (packedFGColor != 0) {
				j = packedFGColor;
			} else if (!active) {
				j = 0x888888FF;
			} else if (isHovered) {
				j = 0xFFFFFFFF;
			}

			RenderUtils.setup(blitOffset);
			RenderUtils.renderRect(x, y, width, height, false, j);
			if (spell == null)
				return;
			AoVUIBar.renderHotbarIcon(null, 0, x + 1, y + 1, spell.getIcon(), false);
			GlStateManager.pushMatrix();
			GlStateManager.scalef(0.5f, 0.5f, 0.0f);
			drawString(fontrenderer, spell.getName(), x * 2 + 38, y * 2 + 14, 0xFFFF00);
			GlStateManager.popMatrix();
		}
	}

}
