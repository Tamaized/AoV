package Tamaized.AoV.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BlockModelAngelic extends ModelBase{
	
	//fields
	ModelRenderer Body1;
	ModelRenderer Body2;
	ModelRenderer Body3;
	ModelRenderer Body4;
	ModelRenderer Base_1_1;
	ModelRenderer Base_1_2;
	ModelRenderer Base_1_3;
	ModelRenderer Wing_1;
	ModelRenderer Base_2_1;
	ModelRenderer Base_2_2;
	ModelRenderer Base_2_3;
	ModelRenderer Wing_2;
  
	public BlockModelAngelic(){
		textureWidth = 128;
		textureHeight = 128;
		
		Body1 = new ModelRenderer(this, 0, 0);
		Body1.addBox(0F, 0F, 0F, 16, 6, 16);
		Body1.setRotationPoint(-8F, 18F, -8F);
		Body1.setTextureSize(128, 128);
		Body1.mirror = true;
		setRotation(Body1, 0F, 0F, 0F);
		
		Body2 = new ModelRenderer(this, 0, 0);
		Body2.addBox(0F, 0F, 0F, 12, 4, 12);
		Body2.setRotationPoint(-6F, 14F, -6F);
		Body2.setTextureSize(128, 128);
		Body2.mirror = true;
		setRotation(Body2, 0F, 0F, 0F);
		
		Body3 = new ModelRenderer(this, 0, 22);
		Body3.addBox(0F, 0F, 0F, 8, 4, 8);
		Body3.setRotationPoint(-4F, 10F, -4F);
		Body3.setTextureSize(128, 128);
		Body3.mirror = true;
		setRotation(Body3, 0F, 0F, 0F);
		
		Body4 = new ModelRenderer(this, 0, 0);
		Body4.addBox(0F, 0F, 0F, 4, 4, 4);
		Body4.setRotationPoint(-2F, 6F, -2F);
		Body4.setTextureSize(128, 128);
		Body4.mirror = true;
		setRotation(Body4, 0F, 0F, 0F);
      
		Base_1_1 = new ModelRenderer(this, 34, 56);
		Base_1_1.addBox(0F, 0F, 0F, 10, 1, 2);
		Base_1_1.setRotationPoint(-1F, 7F, -1F);
		Base_1_1.setTextureSize(128, 128);
		Base_1_1.mirror = true;
		setRotation(Base_1_1, 0F, 0F, -2.356194F);
		
		Base_1_2 = new ModelRenderer(this, 34, 59);
		Base_1_2.addBox(0F, 0F, 0F, 10, 1, 2);
		Base_1_2.setRotationPoint(-7F, 0F, -1F);
		Base_1_2.setTextureSize(128, 128);
		Base_1_2.mirror = true;
		setRotation(Base_1_2, 0F, 0F, 2.879793F);
		
		Base_1_3 = new ModelRenderer(this, 34, 53);
		Base_1_3.addBox(0F, 0F, 0F, 8, 1, 2);
		Base_1_3.setRotationPoint(-15F, 2F, -1F);
		Base_1_3.setTextureSize(128, 128);
		Base_1_3.mirror = true;
		setRotation(Base_1_3, 0F, 0F, 2.007129F);
		
		Wing_1 = new ModelRenderer(this, 0, 52);
		Wing_1.addBox(0F, 0F, 0F, 16, 11, 1);
		Wing_1.setRotationPoint(-18F, 0F, 0F);
		Wing_1.setTextureSize(128, 128);
		Wing_1.mirror = true;
		setRotation(Wing_1, 0F, 0F, 0F);
		
		Base_2_1 = new ModelRenderer(this, 58, 56);
		Base_2_1.addBox(0F, 0F, 0F, 10, 1, 2);
		Base_2_1.setRotationPoint(8F, 0F, -1F);
		Base_2_1.setTextureSize(128, 128);
		Base_2_1.mirror = true;
		setRotation(Base_2_1, 0F, 0F, 2.356194F);
		
		Base_2_2 = new ModelRenderer(this, 58, 59);
		Base_2_2.addBox(0F, -1F, 0F, 10, 1, 2);
		Base_2_2.setRotationPoint(7F, 0F, -1F);
		Base_2_2.setTextureSize(128, 128);
		Base_2_2.mirror = true;
		setRotation(Base_2_2, 0F, 0F, 0.2617994F);
		
		Base_2_3 = new ModelRenderer(this, 58, 53);
		Base_2_3.addBox(0F, -1F, 0F, 8, 1, 2);
		Base_2_3.setRotationPoint(15F, 2F, -1F);
		Base_2_3.setTextureSize(128, 128);
		Base_2_3.mirror = true;
		setRotation(Base_2_3, 0F, 0F, 1.134464F);
		
		Wing_2 = new ModelRenderer(this, 34, 64);
		Wing_2.addBox(0F, 0F, 0F, 16, 11, 1);
		Wing_2.setRotationPoint(2F, 0F, 0F);
		Wing_2.setTextureSize(128, 128);
		Wing_2.mirror = true;
		setRotation(Wing_2, 0F, 0F, 0F);
	}
  
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Body1.render(f5);
		Body2.render(f5);
		Body3.render(f5);
		Body4.render(f5);
		Wing_1.render(f5);
		Base_1_1.render(f5);
		Base_1_2.render(f5);
		Base_1_3.render(f5);
		Wing_2.render(f5);
		Base_2_1.render(f5);
		Base_2_2.render(f5);
		Base_2_3.render(f5);
	}	
  
	private void setRotation(ModelRenderer model, float x, float y, float z){
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
  
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e){	
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}
}
