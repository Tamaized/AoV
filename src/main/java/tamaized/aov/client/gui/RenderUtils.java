package tamaized.aov.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

	private static float zIndex = 0;

	public static void setup(float zindex) {
		zIndex = zindex;
	}

	public static void renderRect(float x, float y, float w, float h) {
		renderRect(x, y, w, h, true, 0xFFFFFFFF);
	}

	public static void renderRect(float x, float y, float w, float h, boolean texture, int color) {
		if (texture)
			renderRect(x, y, w, h, 0, 0);
		else {
			BufferBuilder buffer = Tessellator.getInstance().getBuffer();
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			setupRectVerticies(buffer, x, y, w, h, 0, 0, 0, 0, false, color);
			GlStateManager.disableTexture();
			Tessellator.getInstance().draw();
			GlStateManager.enableTexture();
		}
	}

	public static void renderRect(float x, float y, float w, float h, float u, float v) {
		renderRect(x, y, w, h, u, v, 1, 1);
	}

	public static void renderRect(float x, float y, float w, float h, float u, float v, float ue, float ve) {
		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		setupRectVerticies(buffer, x, y, w, h, u, v, ue, ve, true, 0xFFFFFFFF);
		Tessellator.getInstance().draw();
	}

	public static BufferBuilder setupRectVerticies(BufferBuilder buffer, float x, float y, float w, float h, float u, float v, float ue, float ve, boolean texture, int color) {
		int r = (color >> 24) & 0xFF;
		int g = (color >> 16) & 0xFF;
		int b = (color >> 8) & 0xFF;
		int a = color & 0xFF;
		buffer.pos(x, y + h, zIndex);
		if (texture)
			buffer.tex(u, ve);
		buffer.color(r, g, b, a).endVertex();

		buffer.pos(x + w, y + h, zIndex);
		if (texture)
			buffer.tex(ue, ve);
		buffer.color(r, g, b, a).endVertex();

		buffer.pos(x + w, y, zIndex);
		if (texture)
			buffer.tex(ue, v);
		buffer.color(r, g, b, a).endVertex();

		buffer.pos(x, y, zIndex);
		if (texture)
			buffer.tex(u, v);
		buffer.color(r, g, b, a).endVertex();

		return buffer;
	}

}
