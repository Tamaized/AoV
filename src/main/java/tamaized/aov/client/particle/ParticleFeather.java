package tamaized.aov.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleFeather extends Particle {

	private static final ResourceLocation FEATHER = new ResourceLocation("items/feather");

	public ParticleFeather(World world, Vec3d pos, Vec3d target, int life, float gravity, float scale, int color) {
		super(world, pos.x, pos.y, pos.z, 0.0D, 0.0D, 0.0D);
		motionX = target.x;
		motionY = target.y;
		motionZ = target.z;
		particleScale = scale;
		particleGravity = gravity;
		particleMaxAge = life;
		particleRed = (float) (color >> 24 & 255) / 255.0F;
		particleGreen = (float) (color >> 16 & 255) / 255.0F;
		particleBlue = (float) (color >> 8 & 255) / 255.0F;
		particleAlpha = (float) (color & 255) / 255.0F;
		particleTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(FEATHER.toString());
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		particleScale *= 0.97F;
		particleAlpha *= 0.9F;
	}

	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		return 0xF000F0;
	}
}
