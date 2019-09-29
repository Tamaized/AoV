package tamaized.aov.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import tamaized.aov.AoV;
import tamaized.aov.client.particle.ParticleColorSpark;
import tamaized.aov.client.particle.ParticleFeather;
import tamaized.aov.client.particle.ParticleHeartColor;
import tamaized.aov.network.client.ClientPacketHandlerParticleMesh;

public class ParticleHelper {

	public static void spawnParticle(ParticleType type, World world, Vec3d pos, Vec3d target, int life, float gravity, float scale, Integer... colors) {
		int color = colors[world.rand.nextInt(colors.length)];
		Particle particle = null;
		switch (type) {
			case Fluff:
				//				particle = new ParticleFluff(world, pos, target, life, gravity, scale, color); TODO: let's not use this stupid thing anymore
				break;
			case Heart:
				particle = new ParticleHeartColor(world, pos, target, life, gravity, scale, color);
				break;
			case Feather:
				particle = new ParticleFeather(world, pos, target, life, gravity, scale, color);
				break;
			case Spark:
				Vec3d vec = new Vec3d(1, 0, 0);
				particle = ParticleColorSpark.makeSpark(

						world,

						pos.x,

						pos.y + 0.75F,

						pos.z,

						-((0.015 * vec.x) + ((world.rand.nextFloat() * 0.125) - 0.0625)),

						((0.015 * vec.y) + ((world.rand.nextFloat() * 0.125) - 0.0625)),

						-((0.015 * vec.z) + ((world.rand.nextFloat() * 0.125) - 0.0625)),

						Minecraft.getInstance().particles,

						color >> 8

				);
				break;
		}
		if (particle != null)
			Minecraft.getInstance().particles.addEffect(particle);
	}

	public static void spawnParticleMesh(MeshType type, ParticleType particle, World world, Vec3d target, int range, int color) {
		if (!world.isRemote) {
			AoV.network.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(new BlockPos(target.x, target.y, target.z))), new ClientPacketHandlerParticleMesh(type, particle, target, range, color));
		} else {
			switch (type) {
				case BURST:
					burstParticles(particle, world, target, range, color);
					break;
				default:
					break;
			}
		}
	}

	private static void burstParticles(ParticleType particle, World world, Vec3d pos, int range, int color) {
		int amount = range * 4;
		for (int i = -amount; i <= amount; i++) {
			double dx = (world.rand.nextFloat() * 0.2F) - 0.1F;
			double dz = (world.rand.nextFloat() * 0.2F) - 0.1F;
			spawnParticle(particle, world, pos, new Vec3d(dx, 0, dz), 20 * (world.rand.nextInt(6)), -0.01F, (world.rand.nextFloat() * 0.9F) - 0.25F, color);
		}
	}

	public enum MeshType {
		BURST
	}

	public enum ParticleType {
		Fluff, Heart, Feather, Spark
	}

}
