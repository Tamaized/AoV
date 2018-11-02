package tamaized.aov.client.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.common.entity.EntitySpellLightningStorm;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderSpellLightingStorm extends Render<EntitySpellLightningStorm> {

	public RenderSpellLightingStorm(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void doRender(@Nonnull EntitySpellLightningStorm entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	@Nullable
	protected ResourceLocation getEntityTexture(@Nonnull EntitySpellLightningStorm entity) {
		return null;
	}
}