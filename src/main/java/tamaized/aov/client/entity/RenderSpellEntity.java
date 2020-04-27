package tamaized.aov.client.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderSpellEntity<T extends Entity> extends EntityRenderer<T> {

	public RenderSpellEntity(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Nonnull
	@Override
	public ResourceLocation getEntityTexture(@Nonnull T entity) {
		return AtlasTexture.LOCATION_PARTICLES_TEXTURE;
	}

}