package tamaized.aov.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import tamaized.aov.AoV;
import tamaized.aov.client.ClientHelpers;
import tamaized.aov.client.handler.ClientTicker;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.proxy.ClientProxy;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AoVOverlay extends Gui {

	public static final ResourceLocation TEXTURE_ASTRO = new ResourceLocation(AoV.modid, "textures/gui/astro.png");
	public static final ResourceLocation TEXTURE_FOCUS = new ResourceLocation(AoV.modid, "textures/gui/focus.png");
	public static final ResourceLocation TEXTURE_DOGGO = new ResourceLocation(AoV.modid, "textures/gui/doggo.png");
	private static final ResourceLocation ELEMENTAL_TEXTURES = new ResourceLocation(AoV.modid, "textures/entity/fluid.png");
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static boolean hackyshit = false;
	private static EntityLivingBase cacheEntity;
	private static int cacheEntityID = -1;

	@SubscribeEvent
	public void renderOverlayPre(RenderGameOverlayEvent.Pre e) {
		if (e.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
			IPolymorphCapability poly = mc.player.hasCapability(CapabilityList.POLYMORPH, null) ? mc.player.getCapability(CapabilityList.POLYMORPH, null) : null;
			if (poly != null) {
				if (poly.getMorph() == IPolymorphCapability.Morph.Wolf) {
					e.setCanceled(true);
					float perc = poly.getAttackCooldown() / poly.getInitalAttackCooldown();
					if (perc > 0F) {
						GlStateManager.pushMatrix();
						{
							Tessellator tessellator = Tessellator.getInstance();
							BufferBuilder buffer = tessellator.getBuffer();
							buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
							ScaledResolution resolution = e.getResolution();
							float scale = 3F;
							float w = 89F / scale;
							float h = 54F / scale;
							float x = (float) (resolution.getScaledWidth_double() / 2F) - (w / 2F);
							float y = (float) (resolution.getScaledHeight_double() - 12F) - (h / 2F);
							float alpha = 0.35F;
							float tone = 0.75F;
							float heightscale = perc * h;
							buffer.pos(x, y + heightscale, 0F).tex(0, perc).color(tone, tone, tone, alpha).endVertex();
							buffer.pos(x, y + h, 0F).tex(0, 1).color(tone, tone, tone, alpha).endVertex();
							buffer.pos(x + w, y + h, 0F).tex(1, 1).color(tone, tone, tone, alpha).endVertex();
							buffer.pos(x + w, y + heightscale, 0F).tex(1, perc).color(tone, tone, tone, alpha).endVertex();
							mc.renderEngine.bindTexture(TEXTURE_DOGGO);
							tessellator.draw();
						}
						GlStateManager.popMatrix();
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void renderOverlayPost(RenderGameOverlayEvent.Post e) {
		if (e.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) // TODO: ??? shouldnt this be hotbar? recheck it later.
			return;
		ClientTicker.update();
		IAoVCapability cap = mc.player.getCapability(CapabilityList.AOV, null);
		FontRenderer fontRender = mc.fontRenderer;
		ScaledResolution sr = new ScaledResolution(mc);
		int sW = sr.getScaledWidth() / 2;

		if (cap != null && cap.hasCoreSkill()) {
			if (ClientProxy.barToggle) {
				GlStateManager.pushMatrix();
				{
					if (ConfigHandler.renderBarOverHotbar)
						GlStateManager.translate(0, sr.getScaledHeight() - 23, 0);
					for (int i = 0; i < 9; i++) {
						int x = sW - 90 + (20 * i);
						int y = ConfigHandler.renderBarOverHotbar || ConfigHandler.renderChargesAboveSpellbar ? 1 - ClientTicker.charges.getValue(i) : 1 + ClientTicker.charges.getValue(i);
						renderCharges(x + ConfigHandler.elementPositions.spellbar_x, y + ConfigHandler.elementPositions.spellbar_y, fontRender, cap, i);
					}
				}
				GlStateManager.popMatrix();
			}

			AoVUIBar.render(this, ConfigHandler.elementPositions.spellbar_x, ConfigHandler.elementPositions.spellbar_y);
			if (cap.getCoreSkill() == AoVSkills.astro_core_1)
				renderAstro(mc.player, sr);
			Entity target = ClientProxy.getTarget() != null ? ClientProxy.getTarget() : ClientHelpers.getTargetOverMouse(mc, 128);
			if (ConfigHandler.renderTarget && target instanceof EntityLivingBase)
				renderTarget((EntityLivingBase) target);
		}
	}

	@SubscribeEvent
	public void render(TickEvent.RenderTickEvent e) {
		if (e.phase == TickEvent.Phase.END && mc.currentScreen != null) {
			renderStencils();
		}
		hackyshit = false;
	}

	@SubscribeEvent
	public void render(RenderWorldLastEvent e) {
		renderStencils();
		hackyshit = true;
	}

	private void renderStencils() {
		if (mc.world != null) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ELEMENTAL_TEXTURES);
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buffer = tess.getBuffer();
			ScaledResolution resolution = new ScaledResolution(mc);
			float w = resolution.getScaledWidth();
			float h = resolution.getScaledHeight();
			float scale = 32F;
			float u = 1F / (scale / w);
			float v = 1F / (scale / h);
			Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
			//			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			GlStateManager.color(1F, 1F, 1F, 1F);
			GL11.glEnable(GL11.GL_STENCIL_TEST);

			GL11.glStencilFunc(GL11.GL_EQUAL, hackyshit ? 10 : 8, 0xFF); // Water
			{
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
				buffer.pos(0, h, 0).tex(0, v).color(0F, 0.5F, 0.75F, 0.75F).endVertex();
				buffer.pos(w, h, 0).tex(u, v).color(0F, 0.5F, 0.5F, 0.75F).endVertex();
				buffer.pos(w, 0, 0).tex(u, 0).color(0F, 0.5F, 1F, 0.75F).endVertex();
				buffer.pos(0, 0, 0).tex(0, 0).color(0F, 0F, 1F, 1F).endVertex();

				GlStateManager.pushMatrix();
				float f = (float) mc.world.getTotalWorldTime() + (mc.isGamePaused() ? 0 : mc.getRenderPartialTicks());
				GlStateManager.matrixMode(GL11.GL_TEXTURE);
				GlStateManager.loadIdentity();
				GlStateManager.translate(f * 0.001F, f * -0.01F, 0.0F);
				GlStateManager.scale(0.5F, 0.5F, 0.5F);
				GlStateManager.rotate(f * 0.1F, 0, 1, 0);
				GlStateManager.matrixMode(GL11.GL_MODELVIEW);
				tess.draw();
				GlStateManager.matrixMode(5890);
				GlStateManager.loadIdentity();
				GlStateManager.matrixMode(5888);
				GlStateManager.popMatrix();
			}
			GL11.glStencilFunc(GL11.GL_EQUAL, hackyshit ? 11 : 9, 0xFF); // Fire
			{
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
				buffer.pos(0, h, 0).tex(0, v).color(0.75F, 0.25F, 0F, 0.75F).endVertex();
				buffer.pos(w, h, 0).tex(u, v).color(1F, 0F, 0F, 0.75F).endVertex();
				buffer.pos(w, 0, 0).tex(u, 0).color(1F, 0.5F, 0F, 0.75F).endVertex();
				buffer.pos(0, 0, 0).tex(0, 0).color(0.5F, 0.5F, 0F, 1F).endVertex();

				GlStateManager.pushMatrix();
				float f = (float) mc.world.getTotalWorldTime() + (mc.isGamePaused() ? 0 : mc.getRenderPartialTicks());
				GlStateManager.matrixMode(GL11.GL_TEXTURE);
				GlStateManager.loadIdentity();
				GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
				GlStateManager.scale(0.5F, 0.5F, 0.5F);
				GlStateManager.rotate(f, 0, 0, 1);
				GlStateManager.matrixMode(GL11.GL_MODELVIEW);
				tess.draw();
				GlStateManager.matrixMode(5890);
				GlStateManager.loadIdentity();
				GlStateManager.matrixMode(5888);
				GlStateManager.popMatrix();
			}

			if (!hackyshit)
				GL11.glStencilMask(0xFF);
			GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
			GL11.glStencilMask(0x00);
			GL11.glDisable(GL11.GL_STENCIL_TEST);
			GlStateManager.shadeModel(GL11.GL_FLAT);
			GlStateManager.disableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.disableBlend();
			//			GlStateManager.enableLighting();
		}
	}

	private void renderCharges(int x, int y, FontRenderer fontRender, IAoVCapability cap, int index) {
		Ability ability = cap.getSlot(index);
		int val = ability == null ? -1 : ability.getCharges();
		if (val < 0)
			return;
		int w = 20;
		int h = 20;
		drawRect(x, y, x + w, y + h, !cap.canUseAbility(ability) ? 0x77FF0000 : 0x7700BBFF);
		drawCenteredStringNoShadow(fontRender, String.valueOf(val), x + 10, y + (ConfigHandler.renderBarOverHotbar || ConfigHandler.renderChargesAboveSpellbar ? 3 : 10), 0x000000);
	}

	private void drawCenteredStringNoShadow(FontRenderer fontRendererIn, String text, int x, int y, int color) {
		fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, false);
	}

	private void renderAstro(EntityPlayer player, ScaledResolution sr) {
		if (!player.hasCapability(CapabilityList.ASTRO, null))
			return;
		IAstroCapability cap = player.getCapability(CapabilityList.ASTRO, null);
		if (cap == null)
			return;
		if (!ConfigHandler.renderAstro && cap.getDraw() == null && cap.getBurn() == null && cap.getSpread() == null)
			return;
		GlStateManager.pushMatrix();
		{
			GlStateManager.color(1F, 1F, 1F, 1F);
			mc.getTextureManager().bindTexture(TEXTURE_ASTRO);
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buffer = tess.getBuffer();
			buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

			float x = sr.getScaledWidth() * 2F / 3F;
			float y = sr.getScaledHeight() / 5F;

			x += ConfigHandler.elementPositions.astro_x;
			y += ConfigHandler.elementPositions.astro_y;

			float scale = 0.35F;
			buffer.pos(x, y + 143F * scale, 0).tex(0, 0.5F).endVertex();
			buffer.pos(x + 235F * scale, y + 143F * scale, 0).tex(0.5F, 0.5F).endVertex();
			buffer.pos(x + 235F * scale, y, 0).tex(0.5F, 0).endVertex();
			buffer.pos(x, y, 0).tex(0, 0).endVertex();

			if (cap.getDraw() != null) {
				renderAstroIcon(IAstroCapability.ICard.getCardID(cap.getDraw()), buffer, x + 33.5F, y + 17F, scale);
				drawCenteredString(mc.fontRenderer, "" + cap.getDrawTime(), (int) (x + 43), (int) (y + 50), 0xbd7e10);
				mc.getTextureManager().bindTexture(TEXTURE_ASTRO);
				GlStateManager.color(1, 1, 1, 1);
			}

			if (cap.getSpread() != null)
				renderAstroIcon(IAstroCapability.ICard.getCardID(cap.getSpread()), buffer, x + 14F, y + 20F, scale * 0.8F);

			if (cap.getBurn() != null)
				renderAstroRoyalRoadIcon((int) Math.floor(IAstroCapability.ICard.getCardID(cap.getBurn()) / 2F), buffer, x + 55F, y - 7.5F, scale);

			tess.draw();
		}
		GlStateManager.popMatrix();
	}

	private void renderAstroIcon(int index, BufferBuilder buffer, float x, float y, float scale) {
		scale = scale / 4F;
		float xOffset = 0.25F * (index % 4);
		float yOffset = 0.25F * (float) Math.floor(index / 4F);
		buffer.pos(x, y + 286F * scale, 0).tex(0.5F * xOffset, 0.75F + yOffset).endVertex();
		buffer.pos(x + 235F * scale, y + 286F * scale, 0).tex(0.5F * (0.25F + xOffset), 0.75F + yOffset).endVertex();
		buffer.pos(x + 235F * scale, y, 0).tex(0.5F * (0.25F + xOffset), 0.5F + yOffset).endVertex();
		buffer.pos(x, y, 0).tex(0.5F * xOffset, 0.5F + yOffset).endVertex();
	}

	private void renderAstroRoyalRoadIcon(int index, BufferBuilder buffer, float x, float y, float scale) {
		scale *= 0.60F;
		float xOffset = 0.15F + (0.084F * index);
		float yOffset = 0;//0.25F * (float) Math.floor(index / 4);
		buffer.pos(x + 80F * scale, y, 0).tex(0.5F + xOffset, 0 + yOffset).endVertex();
		buffer.pos(x, y, 0).tex(0.5F + (0.08F + xOffset), 0 + yOffset).endVertex();
		buffer.pos(x, y + 286F * scale, 0).tex(0.5F + (0.08F + xOffset), 0.5F + yOffset).endVertex();
		buffer.pos(x + 80F * scale, y + 286F * scale, 0).tex(0.5F + xOffset, 0.5F + yOffset).endVertex();
		if (ConfigHandler.renderRoyalRoad) {
			GlStateManager.pushMatrix();
			drawCenteredString(mc.fontRenderer, I18n.format("aov.astro.burn." + index), (int) x - 16, (int) y, index == 0 ? 0x00AAFF : index == 1 ? 0x00FFAA : 0xFFDD88);
			GlStateManager.popMatrix();
			mc.getTextureManager().bindTexture(TEXTURE_ASTRO);
		}
		GlStateManager.color(1, 1, 1, 1);
	}

	private void renderTarget(EntityLivingBase target) {
		GlStateManager.pushMatrix();
		{
			double x = 10 + ConfigHandler.elementPositions.target_x;
			double y = 150 + ConfigHandler.elementPositions.target_y;
			double w = 100;
			double h = 41;

			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buffer = tess.getBuffer();
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
			GlStateManager.color(1F, 1F, 1F, 1F);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			{

				float r = 1F;
				float g = 1F;
				float b = 1F;
				float a = ConfigHandler.targetOpacity;

				buffer.pos(x + w, y, 0).tex(1, 0).color(r, g, b, a).endVertex();
				buffer.pos(x, y, 0).tex(0, 0).color(r, g, b, a).endVertex();
				buffer.pos(x, y + h, 0).tex(0, 1).color(r, g, b, a).endVertex();
				buffer.pos(x + w, y + h, 0).tex(1, 1).color(r, g, b, a).endVertex();

				Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_FOCUS);
				tess.draw();
			}
			GlStateManager.disableBlend();

			{
				GlStateManager.pushMatrix();
				{
					if (cacheEntityID != target.getEntityId()) {
						cacheEntityID = target.getEntityId();
						try {
							cacheEntity = target.getClass().getConstructor(World.class).newInstance(mc.world);
						} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
							e1.printStackTrace();
						}
					}
					if (cacheEntity != null)
						GuiInventory.drawEntityOnScreen((int) (x + 30), (int) (y + 36), 8, -40, 5, cacheEntity);
				}
				GlStateManager.popMatrix();
				String name = target.getDisplayName().getFormattedText();
				FontRenderer font = ClientProxy.getFontRenderer().setSize(0.5F);
				List<String> list = font.listFormattedStringToWidth(name, 80);
				if (!list.isEmpty())
					name = list.get(0);
				drawString(font,

						name,

						(int) (x + w / 3) + 3,

						(int) (y + 28) - (int) (ClientProxy.getFontRenderer().getFontHeight() / 2F),

						0xFFFFFF);
				drawString(font,

						"x " + (int) target.getHealth() + "/" + (int) target.getMaxHealth(),

						(int) (x + w / 3) + 3,

						(int) (y + 16) - (int) (ClientProxy.getFontRenderer().getFontHeight() / 2F),

						0xFFFFFF);
				{
					Minecraft.getMinecraft().renderEngine.bindTexture(Gui.ICONS);
					int posx = (int) (x + 30);
					int posy = (int) (y + 13);
					int textureX = 52;
					int textureY = 0;
					int width = 9;
					int height = 9;
					int sizeX = 5;
					int sizeY = 5;
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferbuilder = tessellator.getBuffer();
					bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
					bufferbuilder.pos((double) (posx), (double) (posy + sizeY), (double) this.zLevel).tex((double) ((float) (textureX) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).endVertex();
					bufferbuilder.pos((double) (posx + sizeX), (double) (posy + sizeY), (double) this.zLevel).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).endVertex();
					bufferbuilder.pos((double) (posx + sizeX), (double) (posy), (double) this.zLevel).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY) * 0.00390625F)).endVertex();
					bufferbuilder.pos((double) (posx), (double) (posy), (double) this.zLevel).tex((double) ((float) (textureX) * 0.00390625F), (double) ((float) (textureY) * 0.00390625F)).endVertex();
					tessellator.draw();
				}
				ClientProxy.getFontRenderer().reset();
			}
		}
		GlStateManager.popMatrix();
	}

}
