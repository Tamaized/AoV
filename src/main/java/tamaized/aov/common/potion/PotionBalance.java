package tamaized.aov.common.potion;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;

import javax.annotation.Nonnull;

public class PotionBalance extends Potion {

	private final ResourceLocation iconTexture;

	public PotionBalance(String name) {
		super(false, 0xFF0000);
		iconTexture = new ResourceLocation(AoV.MODID, "textures/potions/" + name + ".png");
		setRegistryName(AoV.MODID, name);
		setPotionName("effect." + AoV.MODID + "." + name);
		registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "738D7064-6A60-4F59-8ABE-C2C23A6DD7B8", 0.0D, 0);
		setBeneficial();
	}

	@Override
	public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
		return 3 * (double) (amplifier + 1);
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return false;
	}

	@Override
	public void performEffect(@Nonnull EntityLivingBase entityLivingBaseIn, int p_76394_2_) {

	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
		mc.getTextureManager().bindTexture(iconTexture);
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		GlStateManager.enableBlend();
		net.minecraft.client.gui.Gui.drawModalRectWithCustomSizedTexture(x + 7, y + 8, 0, 0, 16, 16, 16, 16);
		GlStateManager.disableBlend();
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) {
		mc.getTextureManager().bindTexture(iconTexture);
		GlStateManager.enableBlend();
		net.minecraft.client.gui.Gui.drawModalRectWithCustomSizedTexture(x + 4, y + 4, 0, 0, 16, 16, 16, 16);
		GlStateManager.disableBlend();
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

}
