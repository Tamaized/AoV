package tamaized.aov.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ObjectHolder;
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

@ObjectHolder(AoV.MODID)
@Mod.EventBusSubscriber(modid = AoV.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AoVEntities {

	public static final EntityType projectilenimbusray = getNull();
	public static final EntityType projectileflamestrike = getNull();
	public static final EntityType entityspellimplosion = getNull();
	public static final EntityType entityspellbladebarrier = getNull();
	public static final EntityType entityspellvanillaparticles = getNull();
	public static final EntityType entityspellaovparticles = getNull();
	public static final EntityType entitymalefic = getNull();
	public static final EntityType entitycombust = getNull();
	public static final EntityType entitygravity = getNull();
	public static final EntityType entitycelestialopposition = getNull();
	public static final EntityType entityspelllightningbolt = getNull();
	public static final EntityType entityearthquake = getNull();
	public static final EntityType entityspelllightningstorm = getNull();
	public static final EntityType entitydruidicwolf = getNull();
	public static final EntityType entityalignmentaoe = getNull();

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
		final String name = entity.getSimpleName().toLowerCase();
		EntityType<T> type = EntityType.Builder.create(entity, world -> {
			try {
				return entity.getConstructor(World.class).newInstance(world);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
			return null;
		}).build(name);
		type.setRegistryName(AoV.MODID, name);
		return type;
	}

	private static <T> T getNull(){
		return null;
	}

}
