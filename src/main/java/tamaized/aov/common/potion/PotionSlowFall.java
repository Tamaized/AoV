package tamaized.aov.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;

import javax.annotation.Nonnull;

public class PotionSlowFall extends Effect {

	private final ResourceLocation iconTexture;

	public PotionSlowFall(String name) {
		super(EffectType.BENEFICIAL, 0xFFFFFF);
		iconTexture = new ResourceLocation(AoV.MODID, "textures/potions/" + name + ".png");
		setRegistryName(AoV.MODID, name);
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int p_76394_2_) {
		if (entityLivingBaseIn.getMotion().y < -0.25F)
			entityLivingBaseIn.setMotion(entityLivingBaseIn.getMotion().x, -0.25F, entityLivingBaseIn.getMotion().z);
	}

	@Override
	public void applyAttributesModifiersToEntity(LivingEntity entityLivingBaseIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier) {
		super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, int x, int y, float z) {
		Minecraft.getInstance().getTextureManager().bindTexture(iconTexture);
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		AbstractGui.blit(x + 7, y + 8, 0, 0, 16, 16, 16, 16);
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
		Minecraft.getInstance().getTextureManager().bindTexture(iconTexture);
		AbstractGui.blit(x + 4, y + 4, 0, 0, 16, 16, 16, 16);
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

}
