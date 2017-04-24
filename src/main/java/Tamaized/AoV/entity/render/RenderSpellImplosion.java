package Tamaized.AoV.entity.render;

import java.util.Random;

import Tamaized.AoV.entity.EntitySpellImplosion;
import Tamaized.TamModized.particles.FX.ParticleFluff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RenderSpellImplosion<T extends EntitySpellImplosion> extends Render<T> {

	public RenderSpellImplosion(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float ticks) {
		World world = entity.world;
		if(world == null || Minecraft.getMinecraft().isGamePaused() && ticks != 1.0F) return;
		Random rand = world.rand;
		for (int index = 0; index < 10; index++) {
			Vec3d vec = entity.getLook(1.0F).rotatePitch(rand.nextInt(360)).rotateYaw(rand.nextInt(360));
			float speed = 0.08F;
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleFluff(world, entity.getPositionVector().addVector(0, 0.65F, 0).add(vec), new Vec3d(-vec.xCoord*speed, -vec.yCoord*speed, -vec.zCoord*speed), 7, 0, rand.nextFloat() * 0.90F + 0.10F, 0x7700FFFF));
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return null;
	}

}