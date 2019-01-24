package tamaized.aov.client.entity;

import net.minecraft.client.model.ModelWolf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelPolymorphWolf extends ModelWolf {

	public ModelPolymorphWolf() {
		super();
		isChild = false;
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime) {

		/*if (entitywolf.isAngry()) {
			this.wolfTail.rotateAngleY = 0.0F;
		} else {*/
		this.wolfTail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;
		//		}

		/*if (entitywolf.isSitting()) {
			this.wolfMane.setRotationPoint(-1.0F, 16.0F, -3.0F);
			this.wolfMane.rotateAngleX = ((float) Math.PI * 2F / 5F);
			this.wolfMane.rotateAngleY = 0.0F;
			this.wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
			this.wolfBody.rotateAngleX = ((float) Math.PI / 4F);
			this.wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
			this.wolfLeg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
			this.wolfLeg1.rotateAngleX = ((float) Math.PI * 3F / 2F);
			this.wolfLeg2.setRotationPoint(0.5F, 22.0F, 2.0F);
			this.wolfLeg2.rotateAngleX = ((float) Math.PI * 3F / 2F);
			this.wolfLeg3.rotateAngleX = 5.811947F;
			this.wolfLeg3.setRotationPoint(-2.49F, 17.0F, -4.0F);
			this.wolfLeg4.rotateAngleX = 5.811947F;
			this.wolfLeg4.setRotationPoint(0.51F, 17.0F, -4.0F);
		} else {*/
		this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
		this.wolfBody.rotateAngleX = ((float) Math.PI / 2F);
		this.wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
		this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
		this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
		this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
		this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
		this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
		this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
		this.wolfLeg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.wolfLeg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.wolfLeg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.wolfLeg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		//		}

		this.wolfHeadMain.rotateAngleZ = getShakeAngle(partialTickTime, 0.0F);
		this.wolfMane.rotateAngleZ = getShakeAngle(partialTickTime, -0.08F);
		this.wolfBody.rotateAngleZ = getShakeAngle(partialTickTime, -0.16F);
		this.wolfTail.rotateAngleZ = getShakeAngle(partialTickTime, -0.2F);
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
