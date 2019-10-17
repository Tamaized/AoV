package tamaized.aov.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.client.ClientHelpers;

import static tamaized.aov.client.gui.AoVUIBar.slotLoc;

public class AdjustElementsGUI extends GuiScreenClose {

	public static final ResourceLocation TEXTURE_SPELLBAR = new ResourceLocation("textures/gui/widgets.png");

	private static final int ELEMENT_SPELLBAR = 0;
	private static final int ELEMENT_ASTRO = 1;
	private static final int ELEMENT_TARGET = 2;

	private Element heldElement = null;
	private double oldMouseX;
	private double oldMouseY;
	private double oldElementX;
	private double oldElementY;

	protected AdjustElementsGUI() {
		super(makeTranslationKey("adjustelements"));
	}

	@Override
	public void init() {
		super.init();
		MainWindow sr = Minecraft.getInstance().mainWindow;
		addButton(new Element(ELEMENT_SPELLBAR, (sr.getScaledWidth() / 2F) - 91, 1, AoV.config_client.ELEMENT_POSITIONS.spellbar_x.get(), AoV.config_client.ELEMENT_POSITIONS.spellbar_y.get(), 182, 22, 0x00FFFF, ""));
		addButton(new Element(ELEMENT_ASTRO, sr.getScaledWidth() * 2F / 3F, sr.getScaledHeight() / 5F - 8F, AoV.config_client.ELEMENT_POSITIONS.astro_x.get(), AoV.config_client.ELEMENT_POSITIONS.astro_y.get(), (int) (235F * 0.35F), (int) (143F * 0.35F) + 8, 0x00FFFF, ""));
		addButton(new Element(ELEMENT_TARGET, 10, 150, AoV.config_client.ELEMENT_POSITIONS.target_x.get(), AoV.config_client.ELEMENT_POSITIONS.target_y.get(), 100, 41, 0x00FFFF, ""));
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int id) {
		oldMouseX = mouseX;
		oldMouseY = mouseY;
		for (Widget button : buttons) {
			if (button instanceof Element) {
				Element check = (Element) button;
				if (mouseX >= check.x + check.defaultX && mouseX <= check.x + check.defaultX + check.width)
					if (mouseY >= check.y + check.defaultY && mouseY <= check.y + check.defaultY + check.height) {
						heldElement = check;
						oldElementX = heldElement.x;
						oldElementY = heldElement.y;
						return true;
					}
			}
		}
		return false;
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
		if (heldElement != null) {
			heldElement.x = (int) (oldElementX + mouseX - oldMouseX);
			heldElement.y = (int) (oldElementY + mouseY - oldMouseY);
			updateValues(heldElement);
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
		if (heldElement != null) {
			heldElement = null;
			return true;
		}
		return false;
	}

	private void updateValues(Element element) {
		//		AoV.config.file.set(AoV.config.ELEMENT_POSITIONS.spellbar_x.getPath(), );
		/*switch (element.id) { TODO
			case ELEMENT_SPELLBAR:
				ConfigHandler.ELEMENT_POSITIONS.spellbar_x = element.x;
				ConfigHandler.ELEMENT_POSITIONS.spellbar_y = element.y;
				break;
			case ELEMENT_ASTRO:
				ConfigHandler.ELEMENT_POSITIONS.astro_x = element.x;
				ConfigHandler.ELEMENT_POSITIONS.astro_y = element.y;
				break;
			case ELEMENT_TARGET:
				ConfigHandler.ELEMENT_POSITIONS.target_x = element.x;
				ConfigHandler.ELEMENT_POSITIONS.target_y = element.y;
				break;
		}*/
	}

	@Override
	public void onClose() {
		//		ConfigManager.sync(AoV.MODID, Config.Type.INSTANCE); TODO
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		if (Minecraft.getInstance().world == null) {
			renderBackground();
			renderSpellBar();
			renderAstro();
			renderFocus();
		}
		super.render(mouseX, mouseY, partialTicks);
		//		drawString(mc.fontRenderer, I18n.format("aov.repositionelements.exit", Keyboard.getKeyName(mc.gameSettings.keyBindInventory.getKeyCode()), Keyboard.getKeyName(Keyboard.KEY_ESCAPE)), 5, 5, 0xFFFF00); TODO
	}

	private void renderSpellBar() {
		final int xpos = AoV.config_client.ELEMENT_POSITIONS.spellbar_x.get();
		final int ypos = AoV.config_client.ELEMENT_POSITIONS.spellbar_y.get();
		GlStateManager.pushMatrix();
		{
			MainWindow sr = Minecraft.getInstance().mainWindow;
			if (AoV.config_client.renderBarOverHotbar.get())
				GlStateManager.translated(0, sr.getScaledHeight() - 23, 0);
			float alpha = 0.2f;
			if (ClientHelpers.barToggle)
				alpha = 1.0f;
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);
			Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE_SPELLBAR);
			int i = sr.getScaledWidth() / 2;
			RenderUtils.setup(blitOffset);
			RenderUtils.renderRect(xpos + i - 91, ypos + 1, 182, 22);
			RenderUtils.renderRect(xpos + i - 91 - 1 + slotLoc * 20, ypos, 24, 22, 0, 22F / 256F);
		}
		GlStateManager.popMatrix();
	}

	private void renderAstro() {
		GlStateManager.pushMatrix();
		{
			GlStateManager.color4f(1, 1, 1, 1);
			Minecraft.getInstance().getTextureManager().bindTexture(AoVOverlay.TEXTURE_ASTRO);
			GlStateManager.enableAlphaTest();
			GlStateManager.enableBlend();
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buffer = tess.getBuffer();
			buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

			MainWindow sr = Minecraft.getInstance().mainWindow;
			float x = sr.getScaledWidth() * 2F / 3F;
			float y = sr.getScaledHeight() / 5F;

			x += AoV.config_client.ELEMENT_POSITIONS.astro_x.get();
			y += AoV.config_client.ELEMENT_POSITIONS.astro_y.get();

			float scale = 0.35F;
			buffer.pos(x, y + 143F * scale, 0).tex(0, 0.5F).endVertex();
			buffer.pos(x + 235F * scale, y + 143F * scale, 0).tex(0.5F, 0.5F).endVertex();
			buffer.pos(x + 235F * scale, y, 0).tex(0.5F, 0).endVertex();
			buffer.pos(x, y, 0).tex(0, 0).endVertex();
			tess.draw();
		}
		GlStateManager.popMatrix();
	}

	private void renderFocus() {
		GlStateManager.pushMatrix();
		{
			double x = 10 + AoV.config_client.ELEMENT_POSITIONS.target_x.get();
			double y = 150 + AoV.config_client.ELEMENT_POSITIONS.target_y.get();
			double w = 100;
			double h = 41;

			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buffer = tess.getBuffer();
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
			GlStateManager.color4f(1F, 1F, 1F, 1F);
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			{

				float r = 1F;
				float g = 1F;
				float b = 1F;
				float a = 1F;

				buffer.pos(x + w, y, 0).tex(1, 0).color(r, g, b, a).endVertex();
				buffer.pos(x, y, 0).tex(0, 0).color(r, g, b, a).endVertex();
				buffer.pos(x, y + h, 0).tex(0, 1).color(r, g, b, a).endVertex();
				buffer.pos(x + w, y + h, 0).tex(1, 1).color(r, g, b, a).endVertex();

				Minecraft.getInstance().textureManager.bindTexture(AoVOverlay.TEXTURE_FOCUS);
				tess.draw();
			}
		}
		GlStateManager.popMatrix();
	}

	static class Element extends Button {

		private final float defaultX;
		private final float defaultY;
		private final float width;
		private final float height;

		private final float red;
		private final float green;
		private final float blue;

		public Element(int buttonId, float xDefault, float yDefault, int x, int y, int w, int h, int color, String buttonText) {
			super(x, y, w, h, buttonText, button -> {

			});
			defaultX = xDefault;
			defaultY = yDefault;
			width = w;
			height = h;
			red = (float) ((color >> 16) & 0xFF) / 255F;
			green = (float) ((color >> 8) & 0xFF) / 255F;
			blue = (float) (color & 0xFF) / 255F;
		}

		@Override
		public void render(int mouseX, int mouseY, float partialTicks) {
			GlStateManager.disableTexture();
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
			GlStateManager.enableTexture();
		}
	}
}
