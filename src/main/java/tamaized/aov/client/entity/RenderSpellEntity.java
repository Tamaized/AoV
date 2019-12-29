package tamaized.aov.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
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

	@Override
	public void func_225629_a_(@Nonnull T entity, @Nonnull String p_225629_2_, @Nonnull MatrixStack p_225629_3_, @Nonnull IRenderTypeBuffer p_225629_4_, int p_225629_5_) {
	}

	@Nonnull
	@Override
	public ResourceLocation getEntityTexture(@Nonnull T entity) {
		return AtlasTexture.LOCATION_PARTICLES_TEXTURE;
	}

}