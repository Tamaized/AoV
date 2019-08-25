package tamaized.aov.client.particle;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Objects;

public class ParticleSnow extends SmokeParticle {

	private static SmokeParticle.Factory FACTORY = null;
	private static IAnimatedSprite SPRITE_SET;

	public ParticleSnow(World p_i46348_1_, double p_i46348_2_, double p_i46348_4_, double p_i46348_6_, double p_i46348_8_, double p_i46348_10_, double p_i46348_12_) {
		super(p_i46348_1_, p_i46348_2_, p_i46348_4_, p_i46348_6_, p_i46348_8_, p_i46348_10_, p_i46348_12_, 1F, getAnimatedSprite());
		particleRed = particleGreen = particleBlue = 1F;
	}

	private static IAnimatedSprite getAnimatedSprite() {
		if (FACTORY == null)
			FACTORY = Objects.requireNonNull(ObfuscationReflectionHelper.<Int2ObjectMap<SmokeParticle.Factory>, ParticleManager>getPrivateValue(ParticleManager.class, Minecraft.getInstance().particles, "field_178932_g")).get(Registry.PARTICLE_TYPE.getId(ParticleTypes.SMOKE));
		if (SPRITE_SET == null)
			SPRITE_SET = ObfuscationReflectionHelper.getPrivateValue(SmokeParticle.Factory.class, FACTORY, "field_217540_a ");
		return SPRITE_SET;
	}

	// [Vanilla Copy] from super; except we remove the downward motion.
	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.age++ >= this.maxAge) {
			this.setExpired();
		} else {
			this.selectSpriteWithAge(getAnimatedSprite());
			//			this.motionY += 0.004D;
			this.move(this.motionX, this.motionY, this.motionZ);
			if (this.posY == this.prevPosY) {
				this.motionX *= 1.1D;
				this.motionZ *= 1.1D;
			}

			this.motionX *= (double) 0.96F;
			this.motionY *= (double) 0.96F;
			this.motionZ *= (double) 0.96F;
			if (this.onGround) {
				this.motionX *= (double) 0.7F;
				this.motionZ *= (double) 0.7F;
			}

		}
	}
}
