package tamaized.aov.common.potion;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;

import javax.annotation.Nonnull;

public class PotionColdChill extends Potion {

	private final ResourceLocation iconTexture;

	public PotionColdChill(String name) {
		super(true, 0xFFFFFF);
		iconTexture = new ResourceLocation(AoV.modid, "textures/potions/" + name + ".png");
		setRegistryName(AoV.modid, name);
		setPotionName("effect." + AoV.modid + "." + name);
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
	public void performEffect(@Nonnull EntityLivingBase entityLivingBaseIn, int amp) {
		entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1F + (amp * 1F));
		entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 5, amp));
		entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 * 5, amp));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
		mc.getTextureManager().bindTexture(iconTexture);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		net.minecraft.client.gui.Gui.drawModalRectWithCustomSizedTexture(x + 7, y + 8, 0, 0, 16, 16, 16, 16);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) {
		mc.getTextureManager().bindTexture(iconTexture);
		net.minecraft.client.gui.Gui.drawModalRectWithCustomSizedTexture(x + 4, y + 4, 0, 0, 16, 16, 16, 16);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	}

}