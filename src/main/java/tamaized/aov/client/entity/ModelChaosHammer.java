package tamaized.aov.client.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelChaosHammer extends ModelBase {

	public ModelRenderer handle;
	public ModelRenderer head;

	public ModelChaosHammer() {
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
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.handle.render(scale);
	}
}
