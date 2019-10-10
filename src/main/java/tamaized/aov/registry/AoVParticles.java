package tamaized.aov.registry;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.IEventBus;
import tamaized.aov.client.particle.ParticleSnow;

import java.util.function.Supplier;

@SuppressWarnings("Convert2Lambda")
public class AoVParticles {

	public static final int SNOW = ParticleRegistry.register(new Supplier<ParticleRegistry.IParticleHandler>() {
		@Override
		public ParticleRegistry.IParticleHandler get() {
			return new ParticleRegistry.IParticleHandler() {
				@Override
				public void execute(ParticleManager manager, World world, double x, double y, double z, double dx, double dy, double dz, int... data) {
					manager.addEffect(new ParticleSnow(world, x, y, z, dx, dy, dz));
				}
			};
		}
	});

	public static void register(IEventBus mod) {
	}

}
