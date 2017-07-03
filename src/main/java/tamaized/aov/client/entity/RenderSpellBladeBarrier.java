package tamaized.aov.client.entity;

import tamaized.aov.common.entity.EntitySpellBladeBarrier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderSpellBladeBarrier<T extends EntitySpellBladeBarrier> extends Render<T> {

	private static final ItemStack woodSword = new ItemStack(Items.WOODEN_SWORD);
	private static final ItemStack stoneSword = new ItemStack(Items.STONE_SWORD);
	private static final ItemStack ironSword = new ItemStack(Items.IRON_SWORD);
	private static final ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);
	private static final ItemStack goldSword = new ItemStack(Items.GOLDEN_SWORD);

	public RenderSpellBladeBarrier(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float ticks) {
		World world = entity.world;
		if (world == null)
			return;
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(x, y + 1, z);
			int t = entity.ticksExisted * 10;
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(0, 0.5, 0);
				GlStateManager.rotate(-(t > 0 ? t % 360 : 0), 0, 1, 0);
				drawRing(entity);
			}
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			{
				GlStateManager.rotate((t > 0 ? t % 360 : 0), 0, 1, 0);
				drawRing(entity);
			}
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return null;
	}

	private void drawRing(T entity) {
		RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
		for (int r = 0; r <= entity.getRange() + 1; r++) {
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(Math.cos(r) * entity.getRange(), 0, Math.sin(r) * entity.getRange());
				GlStateManager.rotate(60, 1, 0, 0);
				ItemStack stack = getSword(r == 0 ? 0 : r % 5);
				itemRender.renderItem(stack, itemRender.getItemModelWithOverrides(stack, entity.world, null));
			}
			GlStateManager.popMatrix();
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