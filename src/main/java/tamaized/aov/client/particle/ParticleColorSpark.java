package tamaized.aov.client.particle;

import net.minecraft.client.particle.FireworkParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.world.World;

public class ParticleColorSpark extends FireworkParticle.Spark {

	public ParticleColorSpark(World p_i46465_1_, double p_i46465_2_, double p_i46465_4_, double p_i46465_6_, double p_i46465_8_, double p_i46465_10_, double p_i46465_12_, ParticleManager p_i46465_14_, int color) {
		super(p_i46465_1_, p_i46465_2_, p_i46465_4_, p_i46465_6_, p_i46465_8_, p_i46465_10_, p_i46465_12_, p_i46465_14_);
		particleRed = ((color >> 16) & 0xFF) / 255F;
		particleGreen = ((color >> 8) & 0xFF) / 255F;
		particleBlue = (color & 0xFF) / 255F;
	}

}
