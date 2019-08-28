package tamaized.aov.client;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import tamaized.aov.common.helper.RayTraceHelper;

import java.util.HashSet;

public final class ClientHelpers {

	private static final HashSet<Entity> BLANK_SET = Sets.newHashSet();

	private ClientHelpers() {

	}

	public static Entity getTargetOverMouse(Minecraft mc, int range) {
		BLANK_SET.clear();
		BLANK_SET.add(mc.player);
		RayTraceResult ray = RayTraceHelper.tracePath(mc.player, mc.world, mc.player, range, 1, BLANK_SET);
		BLANK_SET.clear();
		return ray instanceof EntityRayTraceResult ? ((EntityRayTraceResult) ray).getEntity() : null;
	}

}
