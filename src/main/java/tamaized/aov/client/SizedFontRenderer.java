package tamaized.aov.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SizedFontRenderer extends FontRenderer { // TODO tammodized

	private static final ResourceLocation[] UNICODE_PAGE_LOCATIONS = new ResourceLocation[256];
	private float size = 1F;

	public SizedFontRenderer() {
		super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().renderEngine, false);
	}

	public SizedFontRenderer setSize(float s) {
		size = s;
		return this;
	}

	public SizedFontRenderer reset() {
		return setSize(1F);
	}

	public float getFontHeight() {
		return FONT_HEIGHT * size;
	}

	@Override
	protected float renderDefaultChar(int ch, boolean italic) {
		int i = ch % 16 * 8;
		int j = ch / 16 * 8;
		int k = italic ? 1 : 0;
		bindTexture(this.locationFontTexture);
		int l = this.charWidth[ch];
		float x = posX;
		float y = posY;
		float f = (float) l - 0.01F;
		float width = f * size;
		float height = 7.99F * size;
		GlStateManager.glBegin(5);
		GlStateManager.glTexCoord2f((float) i / 128.0F, (float) j / 128.0F);
		GlStateManager.glVertex3f(x + (float) k, y, 0.0F);
		GlStateManager.glTexCoord2f((float) i / 128.0F, ((float) j + 7.99F) / 128.0F);
		GlStateManager.glVertex3f(x - (float) k, y + height, 0.0F);
		GlStateManager.glTexCoord2f(((float) i + f - 1.0F) / 128.0F, (float) j / 128.0F);
		GlStateManager.glVertex3f(x + width - size + (float) k, y, 0.0F);
		GlStateManager.glTexCoord2f(((float) i + f - 1.0F) / 128.0F, ((float) j + 7.99F) / 128.0F);
		GlStateManager.glVertex3f(x + width - size - (float) k, y + height, 0.0F);
		GlStateManager.glEnd();
		return (float) l * size;
	}

	@Override
	protected float renderUnicodeChar(char ch, boolean italic) {
		int i = this.glyphWidth[ch] & 255;

		if (i == 0) {
			return 0.0F;
		} else {
			int j = ch / 256;
			this.loadGlyphTexture(j);
			int k = i >>> 4;
			int l = i & 15;
			float f = (float) k;
			float f1 = (float) (l + 1);
			float f2 = (float) (ch % 16 * 16) + f;
			float f3 = (float) ((ch & 255) / 16 * 16);
			float f4 = f1 - f - 0.02F;
			float f5 = italic ? 1.0F : 0.0F;
			float width = f4 * size;
			float height = 7.99F * size;
			GlStateManager.glBegin(5);
			GlStateManager.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
			GlStateManager.glVertex3f(this.posX + f5, this.posY, 0.0F);
			GlStateManager.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
			GlStateManager.glVertex3f(this.posX - f5, this.posY + height, 0.0F);
			GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
			GlStateManager.glVertex3f(this.posX + width / 2.0F + f5, this.posY, 0.0F);
			GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
			GlStateManager.glVertex3f(this.posX + width / 2.0F - f5, this.posY + height, 0.0F);
			GlStateManager.glEnd();
			return ((f1 - f) / 2.0F + 1.0F) * size;
		}
	}

	private void loadGlyphTexture(int page) {
		bindTexture(this.getUnicodePageLocation(page));
	}

	private ResourceLocation getUnicodePageLocation(int page) {
		if (UNICODE_PAGE_LOCATIONS[page] == null) {
			UNICODE_PAGE_LOCATIONS[page] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", page));
		}

		return UNICODE_PAGE_LOCATIONS[page];
	}
}
