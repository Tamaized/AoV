package tamaized.aov.client.particle;

import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.world.World;

public class ParticleSnow extends SmokeParticle {

	public ParticleSnow(World p_i46348_1_, double p_i46348_2_, double p_i46348_4_, double p_i46348_6_, double p_i46348_8_, double p_i46348_10_, double p_i46348_12_) {
		super(p_i46348_1_, p_i46348_2_, p_i46348_4_, p_i46348_6_, p_i46348_8_, p_i46348_10_, p_i46348_12_, 1F);
		particleRed = particleGreen = particleBlue = 1F;
	}

	// [Vanilla Copy] from super; except we remove the downward motion.
	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.age++ >= this.maxAge) {
			this.setExpired();
		}

		this.setParticleTextureIndex(7 - this.age * 8 / this.maxAge);
		//		this.motionY -= 0.03D;
		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9900000095367432D;
		this.motionY *= 0.9900000095367432D;
		this.motionZ *= 0.9900000095367432D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
	}
}
