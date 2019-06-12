package tamaized.aov.client.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderSpellEntity<T extends Entity> extends EntityRenderer<T> {

	public RenderSpellEntity(EntityRendererManager renderManager) {
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