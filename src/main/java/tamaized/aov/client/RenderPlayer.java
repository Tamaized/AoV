package tamaized.aov.client;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.client.entity.ModelPolymorphWolf;
import tamaized.aov.client.gui.AoVOverlay;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.items.Handwraps;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Dist.CLIENT)
public class RenderPlayer {

	private static final ResourceLocation TEXTURE_WING = new ResourceLocation(AoV.MODID, "textures/entity/wing.png");
	private static final ResourceLocation TEXTURE_SUNBODY = new ResourceLocation(AoV.MODID, "textures/entity/sunbody.png");
	private static final ModelPolymorphWolf WOLF_MODEL = new ModelPolymorphWolf();
	private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf.png");
	private static boolean hackyshit = false;

	@SubscribeEvent
	public static void render(RenderPlayerEvent.Pre e) {
		PlayerEntity player = e.getPlayer();
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null) {
			if (cap.getMorph() == IPolymorphCapability.Morph.Wolf) { // TODO
				/*RenderSystem.pushMatrix();
				e.getRenderer().renderName((AbstractClientPlayerEntity) player, e.getX(), e.getY(), e.getZ());
				RenderSystem.translated(e.getX(), e.getY(), e.getZ());
				float swingProgress = player.limbSwing - player.limbSwingAmount * (1.0F - e.getPartialRenderTick());//e.getRenderer().getMainModel().swingProgress;
				float swingAmount = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * e.getPartialRenderTick();
				if (swingAmount > 1.0F)
					swingAmount = 1.0F;
				float f = interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, e.getPartialRenderTick());
				float f1 = interpolateRotation(player.prevRotationYawHead, player.rotationYawHead, e.getPartialRenderTick());
				float netHeadYaw = f1 - f;
				float headPitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * e.getPartialRenderTick();
				float ticksExisted = 0.53F * (float) Math.PI;
				applyRotations(player, ticksExisted, f, e.getPartialRenderTick());
				float scale = e.getRenderer().prepareScale((AbstractClientPlayerEntity) player, e.getPartialRenderTick());
				WOLF_MODEL.setLivingAnimations(null, swingProgress, swingAmount, e.getPartialRenderTick());
				WOLF_MODEL.setRotationAngles(null, swingProgress, swingAmount, ticksExisted, netHeadYaw, headPitch, scale);
				e.getRenderer().bindTexture(WOLF_TEXTURES);
				WOLF_MODEL.render(null, swingProgress, swingAmount, ticksExisted, netHeadYaw, headPitch, scale);
				RenderSystem.popMatrix();
				e.setCanceled(true);*/
			}
		}
	}

	/*@SubscribeEvent TODO
	public static void renderName(RenderLivingEvent.Specials.Pre<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity>> e) {
		if (hackyshit)
			return;
		IPolymorphCapability cap = CapabilityList.getCap(e.getEntity(), CapabilityList.POLYMORPH);
		if (cap != null && (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental || cap.getMorph() == IPolymorphCapability.Morph.FireElemental || cap.getMorph() == IPolymorphCapability.Morph.ArchAngel))
			e.setCanceled(true);
	}*/

	@SubscribeEvent
	public static void renderLiving(RenderLivingEvent.Pre<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity>> e) {
		RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		if (!(e.getEntity() instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) e.getEntity();
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null) {
			RenderSystem.enableBlend();
			enableStencils(cap);
		}
	}

	@SubscribeEvent
	public static void renderLiving(RenderLivingEvent.Post<PlayerEntity, PlayerModel<PlayerEntity>> e) {
		if (!(e.getEntity() instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) e.getEntity();
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null) {
			if (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental || cap.getMorph() == IPolymorphCapability.Morph.FireElemental || cap.getMorph() == IPolymorphCapability.Morph.ArchAngel) {
				if (AoVOverlay.NO_STENCIL) {
					RenderSystem.color4f(1F, 1F, 1F, 1F);
				} else {
					disableStencils();
					hackyshit = true;
					//e.getRenderer().renderName(player, e.getX(), e.getY(), e.getZ()); TODO
					hackyshit = false;
				}
				IAoVCapability aov = CapabilityList.getCap(player, CapabilityList.AOV);
				if (aov != null && cap.getMorph() == IPolymorphCapability.Morph.FireElemental && aov.isAuraActive(Abilities.elementalEmpowerment)) {
					/*RenderSystem.pushMatrix(); TODO
					{
						RenderSystem.translated(e.getX(), e.getY(), e.getZ());
						Minecraft.getInstance().textureManager.bindTexture(TEXTURE_SUNBODY);

						Tessellator tess = Tessellator.getInstance();
						BufferBuilder buffer = tess.getBuffer();

						RenderSystem.translated(0, player.getEyeHeight() / 1.5F, 0);
						RenderSystem.rotatef(180.0F - e.getRenderer().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
						RenderSystem.rotatef((float) (e.getRenderer().getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * -e.getRenderer().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);

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
							RenderSystem.pushMatrix();
							RenderSystem.rotatef((2 + (index * 3)) * player.ticksExisted + e.getPartialRenderTick(), 0, 0, ((index & 1) == 0 ? 1 : -1));

							RenderSystem.disableLighting();
							int j = 0xF000F0 % 0x10000;
							int k = 0xF000F0 / 0x10000;
							GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) j, (float) k);
							RenderSystem.enableBlend();
							tess.draw();
							RenderSystem.popMatrix();
							RenderSystem.enableLighting();

							size -= 0.25F;
							z += 0.0001F;
						}
					}
					RenderSystem.popMatrix();*/
				}
				RenderSystem.disableBlend();
			}
		}
	}

	public static void enableStencils(IPolymorphCapability cap) {
		if (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental || cap.getMorph() == IPolymorphCapability.Morph.FireElemental || cap.getMorph() == IPolymorphCapability.Morph.ArchAngel) {
			if (AoVOverlay.NO_STENCIL) {
				if (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental)
					RenderSystem.color4f(0F, 0.5F, 1F, 0.75F);
				else if (cap.getMorph() == IPolymorphCapability.Morph.FireElemental)
					RenderSystem.color4f(1F, 0.75F, 0F, 0.75F);
				else if (cap.getMorph() == IPolymorphCapability.Morph.ArchAngel)
					RenderSystem.color4f(1F, 1F, 0F, 0.75F);
			} else {
				RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ZERO);
				GL11.glEnable(GL11.GL_STENCIL_TEST);
				GL11.glStencilMask(0xFF);
				GL11.glStencilFunc(GL11.GL_ALWAYS, (

						AoV.config_client.stencil.get() + (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental ? 8 :

								cap.getMorph() == IPolymorphCapability.Morph.FireElemental ? 9 : 10

						) + (AoVOverlay.hackyshit ? 3 : 0)), 0xFF);
				GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
			}
		}
	}

	public static void disableStencils() {
		GL11.glStencilFunc(GL11.GL_ALWAYS, 0, 0xFF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
		GL11.glStencilMask(0x00);
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	@SubscribeEvent
	public static void render(RenderHandEvent e) {
		PlayerEntity player = Minecraft.getInstance().player;
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null) {
			if (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental || cap.getMorph() == IPolymorphCapability.Morph.FireElemental || cap.getMorph() == IPolymorphCapability.Morph.ArchAngel) {
				Minecraft mc = Minecraft.getInstance();
				boolean flag = mc.getRenderViewEntity() instanceof LivingEntity && ((LivingEntity) mc.getRenderViewEntity()).isSleeping();
				if (mc.gameSettings.thirdPersonView == 0 && !flag && !mc.gameSettings.hideGUI && !mc.playerController.isSpectatorMode()) {
					/*mc.gameRenderer.enableLightmap(); TODO
					RenderSystem.enableBlend();
					if (AoVOverlay.NO_STENCIL) {
						if (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental)
							RenderSystem.color4f(0F, 0.5F, 1F, 0.75F);
						else if (cap.getMorph() == IPolymorphCapability.Morph.FireElemental)
							RenderSystem.color4f(1F, 0.75F, 0F, 0.75F);
						else if (cap.getMorph() == IPolymorphCapability.Morph.FireElemental)
							RenderSystem.color4f(1F, 1F, 0F, 0.75F);
					} else {
						e.setCanceled(true);
						RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ZERO);
						GL11.glEnable(GL11.GL_STENCIL_TEST);
						GL11.glStencilMask(0xFF);
						GL11.glStencilFunc(GL11.GL_ALWAYS,

								AoV.config_client.stencil.get() + (cap.getMorph() == IPolymorphCapability.Morph.WaterElemental ? 8 :

										cap.getMorph() == IPolymorphCapability.Morph.FireElemental ? 9 : 10),

								0xFF);
						GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
						mc.gameRenderer.itemRenderer.renderItemInFirstPerson(e.getPartialTicks());
						GL11.glStencilFunc(GL11.GL_ALWAYS, 0, 0xFF);
						GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
						GL11.glStencilMask(0x00);
						GL11.glDisable(GL11.GL_STENCIL_TEST);
						RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					}
					RenderSystem.color4f(1F, 1F, 1F, 1F);
					RenderSystem.disableBlend();
					mc.gameRenderer.disableLightmap();*/
				}
			}
		}
	}

	/*@SubscribeEvent TODO
	public static void render(RenderSpecificHandEvent e) {
		AbstractClientPlayerEntity player = Minecraft.getInstance().player;
		IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (cap != null && cap.getMorph() == IPolymorphCapability.Morph.Wolf) {
			e.setCanceled(true);
			boolean flag = (e.getHand() == Hand.MAIN_HAND ? player.getPrimaryHand() : player.getPrimaryHand().opposite()) != HandSide.LEFT;
			float f = flag ? 1.0F : -1.0F;
			float f1 = MathHelper.sqrt(e.getSwingProgress());
			float f2 = -0.3F * MathHelper.sin(f1 * (float) Math.PI);
			float f3 = 0.4F * MathHelper.sin(f1 * ((float) Math.PI * 2F));
			float f4 = -0.4F * MathHelper.sin(e.getSwingProgress() * (float) Math.PI);
			RenderSystem.translated(f * (f2 + 0.64000005F), f3 + -0.6F + e.getEquipProgress() * -0.6F, f4 + -0.71999997F);
			RenderSystem.rotatef(f * 45.0F, 0.0F, 1.0F, 0.0F);
			float f5 = MathHelper.sin(e.getSwingProgress() * e.getSwingProgress() * (float) Math.PI);
			float f6 = MathHelper.sin(f1 * (float) Math.PI);
			RenderSystem.rotatef(f * f6 * 40.0F, 0.0F, 1.0F, 0.0F);
			RenderSystem.rotatef(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
			AbstractClientPlayerEntity abstractclientplayer = Minecraft.getInstance().player;
			Minecraft.getInstance().getTextureManager().bindTexture(WOLF_TEXTURES);
			RenderSystem.translated(f * -1.0F, 3.6F, 3.5F);
			RenderSystem.rotatef(f * 120.0F, 0.0F, 0.0F, 1.0F);
			RenderSystem.rotatef(200.0F, 1.0F, 0.0F, 0.0F);
			RenderSystem.rotatef(f * -135.0F, 0.0F, 1.0F, 0.0F);
			RenderSystem.translated(f * 5.6F, 0.0F, 0.0F);
			RenderSystem.disableCull();
			renderRightArm(abstractclientplayer);
			RenderSystem.enableCull();
		} else {
			if (e.getItemStack().getItem() instanceof Handwraps && e.getHand() == Hand.MAIN_HAND) {
				Minecraft mc = Minecraft.getInstance();
				float f = player.getSwingProgress(e.getPartialTicks());
				Hand enumhand = MoreObjects.firstNonNull(player.swingingHand, Hand.MAIN_HAND);
				float f3 = enumhand == e.getHand() ? f : 0.0F;
				float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * e.getPartialTicks();
				mc.getFirstPersonRenderer().renderItemInFirstPerson(player, e.getPartialTicks(), f1, e.getHand(), f3, ItemStack.EMPTY, 0F);
				e.setCanceled(true);
			}
		}
	}*/

	/*public static void renderRightArm(AbstractClientPlayerEntity clientPlayer) { TODO
		float f = 1.0F;
		RenderSystem.color3f(f, f, f);
		float f1 = 0.0625F;
		RenderSystem.enableBlend();
		RenderSystem.pushMatrix();
		float scale = 3.0F;
		RenderSystem.scalef(scale, scale, scale);
		RenderSystem.translated(0.25F, -0.65F, 0F);
		WOLF_MODEL.swingProgress = 0.0F;
		WOLF_MODEL.setRotationAngles(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f1);
		WOLF_MODEL.legBackLeft.rotateAngleX = 0.0F;
		WOLF_MODEL.legBackLeft.render(f1);
		RenderSystem.popMatrix();
		RenderSystem.disableBlend();
	}*/

	/*
	 * [Vanilla Copy] from RenderLivingBase
	 */
	protected static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
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
	protected static void applyRotations(LivingEntity entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
		RenderSystem.rotatef(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);

		if (entityLiving.deathTime > 0) {
			float f = ((float) entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = MathHelper.sqrt(f);

			if (f > 1.0F) {
				f = 1.0F;
			}

			RenderSystem.rotatef(f * 90, 0.0F, 0.0F, 1.0F);
		} else {
			String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName().getString());

			if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof PlayerEntity) || ((PlayerEntity) entityLiving).isWearing(PlayerModelPart.CAPE))) {
				RenderSystem.translated(0.0F, entityLiving.getHeight() + 0.1F, 0.0F);
				RenderSystem.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
			}
		}
	}

}
