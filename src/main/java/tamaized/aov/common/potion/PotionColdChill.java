package tamaized.aov.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;

import javax.annotation.Nonnull;

public class PotionColdChill extends Effect {

	private final ResourceLocation iconTexture;

	public PotionColdChill(String name) {
		super(EffectType.HARMFUL, 0xFFFFFF);
		iconTexture = new ResourceLocation(AoV.MODID, "textures/potions/" + name + ".png");
		setRegistryName(AoV.MODID, name);
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % (20 * 3) == 0;
	}

	@Override
	public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amp) {
		entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1F + (amp * 1F));
		entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20 * 5, amp));
		entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 20 * 5, amp));
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
