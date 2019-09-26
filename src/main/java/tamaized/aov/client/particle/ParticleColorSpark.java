package tamaized.aov.client.particle;

import net.minecraft.client.particle.FireworkParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;
import java.util.Objects;

public class ParticleColorSpark {

	private static FireworkParticle.SparkFactory FACTORY = null;

	public static Particle makeSpark(World p_i46465_1_, double p_i46465_2_, double p_i46465_4_, double p_i46465_6_, double p_i46465_8_, double p_i46465_10_, double p_i46465_12_, ParticleManager p_i46465_14_, int color) {
		if (FACTORY == null)
			FACTORY = Objects.requireNonNull(ObfuscationReflectionHelper.<Map<ResourceLocation, FireworkParticle.SparkFactory>, ParticleManager>getPrivateValue(ParticleManager.class, p_i46465_14_, "field_178932_g")).get(ParticleTypes.FIREWORK.getRegistryName());
		Particle particle = FACTORY.makeParticle(ParticleTypes.FIREWORK, p_i46465_1_, p_i46465_2_, p_i46465_4_, p_i46465_6_, p_i46465_8_, p_i46465_10_, p_i46465_12_);
		Objects.requireNonNull(particle).setColor(((color >> 16) & 0xFF) / 255F, ((color >> 8) & 0xFF) / 255F, (color & 0xFF) / 255F);
		return particle;
	}

}
