package tamaized.aov.client.entity;

import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.math.MathHelper;

public class ModelPolymorphWolf extends WolfModel<WolfEntity> {


	public ModelPolymorphWolf() {
		isChild = false;
	}

	@Override
	public void setLivingAnimations(WolfEntity entity, float limbSwing, float limbSwingAmount, float partialTickTime) {
		this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;
		this.body.setRotationPoint(0.0F, 14.0F, 2.0F);
		this.body.rotateAngleX = ((float) Math.PI / 2F);
		this.mane.setRotationPoint(-1.0F, 14.0F, -3.0F);
		this.mane.rotateAngleX = this.body.rotateAngleX;
		this.tail.setRotationPoint(-1.0F, 12.0F, 8.0F);
		this.legBackRight.setRotationPoint(-2.5F, 16.0F, 7.0F);
		this.legBackLeft.setRotationPoint(0.5F, 16.0F, 7.0F);
		this.legFrontRight.setRotationPoint(-2.5F, 16.0F, -4.0F);
		this.legFrontLeft.setRotationPoint(0.5F, 16.0F, -4.0F);
		this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

		this.head.rotateAngleZ = getShakeAngle(partialTickTime, 0.0F);
		this.mane.rotateAngleZ = getShakeAngle(partialTickTime, -0.08F);
		this.body.rotateAngleZ = getShakeAngle(partialTickTime, -0.16F);
		this.tail.rotateAngleZ = getShakeAngle(partialTickTime, -0.2F);
	}

	public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
		float f = p_70923_2_ / 1.8F;

		if (f < 0.0F) {
			f = 0.0F;
		} else if (f > 1.0F) {
			f = 1.0F;
		}

		return MathHelper.sin(f * (float) Math.PI) * MathHelper.sin(f * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
	}

}
