package tamaized.aov.client.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
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

	@Nonnull
	@Override
	public ResourceLocation getEntityTexture(WolfEntity entity) {
		return ANRGY_WOLF_TEXTURES;
	}
}
