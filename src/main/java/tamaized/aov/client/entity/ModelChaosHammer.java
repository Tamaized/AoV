package tamaized.aov.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

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
		this.handle.addBox(-1.5F, -1.5F, -10.0F, 3, 3, 39, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 0.0F, 23.0F);
		this.head.addBox(-5.0F, -11.0F, -5.0F, 10, 22, 10, 0.0F);
		this.handle.addChild(this.head);
	}

	@Override
	public void setRotationAngles(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		handle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}
