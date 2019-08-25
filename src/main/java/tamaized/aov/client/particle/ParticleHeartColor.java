package tamaized.aov.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.aov.client.events.TextureStitch;

import javax.annotation.Nonnull;

public class ParticleHeartColor extends SpriteTexturedParticle {

	float particleScaleOverTime;

	public ParticleHeartColor(World world, Vec3d pos, Vec3d target, int life, float gravity, float scale, int color) {
		super(world, pos.x, pos.y, pos.z, 0.0D, 0.0D, 0.0D);
		motionX = target.x;
		motionY = target.y;
		motionZ = target.z;
		particleScale *= 0.75F;
		particleScale *= scale;
		particleGravity = gravity;
		maxAge = life;
		particleScaleOverTime = particleScale;
		particleRed = (float) (color >> 24 & 255) / 255.0F;
		particleGreen = (float) (color >> 16 & 255) / 255.0F;
		particleBlue = (float) (color >> 8 & 255) / 255.0F;
		particleAlpha = (float) (color & 255) / 255.0F;
		setSprite(Minecraft.getInstance().getTextureMap().getAtlasSprite(TextureStitch.HEART.toString()));
	}

	@Nonnull
	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, @Nonnull ActiveRenderInfo entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		float f = ((float) this.age + partialTicks) / (float) this.maxAge * 32.0F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		this.particleScale = this.particleScaleOverTime * f;
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		return 0xF000F0;
	}
}
