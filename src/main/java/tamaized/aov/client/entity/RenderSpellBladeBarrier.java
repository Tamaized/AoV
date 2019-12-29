package tamaized.aov.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tamaized.aov.common.entity.EntitySpellBladeBarrier;

import javax.annotation.Nonnull;

public class RenderSpellBladeBarrier<T extends EntitySpellBladeBarrier> extends EntityRenderer<T> {

	private static final ItemStack woodSword = new ItemStack(Items.WOODEN_SWORD);
	private static final ItemStack stoneSword = new ItemStack(Items.STONE_SWORD);
	private static final ItemStack ironSword = new ItemStack(Items.IRON_SWORD);
	private static final ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);
	private static final ItemStack goldSword = new ItemStack(Items.GOLDEN_SWORD);

	public RenderSpellBladeBarrier(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void func_225629_a_(@Nonnull T entity, @Nonnull String p_225629_2_, @Nonnull MatrixStack p_225629_3_, @Nonnull IRenderTypeBuffer p_225629_4_, int p_225629_5_) {
		World world = entity.world;
		if (world == null)
			return;
		bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
		RenderSystem.pushMatrix();
		{
			RenderSystem.translated(x, y + 1, z);
			int t = entity.ticksExisted * 10;
			RenderSystem.pushMatrix();
			{
				RenderSystem.translated(0, 0.5, 0);
				RenderSystem.rotatef(-(t > 0 ? t % 360 : 0), 0, 1, 0);
				drawRing(entity);
			}
			RenderSystem.popMatrix();
			RenderSystem.pushMatrix();
			{
				RenderSystem.rotatef((t > 0 ? t % 360 : 0), 0, 1, 0);
				drawRing(entity);
			}
			RenderSystem.popMatrix();
		}
		RenderSystem.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return null;
	}

	private void drawRing(T entity) {
		ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
		for (int r = 0; r <= entity.getRange() + 1; r++) {
			RenderSystem.pushMatrix();
			{
				RenderSystem.translated(Math.cos(r) * entity.getRange(), 0, Math.sin(r) * entity.getRange());
				RenderSystem.rotatef(60, 1, 0, 0);
				ItemStack stack = getSword(r == 0 ? 0 : r % 5);
				itemRender.renderItem(stack, itemRender.getItemModelWithOverrides(stack, entity.world, null));
			}
			RenderSystem.popMatrix();
		}
	}

	private ItemStack getSword(int index) {
		switch (index) {
			default:
			case 0:
				return woodSword;
			case 1:
				return stoneSword;
			case 2:
				return ironSword;
			case 3:
				return diamondSword;
			case 4:
				return goldSword;
		}
	}

}