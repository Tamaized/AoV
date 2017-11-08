package tamaized.aov.client.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderSpellEntity<T extends Entity> extends Render<T> {

	public RenderSpellEntity(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(@Nonnull T entity, double x, double y, double z, float entityYaw, float ticks) {
	}

	@Override
	protected ResourceLocation getEntityTexture(@Nonnull T entity) {
		return null;
	}

}