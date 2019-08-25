package tamaized.aov.client.entity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class ModelChaosHammer extends EntityModel {

	public RendererModel handle;
	public RendererModel head;

	public ModelChaosHammer() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.handle = new RendererModel(this, 1, 0);
		this.handle.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.handle.addBox(-1.5F, -1.5F, -10.0F, 3, 3, 39, 0.0F);
		this.head = new RendererModel(this, 0, 0);
		this.head.setRotationPoint(0.0F, 0.0F, 23.0F);
		this.head.addBox(-5.0F, -11.0F, -5.0F, 10, 22, 10, 0.0F);
		this.handle.addChild(this.head);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.handle.render(scale);
	}
}
