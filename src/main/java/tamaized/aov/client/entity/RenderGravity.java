package tamaized.aov.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Shader;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GLUtil;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntityGravity;

import javax.annotation.Nonnull;
import java.util.Random;

public class RenderGravity<T extends EntityGravity> extends EntityRenderer<T> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.MODID, "textures/entity/gravity.png");
	//	public static final Sphere SPHERE = new Sphere(); TODO

	public RenderGravity(EntityRendererManager renderManager) {
		super(renderManager);
	}

	public static void drawSphere(float radius, int color) {
		float r = ((color >> 24) & 0xFF) / 255F;
		float g = ((color >> 16) & 0xFF) / 255F;
		float b = ((color >> 8) & 0xFF) / 255F;
		float a = (color & 0xFF) / 255F;
		float PI = (float) Math.PI;
		float x, y, z;
		int gradation = 50;
		Tessellator.getInstance().getBuffer().begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX/*_COLOR*/);
		for (int j = 0; j < gradation; j++) {
			float alpha1 = (float) j / gradation * PI;
			float alpha2 = (float) (j + 1) / gradation * PI;
			for (int i = 0; i <= gradation; i++) {
				float beta = (float) i / gradation * 2.0f * PI;
				x = (float) (radius * Math.cos(beta) * Math.sin(alpha1));
				y = (float) (radius * Math.sin(beta) * Math.sin(alpha1));
				z = (float) (radius * Math.cos(alpha1));
				Tessellator.getInstance().getBuffer().pos(x, y, z).tex(beta / (2.0f * PI), alpha1 / PI)/*.color(r, g, b, a)*/.endVertex();
				x = (float) (radius * Math.cos(beta) * Math.sin(alpha2));
				y = (float) (radius * Math.sin(beta) * Math.sin(alpha2));
				z = (float) (radius * Math.cos(alpha2));
				Tessellator.getInstance().getBuffer().pos(x, y, z).tex(beta / (2.0f * PI), alpha2 / PI)/*.color(r, g, b, a)*/.endVertex();
			}
		}
		Tessellator.getInstance().draw();
	}

	public static void renderSphere(float radius) {
		drawSphere(radius, 0xFFFFFFFF);
		/*SPHERE.setDrawStyle(GLU.GLU_FILL); TODO
		SPHERE.setNormals(GLU.GLU_SMOOTH);
		SPHERE.setOrientation(GLU.GLU_OUTSIDE);
		SPHERE.draw(radius, 32, 32);*/
	}

	public static void drawBoltSegment(Tessellator tessellator, Vec3d p1, Vec3d p2, float scale, int color) {
		BufferBuilder buffer = tessellator.getBuffer();

		RenderSystem.pushMatrix();
		RenderSystem.translated(p1.x, p1.y, p1.z);

		double dist = p1.distanceTo(p2);
		float xd = (float) (p1.x - p2.x);
		float yd = (float) (p1.y - p2.y);
		float zd = (float) (p1.z - p2.z);
		double var7 = (double) MathHelper.sqrt((double) (xd * xd + zd * zd));
		float rotYaw = (float) (Math.atan2((double) xd, (double) zd) * 180.0D / 3.141592653589793D);
		float rotPitch = (float) (Math.atan2((double) yd, var7) * 180.0D / 3.141592653589793D);

		RenderSystem.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
		RenderSystem.rotatef(180.0F + rotYaw, 0.0F, 0.0F, -1.0F);
		RenderSystem.rotatef(rotPitch, 1.0F, 0.0F, 0.0F);
		RenderSystem.disableCull();

		buffer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		for (int i = 0; i <= 9; i++) {
			float f = (i + 1F) / 9F;
			float verX = MathHelper.sin((float) (i % 3) * (float) Math.PI * 2F / (float) 3) * f * scale;
			float verZ = MathHelper.cos((float) (i % 3) * (float) Math.PI * 2F / (float) 3) * f * scale;

			float r = ((color >> 24) & 0xFF) / 255F;
			float g = ((color >> 16) & 0xFF) / 255F;
			float b = ((color >> 8) & 0xFF) / 255F;
			float a = ((color) & 0xFF) / 255F;
			buffer.pos(verX, dist, verZ).color(r, g, b, a).endVertex();
			buffer.pos(verX, 0, verZ).color(r, g, b, a).endVertex();
		}
		tessellator.draw();

		RenderSystem.enableCull();
		RenderSystem.popMatrix();
	}

	public static void renderBoltBetween(Vec3d point1, Vec3d point2, double scale, double maxDeflection, int maxSegments, int color) {
		Tessellator tessellator = Tessellator.getInstance();
		Random random = new Random();

		RenderSystem.disableTexture();
		RenderSystem.disableLighting();
		RenderSystem.enableBlend();
		RenderSystem.glMultiTexCoord2f(GL13.GL_TEXTURE2, 200, 200);
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

		double distance = point1.distanceTo(point2);
		Vec3d dirVec = new Vec3d(point2.x - point1.x, point2.y - point1.y, point2.z - point1.z);
		Vec3d invDir = new Vec3d(1, 1, 1).subtract(dirVec);

		Vec3d[] vectors = new Vec3d[maxSegments / 2 + random.nextInt(maxSegments / 2)];

		vectors[0] = point1;
		vectors[vectors.length - 1] = point2;

		for (int i = 1; i < vectors.length - 1; i++) {
			double pos = (i / (double) vectors.length) * distance;

			Vec3d point = new Vec3d(point1.x, point1.y, point1.z);
			point = point.add(new Vec3d(dirVec.x, dirVec.y, dirVec.z).scale(pos));

			double randX = (-0.5 + random.nextDouble()) * maxDeflection * invDir.x;
			double randY = (-0.5 + random.nextDouble()) * maxDeflection * invDir.y;
			double randZ = (-0.5 + random.nextDouble()) * maxDeflection * invDir.z;

			point = point.add(randX, randY, randZ);

			vectors[i] = point;
		}

		double rScale = scale * (0.5 + (random.nextDouble() * 0.5));
		for (int i = 1; i < vectors.length; i++) {
			drawBoltSegment(tessellator, vectors[i - 1], vectors[i], (float) rScale, color);
		}

		RenderSystem.disableBlend();
		RenderSystem.enableLighting();
		RenderSystem.enableTexture();
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		/*RenderSystem.pushMatrix(); TODO
		RenderSystem.disableCull();
		RenderSystem.disableLighting();
		RenderSystem.enableBlend();
		RenderSystem.disableDepthTest();
		float alpha = entity.spinnyBoi >= 600 ? (entity.spinnyBoi - 600F) / 420F : 0F;
		RenderSystem.color4f(0.5F, 0.5F, 0.5F, 1F);
		RenderSystem.translated(x + 0.5F, y + 5.0F, z + 0.5F);
		RenderSystem.rotatef(90F, 1.0F, 0.0F, 0.0F);
		float s = Math.min(entity.spinnyBoi / 180F, 1F);
		RenderSystem.scalef(s, s, s);
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_ALPHA);

		RenderSystem.pushMatrix();
		bindTexture(TEXTURE);
		//		SPHERE.setTextureFlag(true); TODO
		RenderSystem.rotatef((entity.spinnyBoi += Minecraft.getInstance().isGamePaused() ? 0 : (360F / (float) Minecraft.getDebugFPS())) % 360, 0, 0, 1);
		RenderSystem.alphaFunc(GL11.GL_GREATER, alpha);
		renderSphere(4F);
		RenderSystem.color4f(0.5F, 0.75F, 0.75F, 1F);
		renderSphere(1F);
		RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
		RenderSystem.rotatef(-entity.spinnyBoi % 360, 0, 0, 1);
		float scale = 0.25F;
		for (int i = 0; i <= 8; i++) {
			Vec3d rot = new Vec3d(0.75F, 2.35F, 0).rotatePitch(((i * 30F)) % 360).rotateYaw(((i * 30F)) % 360);
			rot.add(((float) i / 4F) - 1F, 0, ((float) i / 4F) - 1F);
			renderBoltBetween(rot, new Vec3d(-rot.x * scale, -rot.y * scale, -rot.z * scale), 0.015f, 0.5f, 6, 0x77AAFF20);
		}
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.popMatrix();
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		RenderSystem.enableLighting();
		RenderSystem.enableCull();
		RenderSystem.popMatrix();*/
	}

	@Override
	public ResourceLocation getEntityTexture(@Nonnull T entity) {
		return TEXTURE;
	}
}