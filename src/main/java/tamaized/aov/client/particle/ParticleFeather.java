package tamaized.aov.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ParticleFeather extends SpriteTexturedParticle {

	private static final ResourceLocation FEATHER = new ResourceLocation("item/feather");

	public ParticleFeather(World world, Vec3d pos, Vec3d target, int life, float gravity, float scale, int color) {
		super(world, pos.x, pos.y, pos.z, 0.0D, 0.0D, 0.0D);
		motionX = target.x;
		motionY = target.y;
		motionZ = target.z;
		particleScale = scale;
		particleGravity = gravity;
		maxAge = life;
		particleRed = (float) (color >> 24 & 255) / 255.0F;
		particleGreen = (float) (color >> 16 & 255) / 255.0F;
		particleBlue = (float) (color >> 8 & 255) / 255.0F;
		particleAlpha = (float) (color & 255) / 255.0F;
		setSprite(Minecraft.getInstance().getTextureMap().getSprite(FEATHER));
	}

	@Nonnull
	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.TERRAIN_SHEET;
	}

	@Override
	public void tick() {
		super.tick();
		particleScale *= 0.97F;
		particleAlpha *= 0.9F;
	}

	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		return 0xF000F0;
	}
}
