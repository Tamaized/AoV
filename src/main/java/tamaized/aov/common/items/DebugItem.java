package tamaized.aov.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;

import javax.annotation.Nonnull;

public class DebugItem extends Item {

	public DebugItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
		if (world.isRemote) {
			//			tamaized.aov.client.RenderAstro.testVarPleaseIgnore = 12 * 200;
			return super.onItemRightClick(world, player, hand);
		}
		IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
		if (cap != null) {
			cap.resetCharges(player);
			cap.addExp(player, cap.getExpNeededToLevel(), null);
		}
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}

}
