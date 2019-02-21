package tamaized.aov.client.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntitySpellLightningStorm;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class RenderSpellLightingStorm extends Render<EntitySpellLightningStorm> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.MODID, "textures/entity/cloud.png");
	private static final ResourceLocation RAIN_TEXTURES = new ResourceLocation("textures/environment/rain.png");
	static float[] rainXCoords = new float[1024];
	static float[] rainYCoords = new float[1024];

	static {
		for (int i = 0; i < 32; ++i) {
			for (int j = 0; j < 32; ++j) {
				float f = (float) (j - 16);
				float f1 = (float) (i - 16);
				float f2 = MathHelper.sqrt(f * f + f1 * f1);
				rainXCoords[i << 5 | j] = -f1 / f2;
				rainYCoords[i << 5 | j] = f / f2;
			}
		}
	}

	Random random = new Random();
	int lastTick;
	int nextCloud = 1;

	public RenderSpellLightingStorm(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void doRender(@Nonnull EntitySpellLightningStorm entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (lastTick != entity.ticksExisted && entity.ticksExisted % nextCloud == 0) {
			nextCloud = 10 + entity.world.rand.nextInt(20);
			lastTick = entity.ticksExisted;
			entity.clouds.add(new EntitySpellLightningStorm.Cloud());
		}
		GlStateManager.pushMatrix();
		renderRain(entity, partialTicks);
		bindTexture(TEXTURE);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableLighting();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
		GlStateManager.depthMask(false);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		entity.clouds.sort((o1, o2) -> {
			boolean reverse = false;
			Entity view = Minecraft.getInstance().getRenderViewEntity();
			if (view == null) {
				view = entity;
				reverse = true;
			}
			double dist1 = view.getDistanceSq(entity.posX + o1.offset.x, entity.posY + o1.offset.y, entity.posZ + o1.offset.z);
			double dist2 = view.getDistanceSq(entity.posX + o2.offset.x, entity.posY + o2.offset.y, entity.posZ + o2.offset.z);
			return dist1 == dist2 ? 0 : reverse ? dist1 > dist2 ? 1 : -1 : dist1 > dist2 ? -1 : 1;
		});
		entity.clouds.removeIf(c -> c.render(entity, x, y, z, partialTicks, renderManager));
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.depthMask(true);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	@Nullable
	protected ResourceLocation getEntityTexture(@Nonnull EntitySpellLightningStorm entity) {
		return null;
	}

	private void renderRain(EntitySpellLightningStorm storm, float partialTicks) {
		Minecraft mc = Minecraft.getInstance();
		Entity entity = mc.getRenderViewEntity();
		if (entity == null)
			return;
		float f = 1F;

		World world = mc.world;
		int i = MathHelper.floor(entity.posX);
		int j = MathHelper.floor(entity.posY);
		int k = MathHelper.floor(entity.posZ);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.disableCull();
		GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.alphaFunc(516, 0.1F);
		double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
		int l = MathHelper.floor(d1);
		int i1 = 5;

		if (mc.gameSettings.fancyGraphics) {
			i1 = 10;
		}

		int j1 = -1;
		bufferbuilder.setTranslation(-d0, -d1, -d2);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int z = k - i1; z <= k + i1; ++z) {
			for (int x = i - i1; x <= i + i1; ++x) {
				if (x >= storm.posX - storm.width / 4F && x <= storm.posX + storm.width / 4F && z >= storm.posZ - storm.width / 4F && z <= storm.posZ + storm.width / 4F) {
					int i2 = (z - k + 16) * 32 + x - i + 16;
					double d3 = (double) rainXCoords[i2] * 0.5D;
					double d4 = (double) rainYCoords[i2] * 0.5D;
					blockpos$mutableblockpos.setPos(x, 0, z);

					int j2 = world.getPrecipitationHeight(blockpos$mutableblockpos).getY();
					int k2 = (int) Math.min(storm.posY, j - i1);
					int l2 = (int) Math.min(storm.posY, j + i1);

					if (k2 < j2) {
						k2 = j2;
					}

					if (l2 < j2) {
						l2 = j2;
					}

					int i3 = j2;

					if (j2 < l) {
						i3 = l;
					}

					if (k2 != l2) {
						this.random.setSeed((long) (x * x * 3121 + x * 45238971 ^ z * z * 418711 + z * 13761));
						blockpos$mutableblockpos.setPos(x, k2, z);

						if (j1 != 0) {
							j1 = 0;
							mc.getTextureManager().bindTexture(RAIN_TEXTURES);
							bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
						}

						double d5 = -((double) (entity.ticksExisted + x * x * 3121 + x * 45238971 + z * z * 418711 + z * 13761 & 31) + (double) partialTicks) / 32.0D * (3.0D + this.random.nextDouble());
						double d6 = (double) ((float) x + 0.5F) - entity.posX;
						double d7 = (double) ((float) z + 0.5F) - entity.posZ;
						float f3 = MathHelper.sqrt(d6 * d6 + d7 * d7) / (float) i1;
						float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F) * f;
						blockpos$mutableblockpos.setPos(x, i3, z);
						int j3 = world.getCombinedLight(blockpos$mutableblockpos, 0);
						int k3 = j3 >> 16 & 65535;
						int l3 = j3 & 65535;
						bufferbuilder.pos((double) x - d3 + 0.5D, (double) l2, (double) z - d4 + 0.5D).tex(0.0D, (double) k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
						bufferbuilder.pos((double) x + d3 + 0.5D, (double) l2, (double) z + d4 + 0.5D).tex(1.0D, (double) k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
						bufferbuilder.pos((double) x + d3 + 0.5D, (double) k2, (double) z + d4 + 0.5D).tex(1.0D, (double) l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
						bufferbuilder.pos((double) x - d3 + 0.5D, (double) k2, (double) z - d4 + 0.5D).tex(0.0D, (double) l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
					}
				}
			}
		}

		if (j1 != -1)
			tessellator.draw();

		bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.alphaFunc(516, 0.1F);
	}
}