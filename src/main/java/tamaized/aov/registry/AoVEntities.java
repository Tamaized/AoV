package tamaized.aov.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tamaized.aov.AoV;
import tamaized.aov.common.entity.EntityAlignmentAoE;
import tamaized.aov.common.entity.EntityCelestialOpposition;
import tamaized.aov.common.entity.EntityCombust;
import tamaized.aov.common.entity.EntityDruidicWolf;
import tamaized.aov.common.entity.EntityEarthquake;
import tamaized.aov.common.entity.EntityGravity;
import tamaized.aov.common.entity.EntityMalefic;
import tamaized.aov.common.entity.EntitySpellAoVParticles;
import tamaized.aov.common.entity.EntitySpellBladeBarrier;
import tamaized.aov.common.entity.EntitySpellImplosion;
import tamaized.aov.common.entity.EntitySpellLightningBolt;
import tamaized.aov.common.entity.EntitySpellLightningStorm;
import tamaized.aov.common.entity.EntitySpellVanillaParticles;
import tamaized.aov.common.entity.ProjectileFlameStrike;
import tamaized.aov.common.entity.ProjectileNimbusRay;

import java.lang.reflect.InvocationTargetException;

@Mod.EventBusSubscriber(modid = AoV.MODID)
public class AoVEntities {

	@SubscribeEvent
	public static void register(RegistryEvent.Register<EntityType<?>> e) {
		e.getRegistry().registerAll(

				create(ProjectileNimbusRay.class),

				create(ProjectileFlameStrike.class),

				create(EntitySpellImplosion.class),

				create(EntitySpellBladeBarrier.class),

				create(EntitySpellVanillaParticles.class),

				create(EntitySpellAoVParticles.class),

				create(EntityMalefic.class),

				create(EntityCombust.class),

				create(EntityGravity.class),

				create(EntityCelestialOpposition.class),

				create(EntitySpellLightningBolt.class),

				create(EntityEarthquake.class),

				create(EntitySpellLightningStorm.class),

				create(EntityDruidicWolf.class),

				create(EntityAlignmentAoE.class)

		);
	}

	private static <T extends Entity> EntityType<T> create(Class<T> entity) {
		return EntityType.Builder.create(entity, world -> {
			try {
				return entity.getConstructor(World.class).newInstance(world);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
			return null;
		}).build(entity.getSimpleName().toLowerCase());
	}

}
