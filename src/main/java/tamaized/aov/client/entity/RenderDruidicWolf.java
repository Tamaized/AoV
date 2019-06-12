package tamaized.aov.client.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderDruidicWolf extends WolfRenderer {

	private static final ResourceLocation ANRGY_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

	public RenderDruidicWolf(EntityRendererManager p_i47187_1_) {
		super(p_i47187_1_);
		layerRenderers.clear();
	}

	@Override
	protected ResourceLocation getEntityTexture(WolfEntity entity) {
		return ANRGY_WOLF_TEXTURES;
	}

	@Override
	public float prepareScale(@Nonnull WolfEntity entitylivingbaseIn, float partialTicks) {
		GlStateManager.enableRescaleNormal();
		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
		this.preRenderCallback(entitylivingbaseIn, partialTicks);
		GlStateManager.translated(0.0F, -1.501F, 0.0F);
		return 0.0625F;
	}
}
