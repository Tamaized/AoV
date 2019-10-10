package tamaized.aov.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
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

@Mod.EventBusSubscriber(modid = AoV.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AoVEntities {

	static final DeferredRegister<EntityType<?>> REGISTRY = new DeferredRegister<>(ForgeRegistries.ENTITIES, AoV.MODID);
	public static final RegistryObject<EntityType<ProjectileNimbusRay>> projectilenimbusray = assign(ProjectileNimbusRay.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<ProjectileFlameStrike>> projectileflamestrike = assign(ProjectileFlameStrike.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntitySpellImplosion>> entityspellimplosion = assign(EntitySpellImplosion.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntitySpellBladeBarrier>> entityspellbladebarrier = assign(EntitySpellBladeBarrier.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntitySpellVanillaParticles>> entityspellvanillaparticles = assign(EntitySpellVanillaParticles.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntitySpellAoVParticles>> entityspellaovparticles = assign(EntitySpellAoVParticles.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntityMalefic>> entitymalefic = assign(EntityMalefic.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntityCombust>> entitycombust = assign(EntityCombust.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntityGravity>> entitygravity = assign(EntityGravity.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntityCelestialOpposition>> entitycelestialopposition = assign(EntityCelestialOpposition.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntitySpellLightningBolt>> entityspelllightningbolt = assign(EntitySpellLightningBolt.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntityEarthquake>> entityearthquake = assign(EntityEarthquake.class, 6F, 0.1F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntitySpellLightningStorm>> entityspelllightningstorm = assign(EntitySpellLightningStorm.class, 12F, 0.1F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntityDruidicWolf>> entitydruidicwolf = assign(EntityDruidicWolf.class, 0.6F, 0.85F, 256, 1, true, EntityClassification.MISC);
	public static final RegistryObject<EntityType<EntityAlignmentAoE>> entityalignmentaoe = assign(EntityAlignmentAoE.class, 0.5F, 0.5F, 256, 1, true, EntityClassification.MISC);

	public static void register(IEventBus mod) {
		REGISTRY.register(mod);
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

	@SuppressWarnings("ConstantConditions")
	private static <T extends Entity> RegistryObject<EntityType<T>> assign(Class<T> entity, float w, float h, int range, int freq, boolean updates, EntityClassification classification) {
		final String name = entity.getSimpleName().toLowerCase();
		return REGISTRY.register(name, () -> EntityType.Builder.<T>create((et, world) -> {
			try {
				return entity.getConstructor(World.class).newInstance(world);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
			return null;
		}, classification).
				setTrackingRange(range).
				setUpdateInterval(freq).
				setShouldReceiveVelocityUpdates(updates).
				size(w, h).
				setCustomClientFactory((spawnEntity, world) -> {
					try {
						return entity.getConstructor(World.class).newInstance(world);
					} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						e.printStackTrace();
					}
					return null;
				}).
				build(name));
	}

}
