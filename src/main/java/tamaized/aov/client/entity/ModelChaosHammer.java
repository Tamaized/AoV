package tamaized.aov.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class ModelChaosHammer extends EntityModel {

	public ModelRenderer handle;
	public ModelRenderer head;

	public ModelChaosHammer(Function<ResourceLocation, RenderType> p_i225945_1_) {
		super(p_i225945_1_);
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.handle = new ModelRenderer(this, 1, 0);
		this.handle.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.handle.func_228301_a_(-1.5F, -1.5F, -10.0F, 3, 3, 39, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 0.0F, 23.0F);
		this.head.func_228301_a_(-5.0F, -11.0F, -5.0F, 10, 22, 10, 0.0F);
		this.handle.addChild(this.head);
	}

	@Override
	public void func_225597_a_(@Nonnull Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}

	@Override
	public void func_225598_a_(@Nonnull MatrixStack p_225598_1_, @Nonnull IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
		handle.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
	}
}
