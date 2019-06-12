package tamaized.aov.client.particle;

import net.minecraft.client.particle.SquidInkParticle;
import net.minecraft.world.World;

public class ParticleImplosion extends SquidInkParticle {

	public ParticleImplosion(World p_i48831_1_, double p_i48831_2_, double p_i48831_4_, double p_i48831_6_, double p_i48831_8_, double p_i48831_10_, double p_i48831_12_) {
		super(p_i48831_1_, p_i48831_2_, p_i48831_4_, p_i48831_6_, p_i48831_8_, p_i48831_10_, p_i48831_12_);
		particleScale = 1F;
	}

	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.age++ >= this.maxAge) {
			this.setExpired();
		}

		if (this.age > this.maxAge / 2) {
			this.setAlphaF(1.0F - ((float) this.age - (float) (this.maxAge / 2)) / (float) this.maxAge);
		}

		this.setParticleTextureIndex(this.textureIdx + this.numAgingFrames - 1 - this.age * this.numAgingFrames / this.maxAge);
		this.move(this.motionX, this.motionY, this.motionZ);

		this.motionX *= (double) 0.92F;
		this.motionY *= (double) 0.92F;
		this.motionZ *= (double) 0.92F;
		if (this.onGround) {
			this.motionX *= (double) 0.7F;
			this.motionZ *= (double) 0.7F;
		}

	}
}
