package tamaized.aov.client.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderDruidicWolf extends RenderWolf {

	private static final ResourceLocation ANRGY_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

	public RenderDruidicWolf(RenderManager p_i47187_1_) {
		super(p_i47187_1_);
		layerRenderers.clear();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWolf entity) {
		return ANRGY_WOLF_TEXTURES;
	}

	@Override
	public float prepareScale(@Nonnull EntityWolf entitylivingbaseIn, float partialTicks) {
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		this.preRenderCallback(entitylivingbaseIn, partialTicks);
		GlStateManager.translate(0.0F, -1.501F, 0.0F);
		return 0.0625F;
	}
}
