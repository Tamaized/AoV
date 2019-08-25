package tamaized.aov.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;

public class GuiScreenClose extends Screen {

	protected GuiScreenClose(ITextComponent p_i51108_1_) {
		super(p_i51108_1_);
	}

	protected static ITextComponent makeTranslationKey(String name) {
		return new TranslationTextComponent(AoV.MODID + ".gui." + name);
	}

	@Override
	public boolean keyReleased(int key, int scan, int mods) {
		if (minecraft.gameSettings.keyBindInventory.getKey().getKeyCode() == key) {
			if (minecraft.player == null)
				Minecraft.getInstance().displayGuiScreen(null);
			else
				this.minecraft.player.closeScreen();
		}
		return super.keyReleased(key, scan, mods);
	}

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		boolean flag = false;
		for (Widget button : buttons) {
			if (button.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
				flag = true;
				break;
			}
		}
		return flag || super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}
}
