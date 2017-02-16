package Tamaized.AoV.helper;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleHelper {

	public enum Type {
		BURST
	}

	public static void spawnParticleMesh(Type type, World world, Vec3d target, int range, int color) {
		if (world.isRemote) return;
		switch (type) {
			case BURST:
				burstParticles(world, target.xCoord, target.yCoord, target.zCoord, range, color);
				break;
			default:
				break;
		}
	}

	private static void burstParticles(World world, double posX, double posY, double posZ, int range, int color) {
		int amount = range*4;
		for (int i = -amount; i <= amount; i++) {
			double dx = (world.rand.nextFloat() * 0.2F) - 0.1F;
			double dz = (world.rand.nextFloat() * 0.2F) - 0.1F;
			Tamaized.TamModized.particles.FX.network.ParticleFluffPacketHandler.spawnOnServer(world, new Vec3d(posX, posY, posZ), new Vec3d(dx, 0, dz), 20 * (world.rand.nextInt(6)), -0.01F, (world.rand.nextFloat()*0.9F) - 0.25F, color);
			// Tamaized.TamModized.particles.ParticleHelper.spawnVanillaParticleOnServer(world, EnumParticleTypes.PORTAL, d0 + x, d1, d2 + z, -x, 1, -z);
		}
	}

}
