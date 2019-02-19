package tamaized.aov.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tamaized.aov.AoV;
import tamaized.aov.client.entity.RenderAlignmentAoE;
import tamaized.aov.client.entity.RenderCelestialOpposition;
import tamaized.aov.client.entity.RenderCombust;
import tamaized.aov.client.entity.RenderDruidicWolf;
import tamaized.aov.client.entity.RenderEarthquake;
import tamaized.aov.client.entity.RenderFlameStrike;
import tamaized.aov.client.entity.RenderGravity;
import tamaized.aov.client.entity.RenderMalefic;
import tamaized.aov.client.entity.RenderNimbusRay;
import tamaized.aov.client.entity.RenderSpellBladeBarrier;
import tamaized.aov.client.entity.RenderSpellEntity;
import tamaized.aov.client.entity.RenderSpellLightingBolt;
import tamaized.aov.client.entity.RenderSpellLightingStorm;
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

	@SubscribeEvent
	public static void registerRenders(FMLClientSetupEvent e) {
		RenderingRegistry.registerEntityRenderingHandler(ProjectileNimbusRay.class, RenderNimbusRay::new);
		RenderingRegistry.registerEntityRenderingHandler(ProjectileFlameStrike.class, RenderFlameStrike::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellImplosion.class, RenderSpellEntity::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellBladeBarrier.class, RenderSpellBladeBarrier::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellAoVParticles.class, RenderSpellEntity::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellVanillaParticles.class, RenderSpellEntity::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMalefic.class, RenderMalefic::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCombust.class, RenderCombust::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGravity.class, RenderGravity::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCelestialOpposition.class, RenderCelestialOpposition::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellLightningBolt.class, RenderSpellLightingBolt::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEarthquake.class, RenderEarthquake::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellLightningStorm.class, RenderSpellLightingStorm::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDruidicWolf.class, RenderDruidicWolf::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAlignmentAoE.class, RenderAlignmentAoE::new);
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
