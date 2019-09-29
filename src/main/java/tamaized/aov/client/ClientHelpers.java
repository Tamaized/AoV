package tamaized.aov.client;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tamaized.aov.AoV;
import tamaized.aov.client.events.KeyHandler;
import tamaized.aov.common.helper.RayTraceHelper;

import javax.annotation.Nullable;
import java.util.HashSet;

@Mod.EventBusSubscriber(modid = AoV.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientHelpers {

	private static final HashSet<Entity> BLANK_SET = Sets.newHashSet();

	public static boolean barToggle = false;
	private static LivingEntity target;

	private ClientHelpers() {

	}

	@SubscribeEvent
	public static void init(FMLClientSetupEvent event) {
		KeyHandler.register();
		for (PlayerRenderer render : Minecraft.getInstance().getRenderManager().getSkinMap().values())
			render.addLayer(new LayerWings(render));
	}

	public static void setTarget() {
		Entity ent = ClientHelpers.getTargetOverMouse(Minecraft.getInstance(), 128);
		if (ent instanceof LivingEntity && target != ent)
			target = (LivingEntity) ent;
		else
			target = null;
	}

	public static LivingEntity getTarget() {
		return target;
	}

	public static void setTarget(@Nullable LivingEntity entity) {
		target = entity;
	}

	public static Entity getTargetOverMouse(Minecraft mc, int range) {
		BLANK_SET.clear();
		BLANK_SET.add(mc.player);
		RayTraceResult ray = RayTraceHelper.tracePath(mc.player, mc.world, mc.player, range, 1, BLANK_SET);
		BLANK_SET.clear();
		return ray instanceof EntityRayTraceResult ? ((EntityRayTraceResult) ray).getEntity() : null;
	}

}
