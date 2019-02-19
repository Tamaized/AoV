package tamaized.aov.registry;

import tamaized.aov.client.particle.ParticleSnow;

public class AoVParticles {

	public static final int SNOW = ParticleRegistry.register((manager, world, x, y, z, dx, dy, dz, data) -> manager.addEffect(new ParticleSnow(world, x, y, z, dx, dy, dz, 1F)));

}
