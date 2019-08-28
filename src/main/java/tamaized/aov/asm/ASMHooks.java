package tamaized.aov.asm;

import com.mojang.blaze3d.platform.GLX;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;

public class ASMHooks {

	/**
	 * Injection Point:<br>
	 * {@link net.minecraft.entity.Entity#isImmuneToFire}<br>
	 * [BEFORE] IRETURN
	 */
	public static boolean isImmuneToFire(boolean value, Entity entity) {
		if (value)
			return true;
		IPolymorphCapability cap = CapabilityList.getCap(entity, CapabilityList.POLYMORPH);
		return cap != null && cap.getMorph() == IPolymorphCapability.Morph.FireElemental;
	}

	/**
	 * Injection Point:<br>
	 * {@link net.minecraft.client.shader.Framebuffer#func_216492_b}<br>
	 * [BEFORE] {@link net.minecraft.client.shader.Framebuffer#checkFramebufferComplete}
	 */
	public static void enableStencilBuffer(Framebuffer fbo){
		GLX.glBindRenderbuffer(GLX.GL_RENDERBUFFER, fbo.depthBuffer);
		GLX.glRenderbufferStorage(GLX.GL_RENDERBUFFER, org.lwjgl.opengl.EXTPackedDepthStencil.GL_DEPTH24_STENCIL8_EXT, fbo.framebufferTextureWidth, fbo.framebufferTextureHeight);
		GLX.glFramebufferRenderbuffer(GLX.GL_FRAMEBUFFER, org.lwjgl.opengl.EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, GLX.GL_RENDERBUFFER, fbo.depthBuffer);
		GLX.glFramebufferRenderbuffer(GLX.GL_FRAMEBUFFER, org.lwjgl.opengl.EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, GLX.GL_RENDERBUFFER, fbo.depthBuffer);
	}

}
