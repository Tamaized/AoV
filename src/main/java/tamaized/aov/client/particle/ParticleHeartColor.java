package tamaized.aov.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import tamaized.aov.client.events.TextureStitch;

public class ParticleHeartColor extends Particle {

	float particleScaleOverTime;

	public ParticleHeartColor(World worldIn, double posXIn, double posYIn, double posZIn, float scale, int color) {
		super(worldIn, posXIn, posYIn, posZIn, 0.0D, 0.0D, 0.0D);
		motionX *= 0.009999999776482582D;
		motionY *= 0.009999999776482582D;
		motionZ *= 0.009999999776482582D;
		motionY += 0.1D;
		particleScale *= 0.75F;
		particleScale *= scale;
		particleScaleOverTime = particleScale;
		particleMaxAge = 16;
		particleRed = (float) (color >> 24 & 255) / 255.0F;
		particleGreen = (float) (color >> 16 & 255) / 255.0F;
		particleBlue = (float) (color >> 8 & 255) / 255.0F;
		particleAlpha = (float) (color & 255) / 255.0F;
		particleTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(TextureStitch.HEART.toString());
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge * 32.0F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		this.particleScale = this.particleScaleOverTime * f;
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge) {
			this.setExpired();
		}

		this.move(this.motionX, this.motionY, this.motionZ);

		if (this.posY == this.prevPosY) {
			this.motionX *= 1.1D;
			this.motionZ *= 1.1D;
		}

		this.motionX *= 0.8600000143051147D;
		this.motionY *= 0.8600000143051147D;
		this.motionZ *= 0.8600000143051147D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
	}

	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		return 0xF000F0;
	}
}
