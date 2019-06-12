package tamaized.aov.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;

import javax.annotation.Nonnull;

public class PotionNaturesBounty extends Effect {

	private final ResourceLocation iconTexture;

	public PotionNaturesBounty(String name) {
		super(false, 0x00FF00);
		iconTexture = new ResourceLocation(AoV.MODID, "textures/potions/" + name + ".png");
		setRegistryName(AoV.MODID, name);
		setBeneficial();
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

	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderInventoryEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z) {
		Minecraft.getInstance().getTextureManager().bindTexture(iconTexture);
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		AbstractGui.drawModalRectWithCustomSizedTexture(x + 7, y + 8, 0, 0, 16, 16, 16, 16);
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
		Minecraft.getInstance().getTextureManager().bindTexture(iconTexture);
		AbstractGui.drawModalRectWithCustomSizedTexture(x + 4, y + 4, 0, 0, 16, 16, 16, 16);
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

}
