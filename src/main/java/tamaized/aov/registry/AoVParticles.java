package tamaized.aov.registry;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.client.particle.ParticleSnow;

public class AoVParticles {

	public static final int SNOW = ParticleRegistry.register(new ParticleRegistry.IParticleHandler() {

		@Override
		@SideOnly(Side.CLIENT)
		public void execute(ParticleManager manager, World world, double x, double y, double z, double dx, double dy, double dz, int... data) {
			manager.addEffect(new ParticleSnow(world, x, y, z, dx, dy, dz));
		}

	});

}
