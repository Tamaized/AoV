package tamaized.aov.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.aov.proxy.ClientProxy;

import javax.annotation.Nonnull;
import java.io.IOException;

import static tamaized.aov.client.gui.AoVUIBar.slotLoc;

public class AdjustElementsGUI extends GuiScreenClose {

	public static final ResourceLocation TEXTURE_SPELLBAR = new ResourceLocation("textures/gui/widgets.png");
	private static final ResourceLocation TEXTURE_ASTRO = new ResourceLocation(AoV.modid, "textures/gui/astro.png");

	private static final int ELEMENT_SPELLBAR = 0;
	private static final int ELEMENT_ASTRO = 1;

	private Element heldElement = null;
	private int oldMouseX;
	private int oldMouseY;
	private int oldElementX;
	private int oldElementY;

	@Override
	public void initGui() {
		super.initGui();
		ScaledResolution sr = new ScaledResolution(mc);
		addButton(new Element(ELEMENT_SPELLBAR, (sr.getScaledWidth() / 2) - 91, 1, ConfigHandler.elementPositions.spellbar_x, ConfigHandler.elementPositions.spellbar_y, 182, 22, 0x00FFFF, ""));
		addButton(new Element(ELEMENT_ASTRO, sr.getScaledWidth() * 2 / 3, sr.getScaledHeight() / 5 - 8, ConfigHandler.elementPositions.astro_x, ConfigHandler.elementPositions.astro_y, (int) (235F * 0.35F), (int) (143F * 0.35F) + 8, 0x00FFFF, ""));
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		oldMouseX = mouseX;
		oldMouseY = mouseY;
		for (GuiButton button : buttonList) {
			if (button instanceof Element) {
				Element check = (Element) button;
				if (mouseX >= check.x + check.defaultX && mouseX <= check.x + check.defaultX + check.width)
					if (mouseY >= check.y + check.defaultY && mouseY <= check.y + check.defaultY + check.height) {
						heldElement = check;
						oldElementX = heldElement.x;
						oldElementY = heldElement.y;
						break;
					}
			}
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		if (heldElement != null) {
			heldElement.x = oldElementX + mouseX - oldMouseX;
			heldElement.y = oldElementY + mouseY - oldMouseY;
			updateValues(heldElement);
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		heldElement = null;
	}

	private void updateValues(Element element) {
		switch (element.id) {
			case ELEMENT_SPELLBAR:
				ConfigHandler.elementPositions.spellbar_x = element.x;
				ConfigHandler.elementPositions.spellbar_y = element.y;
				break;
			case ELEMENT_ASTRO:
				ConfigHandler.elementPositions.astro_x = element.x;
				ConfigHandler.elementPositions.astro_y = element.y;
				break;
		}
	}

	@Override
	public void onGuiClosed() {
		ConfigManager.sync(AoV.modid, Config.Type.INSTANCE);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (mc.world == null) {
			drawDefaultBackground();
			renderSpellBar();
			renderAstro();
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
		drawString(mc.fontRenderer, I18n.format("aov.repositionelements.exit", Keyboard.getKeyName(mc.gameSettings.keyBindInventory.getKeyCode()), Keyboard.getKeyName(Keyboard.KEY_ESCAPE)), 5, 5, 0xFFFF00);
	}

	private void renderSpellBar() {
		final int xpos = ConfigHandler.elementPositions.spellbar_x;
		final int ypos = ConfigHandler.elementPositions.spellbar_y;
		GlStateManager.pushMatrix();
		{
			ScaledResolution sr = new ScaledResolution(mc);
			if (ConfigHandler.renderBarOverHotbar)
				GlStateManager.translate(0, sr.getScaledHeight() - 23, 0);
			float alpha = 0.2f;
			if (ClientProxy.barToggle)
				alpha = 1.0f;
			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
			mc.getTextureManager().bindTexture(TEXTURE_SPELLBAR);
			int i = sr.getScaledWidth() / 2;
			drawTexturedModalRect(xpos + i - 91, ypos + 1, 0, 0, 182, 22);
			drawTexturedModalRect(xpos + i - 91 - 1 + slotLoc * 20, ypos, 0, 22, 24, 22);
		}
		GlStateManager.popMatrix();
	}

	private void renderAstro() {
		GlStateManager.pushMatrix();
		{
			GlStateManager.color(1, 1, 1, 1);
			mc.getTextureManager().bindTexture(TEXTURE_ASTRO);
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buffer = tess.getBuffer();
			buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

			ScaledResolution sr = new ScaledResolution(mc);
			float x = sr.getScaledWidth() * 2 / 3;
			float y = sr.getScaledHeight() / 5;

			x += ConfigHandler.elementPositions.astro_x;
			y += ConfigHandler.elementPositions.astro_y;

			float scale = 0.35F;
			buffer.pos(x, y + 143F * scale, 0).tex(0, 0.5F).endVertex();
			buffer.pos(x + 235F * scale, y + 143F * scale, 0).tex(0.5F, 0.5F).endVertex();
			buffer.pos(x + 235F * scale, y, 0).tex(0.5F, 0).endVertex();
			buffer.pos(x, y, 0).tex(0, 0).endVertex();
			tess.draw();
		}
		GlStateManager.popMatrix();
	}

	static class Element extends GuiButton {

		private final float defaultX;
		private final float defaultY;
		private final float width;
		private final float height;

		private final float red;
		private final float green;
		private final float blue;

		public Element(int buttonId, float xDefault, float yDefault, int x, int y, int w, int h, int color, String buttonText) {
			super(buttonId, x, y, buttonText);
			defaultX = xDefault;
			defaultY = yDefault;
			width = w;
			height = h;
			red = (float) ((color >> 16) & 0xFF) / 255F;
			green = (float) ((color >> 8) & 0xFF) / 255F;
			blue = (float) (color & 0xFF) / 255F;
		}

		@Override
		public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buf = tessellator.getBuffer();
			final float x = this.x + defaultX;
			final float y = this.y + defaultY;
			float alpha = 0.25F;
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			buf.pos(x, y + height, 0).color(red, green, blue, alpha).endVertex();
			buf.pos(x + width, y + height, 0).color(red, green, blue, alpha).endVertex();
			buf.pos(x + width, y, 0).color(red, green, blue, alpha).endVertex();
			buf.pos(x, y, 0).color(red, green, blue, alpha).endVertex();
			tessellator.draw();
			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
		}
	}
}
