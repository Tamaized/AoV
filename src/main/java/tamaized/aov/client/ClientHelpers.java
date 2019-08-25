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

	private static boolean stencil = false;

	private ClientHelpers() {

	}

	public static Entity getTargetOverMouse(Minecraft mc, int range) {
		BLANK_SET.clear();
		BLANK_SET.add(mc.player);
		RayTraceResult ray = RayTraceHelper.tracePath(mc.player, mc.world, mc.player, range, 1, BLANK_SET);
		BLANK_SET.clear();
		return ray instanceof EntityRayTraceResult ? ((EntityRayTraceResult) ray).getEntity() : null;
	}

	public static boolean isStencilBufferEnabled() {
		return stencil;
	}

	public static void enableStencilBuffer() { // TODO remove when/if Framebuffer gets a method for this again
		if (stencil)
			return;
		stencil = true;
		Framebuffer fbo = Minecraft.getInstance().getFramebuffer();
		int width = fbo.framebufferWidth;
		int height = fbo.framebufferHeight;
		fbo.framebufferWidth = width;
		fbo.framebufferHeight = height;
		fbo.framebufferTextureWidth = width;
		fbo.framebufferTextureHeight = height;
		if (!GLX.isUsingFBOs()) {
			fbo.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
		} else {
			fbo.framebufferObject = GLX.glGenFramebuffers();
			fbo.framebufferTexture = TextureUtil.generateTextureId();
			if (fbo.useDepth) {
				fbo.depthBuffer = GLX.glGenRenderbuffers();
			}

			fbo.setFramebufferFilter(9728);
			GlStateManager.bindTexture(fbo.framebufferTexture);
			GlStateManager.texImage2D(3553, 0, 32856, fbo.framebufferTextureWidth, fbo.framebufferTextureHeight, 0, 6408, 5121, null);
			GLX.glBindFramebuffer(GLX.GL_FRAMEBUFFER, fbo.framebufferObject);
			GLX.glFramebufferTexture2D(GLX.GL_FRAMEBUFFER, GLX.GL_COLOR_ATTACHMENT0, 3553, fbo.framebufferTexture, 0);
			if (fbo.useDepth) {
				GLX.glBindRenderbuffer(GLX.GL_RENDERBUFFER, fbo.depthBuffer);
				GLX.glRenderbufferStorage(GLX.GL_RENDERBUFFER, org.lwjgl.opengl.EXTPackedDepthStencil.GL_DEPTH24_STENCIL8_EXT, fbo.framebufferTextureWidth, fbo.framebufferTextureHeight);
				GLX.glFramebufferRenderbuffer(GLX.GL_FRAMEBUFFER, org.lwjgl.opengl.EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, GLX.GL_RENDERBUFFER, fbo.depthBuffer);
				GLX.glFramebufferRenderbuffer(GLX.GL_FRAMEBUFFER, org.lwjgl.opengl.EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, GLX.GL_RENDERBUFFER, fbo.depthBuffer);
			}

			fbo.checkFramebufferComplete();
			fbo.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
			fbo.unbindFramebufferTexture();
		}
	}

}
