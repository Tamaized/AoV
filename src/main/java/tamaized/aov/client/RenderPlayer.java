package tamaized.aov.client;

import com.google.common.base.MoreObjects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.client.entity.ModelPolymorphWolf;
import tamaized.aov.client.gui.AoVOverlay;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.items.Handwraps;

import java.util.Random;

public class RenderPlayer {

	private static final ResourceLocation TEXTURE_WING = new ResourceLocation(AoV.MODID, "textures/entity/wing.png");
	private static final ResourceLocation TEXTURE_SUNBODY = new ResourceLocation(AoV.MODID, "textures/entity/sunbody.png");
	private static final ModelPolymorphWolf WOLF_MODEL = new ModelPolymorphWolf();
	private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf.png");
	private static boolean hackyshit = false;

	@SubscribeEvent
	public void render(RenderPlayerEvent.Pre e) {
		EntityPlayer player = e.getEntityPlayer();
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null) {
			if (cap.getMorph() == IPolymorphCapability.Morph.Wolf) {
				GlStateManager.pushMatrix();
				e.getRenderer().renderName((AbstractClientPlayer) player, e.getX(), e.getY(), e.getZ());
				GlStateManager.translated(e.getX(), e.getY(), e.getZ());
				float swingProgress = player.limbSwing - player.limbSwingAmount * (1.0F - e.getPartialRenderTick());//e.getRenderer().getMainModel().swingProgress;
				float swingAmount = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * e.getPartialRenderTick();
				if (swingAmount > 1.0F)
					swingAmount = 1.0F;
				float f = this.interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, e.getPartialRenderTick());
				float f1 = this.interpolateRotation(player.prevRotationYawHead, player.rotationYawHead, e.getPartialRenderTick());
				float netHeadYaw = f1 - f;
				float headPitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * e.getPartialRenderTick();
				float ticksExisted = 0.53F * (float) Math.PI;
				applyRotations(player, ticksExisted, f, e.getPartialRenderTick());
				float scale = e.getRenderer().prepareScale((AbstractClientPlayer) player, e.getPartialRenderTick());
				WOLF_MODEL.setLivingAnimations(player, swingProgress, swingAmount, e.getPartialRenderTick());
				WOLF_MODEL.setRotationAngles(swingProgress, swingAmount, ticksExisted, netHeadYaw, headPitch, scale, player);
				e.getRenderer().bindTexture(WOLF_TEXTURES);
				WOLF_MODEL.render(player, swingProgress, swingAmount, ticksExisted, netHeadYaw, headPitch, scale);
				GlStateManager.popMatrix();
				e.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void render(RenderPlayerEvent.Post e) {
		EntityPlayer player = e.getEntityPlayer();
		ILeapCapability cap = CapabilityList.getCap(player, CapabilityList.LEAP);
		if (cap == null || cap.getLeapDuration() <= 0)
			return;
		float perc = (float) cap.getLeapDuration() / (float) cap.getMaxLeapDuration();
		GlStateManager.pushMatrix();
		{
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buffer = tess.getBuffer();

			IPolymorphCapability poly = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
			boolean flag = poly != null && poly.getMorph() == IPolymorphCapability.Morph.ArchAngel;
			if (flag) {
				RenderHelper.disableStandardItemLighting();
				float f =  perc;
				float f1 = 0.0F;

				if (f > 0.8F) {
					f1 = (f - 0.8F) / 0.2F;
				}

				Random random = new Random(432L);
				GlStateManager.disableTexture2D();
				GlStateManager.shadeModel(7425);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
				GlStateManager.disableAlphaTest();
				GlStateManager.enableCull();
				GlStateManager.depthMask(false);
				GlStateManager.pushMatrix();
				GlStateManager.translated(e.getX(), e.getY(), e.getZ());
				GlStateManager.translated(0.0F, 1.0F, 0.0F);
				final float scale = 0.0625F;
				GlStateManager.scalef(scale, scale, scale);

				for (int i = 0; (float) i < (f + f * f) / 2.0F * 60.0F; ++i) {
					GlStateManager.rotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
					GlStateManager.rotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
					GlStateManager.rotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
					GlStateManager.rotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
					GlStateManager.rotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
					GlStateManager.rotatef(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
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

				GlStateManager.popMatrix();
				GlStateManager.depthMask(true);
				GlStateManager.disableCull();
				GlStateManager.disableBlend();
				GlStateManager.shadeModel(7424);
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.enableTexture2D();
				GlStateManager.enableAlphaTest();
				RenderHelper.enableStandardItemLighting();
			}
			GlStateManager.enableBlend();
			GlStateManager.disableCull();
			GlStateManager.color4f(1, 1, 1, perc);
			float scale = 1;
			GlStateManager.translated(e.getX(), e.getY(), e.getZ());
			GlStateManager.scalef(scale, scale, scale);
			GlStateManager.rotatef(-player.renderYawOffset, 0, 1, 0);
			if (player.isSneaking()) {
				GlStateManager.translated(0.0F, -0.2F, 0.0F);
			}
			Minecraft.getInstance().textureManager.bindTexture(TEXTURE_WING);
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			double x1 = 0;
			double x2 = x1 + 1;
			double y1 = 1.90;
			double y2 = y1 - 1;
			double z1 = -0.05;
			double z2 = z1 - 0.5;

			buffer.pos(x1, y1, z1).tex(1, 0).endVertex();
			buffer.pos(x2, y1, z2).tex(0, 0).endVertex();
			buffer.pos(x2, y2, z2).tex(0, 1).endVertex();
			buffer.pos(x1, y2, z1).tex(1, 1).endVertex();

			double offset = -1.0;

			buffer.pos(x2 + offset, y1, z1).tex(1, 0).endVertex();
			buffer.pos(x1 + offset, y1, z2).tex(0, 0).endVertex();
			buffer.pos(x1 + offset, y2, z2).tex(0, 1).endVertex();
			buffer.pos(x2 + offset, y2, z1).tex(1, 1).endVertex();

			if (flag) {
				GlStateManager.disableLighting();
				GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
			}
			tess.draw();
			if (flag) {
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.enableLighting();
			}
			GlStateManager.enableCull();
			GlStateManager.disableBlend();
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		GlStateManager.popMatrix();
	}

	@SubscribeEvent
	public void renderName(RenderLivingEvent.Specials.Pre<EntityLivingBase> e) {
		if (hackyshit)
			return;
		IPolymorphCapability cap = CapabilityList.getCap(e.getEntity(), CapabilityList.POLYMORPH);
		if (cap != null && (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental || cap.getMorph() == IPolymorphCapability.Morph.FireElemental || cap.getMorph() == IPolymorphCapability.Morph.ArchAngel))
			e.setCanceled(true);
	}

	@SubscribeEvent
	public void renderLiving(RenderLivingEvent.Pre<EntityPlayer> e) {
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		if (!(e.getEntity() instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) e.getEntity();
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null) {
			if (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental || cap.getMorph() == IPolymorphCapability.Morph.FireElemental || cap.getMorph() == IPolymorphCapability.Morph.ArchAngel) {
				GlStateManager.enableBlend();
				if (AoVOverlay.NO_STENCIL) {
					if (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental)
						GlStateManager.color4f(0F, 0.5F, 1F, 0.75F);
					else if (cap.getMorph() == IPolymorphCapability.Morph.FireElemental)
						GlStateManager.color4f(1F, 0.75F, 0F, 0.75F);
					else if (cap.getMorph() == IPolymorphCapability.Morph.ArchAngel)
						GlStateManager.color4f(1F, 1F, 0F, 0.75F);
				} else {
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ZERO);
					GL11.glEnable(GL11.GL_STENCIL_TEST);
					GL11.glStencilMask(0xFF);
					GL11.glStencilFunc(GL11.GL_ALWAYS, (

							cap.getMorph() == IPolymorphCapability.Morph.WaterElemental ? 8 :

									cap.getMorph() == IPolymorphCapability.Morph.FireElemental ? 9 : 10

					) + (AoVOverlay.hackyshit ? 3 : 0), 0xFF);
					GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
				}
			}
		}
	}

	@SubscribeEvent
	public void renderLiving(RenderLivingEvent.Post<EntityPlayer> e) {
		if (!(e.getEntity() instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) e.getEntity();
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null) {
			if (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental || cap.getMorph() == IPolymorphCapability.Morph.FireElemental || cap.getMorph() == IPolymorphCapability.Morph.ArchAngel) {
				if (AoVOverlay.NO_STENCIL) {
					GlStateManager.color4f(1F, 1F, 1F, 1F);
				} else {
					//				GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ClientProxy.FRAME_BUFFER);
					GL11.glStencilMask(0x00);
					GL11.glDisable(GL11.GL_STENCIL_TEST);
					GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					hackyshit = true;
					e.getRenderer().renderName(player, e.getX(), e.getY(), e.getZ());
					hackyshit = false;
				}
				IAoVCapability aov = CapabilityList.getCap(player, CapabilityList.AOV);
				if (aov != null && cap.getMorph() == IPolymorphCapability.Morph.FireElemental && aov.isAuraActive(Abilities.elementalEmpowerment)) {
					GlStateManager.pushMatrix();
					{
						GlStateManager.translated(e.getX(), e.getY(), e.getZ());
						Minecraft.getInstance().textureManager.bindTexture(TEXTURE_SUNBODY);

						Tessellator tess = Tessellator.getInstance();
						BufferBuilder buffer = tess.getBuffer();

						GlStateManager.translated(0, player.eyeHeight / 1.5F, 0);
						GlStateManager.rotatef(180.0F - e.getRenderer().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
						GlStateManager.rotatef((float) (e.getRenderer().getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * -e.getRenderer().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);

						float r = 1.0F;
						float g = 0.35F;
						float b = 0.0F;
						float a = 1.0F;

						float size = 1.5F;

						float z = 0;

						for (int index = 0; index < 3; index++) {
							buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

							buffer.pos(-size, -size, z).tex(0, 0).color(r, g, b, a).endVertex();
							buffer.pos(size, -size, z).tex(1, 0).color(r, g, b, a).endVertex();
							buffer.pos(size, size, z).tex(1, 1).color(r, g, b, a).endVertex();
							buffer.pos(-size, size, z).tex(0, 1).color(r, g, b, a).endVertex();
							GlStateManager.pushMatrix();
							GlStateManager.rotatef((2 + (index * 3)) * player.ticksExisted + e.getPartialRenderTick(), 0, 0, ((index & 1) == 0 ? 1 : -1));

							GlStateManager.disableLighting();
							int j = 0xF000F0 % 0x10000;
							int k = 0xF000F0 / 0x10000;
							OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
							GlStateManager.enableBlend();
							tess.draw();
							GlStateManager.popMatrix();
							GlStateManager.enableLighting();

							size -= 0.25F;
							z += 0.0001F;
						}
					}
					GlStateManager.popMatrix();
				}
				GlStateManager.disableBlend();
			}
		}
	}

	@SubscribeEvent
	public void render(RenderHandEvent e) {
		EntityPlayer player = Minecraft.getInstance().player;
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null) {
			if (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental || cap.getMorph() == IPolymorphCapability.Morph.FireElemental || cap.getMorph() == IPolymorphCapability.Morph.ArchAngel) {
				Minecraft mc = Minecraft.getInstance();
				boolean flag = mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase) mc.getRenderViewEntity()).isPlayerSleeping();
				if (mc.gameSettings.thirdPersonView == 0 && !flag && !mc.gameSettings.hideGUI && !mc.playerController.isSpectator()) {
					mc.entityRenderer.enableLightmap();
					GlStateManager.enableBlend();
					if (AoVOverlay.NO_STENCIL) {
						if (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental)
							GlStateManager.color4f(0F, 0.5F, 1F, 0.75F);
						else if (cap.getMorph() == IPolymorphCapability.Morph.FireElemental)
							GlStateManager.color4f(1F, 0.75F, 0F, 0.75F);
						else if (cap.getMorph() == IPolymorphCapability.Morph.FireElemental)
							GlStateManager.color4f(1F, 1F, 0F, 0.75F);
					} else {
						e.setCanceled(true);
						GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ZERO);
						GL11.glEnable(GL11.GL_STENCIL_TEST);
						GL11.glStencilMask(0xFF);
						GL11.glStencilFunc(GL11.GL_ALWAYS,

								cap.getMorph() == IPolymorphCapability.Morph.WaterElemental ? 8 :

										cap.getMorph() == IPolymorphCapability.Morph.FireElemental ? 9 : 10,

								0xFF);
						GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
						mc.entityRenderer.itemRenderer.renderItemInFirstPerson(e.getPartialTicks());
						GL11.glStencilMask(0x00);
						GL11.glDisable(GL11.GL_STENCIL_TEST);
						GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					}
					GlStateManager.color4f(1F, 1F, 1F, 1F);
					GlStateManager.disableBlend();
					mc.entityRenderer.disableLightmap();
				}
			}
		}
	}

	@SubscribeEvent
	public void render(RenderSpecificHandEvent e) {
		AbstractClientPlayer player = Minecraft.getInstance().player;
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null && cap.getMorph() == IPolymorphCapability.Morph.Wolf) {
			e.setCanceled(true);
			boolean flag = (e.getHand() == EnumHand.MAIN_HAND ? player.getPrimaryHand() : player.getPrimaryHand().opposite()) != EnumHandSide.LEFT;
			float f = flag ? 1.0F : -1.0F;
			float f1 = MathHelper.sqrt(e.getSwingProgress());
			float f2 = -0.3F * MathHelper.sin(f1 * (float) Math.PI);
			float f3 = 0.4F * MathHelper.sin(f1 * ((float) Math.PI * 2F));
			float f4 = -0.4F * MathHelper.sin(e.getSwingProgress() * (float) Math.PI);
			GlStateManager.translated(f * (f2 + 0.64000005F), f3 + -0.6F + e.getEquipProgress() * -0.6F, f4 + -0.71999997F);
			GlStateManager.rotatef(f * 45.0F, 0.0F, 1.0F, 0.0F);
			float f5 = MathHelper.sin(e.getSwingProgress() * e.getSwingProgress() * (float) Math.PI);
			float f6 = MathHelper.sin(f1 * (float) Math.PI);
			GlStateManager.rotatef(f * f6 * 40.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotatef(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
			AbstractClientPlayer abstractclientplayer = Minecraft.getInstance().player;
			Minecraft.getInstance().getTextureManager().bindTexture(WOLF_TEXTURES);
			GlStateManager.translated(f * -1.0F, 3.6F, 3.5F);
			GlStateManager.rotatef(f * 120.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotatef(200.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotatef(f * -135.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translated(f * 5.6F, 0.0F, 0.0F);
			GlStateManager.disableCull();
			renderRightArm(abstractclientplayer);
			GlStateManager.enableCull();
		} else {
			if (e.getItemStack().getItem() instanceof Handwraps && e.getHand() == EnumHand.MAIN_HAND) {
				Minecraft mc = Minecraft.getInstance();
				float f = player.getSwingProgress(e.getPartialTicks());
				EnumHand enumhand = MoreObjects.firstNonNull(player.swingingHand, EnumHand.MAIN_HAND);
				float f3 = enumhand == e.getHand() ? f : 0.0F;
				float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * e.getPartialTicks();
				mc.getItemRenderer().renderItemInFirstPerson(player, e.getPartialTicks(), f1, e.getHand(), f3, ItemStack.EMPTY, 0F);
				e.setCanceled(true);
			}
		}
	}

	public void renderRightArm(AbstractClientPlayer clientPlayer) {
		float f = 1.0F;
		GlStateManager.color4f(f, f, f);
		float f1 = 0.0625F;
		GlStateManager.enableBlend();
		GlStateManager.pushMatrix();
		float scale = 3.0F;
		GlStateManager.scalef(scale, scale, scale);
		GlStateManager.translated(0.25F, -0.65F, 0F);
		WOLF_MODEL.swingProgress = 0.0F;
		WOLF_MODEL.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f1, clientPlayer);
		WOLF_MODEL.wolfLeg2.rotateAngleX = 0.0F;
		WOLF_MODEL.wolfLeg2.render(f1);
		GlStateManager.popMatrix();
		GlStateManager.disableBlend();
	}

	/*
	 * [Vanilla Copy] from RenderLivingBase
	 */
	protected float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
		float f;

		for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F) {
			;
		}

		while (f >= 180.0F) {
			f -= 360.0F;
		}

		return prevYawOffset + partialTicks * f;
	}

	/*
	 * [Vanilla Copy] from RenderLivingBase
	 */
	protected void applyRotations(EntityLivingBase entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
		GlStateManager.rotatef(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);

		if (entityLiving.deathTime > 0) {
			float f = ((float) entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = MathHelper.sqrt(f);

			if (f > 1.0F) {
				f = 1.0F;
			}

			GlStateManager.rotatef(f * 90, 0.0F, 0.0F, 1.0F);
		} else {
			String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());

			if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof EntityPlayer) || ((EntityPlayer) entityLiving).isWearing(EnumPlayerModelParts.CAPE))) {
				GlStateManager.translated(0.0F, entityLiving.height + 0.1F, 0.0F);
				GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
			}
		}
	}

}
