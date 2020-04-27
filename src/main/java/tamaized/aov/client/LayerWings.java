package tamaized.aov.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;

import javax.annotation.Nonnull;
import java.util.Random;

public class LayerWings extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

	private static final ResourceLocation TEXTURE_WING = new ResourceLocation(AoV.MODID, "textures/entity/wing.png");

	public LayerWings(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(@Nonnull AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn) {
		RenderPlayer.disableStencils();
		ILeapCapability cap = CapabilityList.getCap(player, CapabilityList.LEAP);
		if (cap == null || cap.getLeapDuration() <= 0)
			return;
		float perc = (float) cap.getLeapDuration() / (float) cap.getMaxLeapDuration();
		RenderSystem.pushMatrix();
		{
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buffer = tess.getBuffer();

			IPolymorphCapability poly = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
			boolean flag = poly != null && poly.getMorph() == IPolymorphCapability.Morph.ArchAngel;
			if (flag) {
				RenderHelper.disableStandardItemLighting();
				float f1 = 0.0F;
				if (perc > 0.8F)
					f1 = (perc - 0.8F) / 0.2F;

				Random random = new Random(432L);
				RenderSystem.disableTexture();
				RenderSystem.shadeModel(7425);
				RenderSystem.enableBlend();
				RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
				RenderSystem.disableAlphaTest();
				RenderSystem.enableCull();
				RenderSystem.depthMask(false);
				RenderSystem.pushMatrix();

				RenderSystem.translated(0.0F, 0.25F, 0.0F);
				RenderSystem.rotatef(-MathHelper.interpolateAngle(partialTicks, player.prevRenderYawOffset, player.renderYawOffset), 0, 1, 0);
				final float scale = 0.0625F;
				RenderSystem.scalef(scale, scale, scale);

				for (int i = 0; (float) i < (perc + perc * perc) / 2.0F * 60.0F; ++i) {
					RenderSystem.rotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
					RenderSystem.rotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
					RenderSystem.rotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
					RenderSystem.rotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
					RenderSystem.rotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
					RenderSystem.rotatef(random.nextFloat() * 360.0F + perc * 90.0F, 0.0F, 0.0F, 1.0F);
					float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
					float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
					buffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
					buffer.pos(0.0D, 0.0D, 0.0D).color(255, 255, 0, (int) (255.0F * (1.0F - f1))).endVertex();
					buffer.pos(-0.866D * (double) f3, (double) f2, (double) (-0.5F * f3)).color(255, 255, 255, 0).endVertex();
					buffer.pos(0.866D * (double) f3, (double) f2, (double) (-0.5F * f3)).color(255, 255, 255, 0).endVertex();
					buffer.pos(0.0D, (double) f2, (double) (1.0F * f3)).color(255, 255, 255, 0).endVertex();
					buffer.pos(-0.866D * (double) f3, (double) f2, (double) (-0.5F * f3)).color(255, 255, 255, 0).endVertex();
					tess.draw();
				}

				RenderSystem.popMatrix();
				RenderSystem.depthMask(true);
				RenderSystem.disableCull();
				RenderSystem.disableBlend();
				RenderSystem.shadeModel(7424);
				RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.enableTexture();
				RenderSystem.enableAlphaTest();
				RenderHelper.enableStandardItemLighting();
			}
			RenderSystem.enableBlend();
			RenderSystem.disableCull();
			RenderSystem.color4f(1F, 1F, 1F, perc);

			Minecraft.getInstance().textureManager.bindTexture(TEXTURE_WING);
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			double x1 = 0;
			double x2 = x1 + 1;
			double y1 = -0.55F;
			double y2 = y1 + 1;
			double z1 = 0.55F;
			double z2 = z1 - 0.5;

			buffer.pos(x2, y2, z1).tex(0, 1).endVertex();
			buffer.pos(x2, y1, z1).tex(0, 0).endVertex();
			buffer.pos(x1, y1, z2).tex(1, 0).endVertex();
			buffer.pos(x1, y2, z2).tex(1, 1).endVertex();

			double offset = -1.0;

			buffer.pos(x1 + offset, y2, z1).tex(0, 1).endVertex();
			buffer.pos(x1 + offset, y1, z1).tex(0, 0).endVertex();
			buffer.pos(x2 + offset, y1, z2).tex(1, 0).endVertex();
			buffer.pos(x2 + offset, y2, z2).tex(1, 1).endVertex();

			if (flag) {
				RenderSystem.disableLighting();
				RenderSystem.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
			}
			tess.draw();
			if (flag) {
				RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				RenderSystem.enableLighting();
			}
			RenderSystem.enableCull();
			RenderSystem.disableBlend();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		RenderSystem.popMatrix();
		IPolymorphCapability poly = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		RenderPlayer.enableStencils(poly);
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
