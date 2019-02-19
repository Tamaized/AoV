package tamaized.aov.client.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntityGravity;

import javax.annotation.Nonnull;
import java.util.Random;

public class RenderGravity<T extends EntityGravity> extends Render<T> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.MODID, "textures/entity/gravity.png");
	public static final Sphere SPHERE = new Sphere();

	public RenderGravity(RenderManager renderManager) {
		super(renderManager);
	}

	public static void renderSphere(float radius) {
		SPHERE.setDrawStyle(GLU.GLU_FILL);
		SPHERE.setNormals(GLU.GLU_SMOOTH);
		SPHERE.setOrientation(GLU.GLU_OUTSIDE);
		SPHERE.draw(radius, 32, 32);
	}

	public static void drawBoltSegment(Tessellator tessellator, Vec3d p1, Vec3d p2, float scale, int color) {
		BufferBuilder buffer = tessellator.getBuffer();

		GlStateManager.pushMatrix();
		GlStateManager.translate(p1.x, p1.y, p1.z);

		double dist = p1.distanceTo(p2);
		float xd = (float) (p1.x - p2.x);
		float yd = (float) (p1.y - p2.y);
		float zd = (float) (p1.z - p2.z);
		double var7 = (double) MathHelper.sqrt((double) (xd * xd + zd * zd));
		float rotYaw = (float) (Math.atan2((double) xd, (double) zd) * 180.0D / 3.141592653589793D);
		float rotPitch = (float) (Math.atan2((double) yd, var7) * 180.0D / 3.141592653589793D);

		GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(180.0F + rotYaw, 0.0F, 0.0F, -1.0F);
		GlStateManager.rotate(rotPitch, 1.0F, 0.0F, 0.0F);
		GlStateManager.disableCull();

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

		GlStateManager.enableCull();
		GlStateManager.popMatrix();
	}

	public static void renderBoltBetween(Vec3d point1, Vec3d point2, double scale, double maxDeflection, int maxSegments, int color) {
		Tessellator tessellator = Tessellator.getInstance();
		Random random = new Random();

		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200, 200);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

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

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
	}

	@Override
	public void doRender(@Nonnull T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		float alpha = entity.spinnyBoi >= 600 ? (entity.spinnyBoi - 600F) / 420F : 0F;
		GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1F - alpha);
		GlStateManager.translate(x + 0.5F, y + 5.0F, z + 0.5F);
		GlStateManager.rotate(90F, 1.0F, 0.0F, 0.0F);
		float s = Math.min(entity.spinnyBoi / 180F, 1F);
		GlStateManager.scale(s, s, s);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_ALPHA);

		GlStateManager.pushMatrix();
		bindTexture(TEXTURE);
		SPHERE.setTextureFlag(true);
		GlStateManager.rotate((entity.spinnyBoi += Minecraft.getMinecraft().isGamePaused() ? 0 : (360F / (float) Minecraft.getDebugFPS())) % 360, 0, 0, 1);
		renderSphere(4F);
		GlStateManager.color4f(0.5F, 0.75F, 0.75F, 1F - alpha);
		renderSphere(1F);
		GlStateManager.rotate(-entity.spinnyBoi % 360, 0, 0, 1);
		float scale = 0.25F;
		for (int i = 0; i <= 8; i++) {
			Vec3d rot = new Vec3d(0.75F, 2.35F, 0).rotatePitch(((i * 30F)) % 360).rotateYaw(((i * 30F)) % 360);
			rot.add(((float) i / 4F) - 1F, 0, ((float) i / 4F) - 1F);
			renderBoltBetween(rot, new Vec3d(-rot.x * scale, -rot.y * scale, -rot.z * scale), 0.015f, 0.5f, 6, 0x77AAFF20);
		}
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.popMatrix();
		GlStateManager.color4f(1, 1, 1, 1);
		GlStateManager.enableDepth();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(@Nonnull T entity) {
		return TEXTURE;
	}
}