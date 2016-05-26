package Tamaized.AoV.registry;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import Tamaized.AoV.AoV;
import Tamaized.AoV.entity.projectile.caster.ProjectileNimbusRay;

public class AoVEntities extends RegistryBase {

	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		EntityRegistry.registerModEntity(ProjectileNimbusRay.class, "ProjectileNimbusRay", 0, AoV.instance, 128, 1, true);
	}

	@Override
	public void postInit() {
		
	}

	@Override
	public void setupRender() {
		//RenderingRegistry.registerEntityRenderingHandler(ProjectileNimbusRay.class, new RenderProjectileNimbusRay(Minecraft.getMinecraft().getRenderManager()));
	}

}
