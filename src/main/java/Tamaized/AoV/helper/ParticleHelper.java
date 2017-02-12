package Tamaized.AoV.helper;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleHelper {

	public enum Type {
		BURST
	}

	public static void spawnParticleMesh(Type type, World world, Vec3d target, int range) {
		if (world.isRemote) return;
		switch (type) {
			case BURST:
				burstParticles(world, target.xCoord, target.yCoord, target.zCoord, range);
				break;
			default:
				break;
		}
	}

	private static void burstParticles(World world, double posX, double posY, double posZ, int range) {
		double d0 = (double) ((float) (posX - .5) + 0.4F + world.rand.nextFloat() * 0.2F);
		double d1 = (double) ((float) (posY) + 0.0F + world.rand.nextFloat() * 0.3F);
		double d2 = (double) ((float) (posZ - .5) + 0.4F + world.rand.nextFloat() * 0.2F);
		for (int x = -range; x <= range; x++) {
			for (int z = -range; z <= range; z++) {
				// Tamaized.TamModized.particles.FX.network.ParticleFluffPacketHandler.spawnOnServer(world, new Vec3d(d0 + x, d1, d2 + z), 20*(world.rand.nextInt(6)), 0, world.rand.nextFloat(), 0xFFFFFFFF);
				Tamaized.TamModized.particles.ParticleHelper.spawnVanillaParticleOnServer(world, EnumParticleTypes.PORTAL, d0 + x, d1, d2 + z, -x, 1, -z);
			}
		}
	}

}
