package tamaized.aov.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SquidInkParticle;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;
import java.util.Objects;

public class ParticleImplosion {

	private static SquidInkParticle.Factory FACTORY = null;

	public static Particle make(World p_i46465_1_, double p_i46465_2_, double p_i46465_4_, double p_i46465_6_, double p_i46465_8_, double p_i46465_10_, double p_i46465_12_, ParticleManager p_i46465_14_) {
		if (FACTORY == null)
			FACTORY = Objects.requireNonNull(ObfuscationReflectionHelper.<Map<ResourceLocation, SquidInkParticle.Factory>, ParticleManager>getPrivateValue(ParticleManager.class, p_i46465_14_, "field_178932_g")).get(ParticleTypes.SQUID_INK.getRegistryName());
		Particle particle = FACTORY.makeParticle(ParticleTypes.SQUID_INK, p_i46465_1_, p_i46465_2_, p_i46465_4_, p_i46465_6_, p_i46465_8_, p_i46465_10_, p_i46465_12_);
		Objects.requireNonNull(particle).multipleParticleScaleBy(0.25F);
		return particle;
	}
}
