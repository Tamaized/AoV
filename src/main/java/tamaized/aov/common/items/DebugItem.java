package tamaized.aov.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.registry.AoVTabs;
import tamaized.tammodized.common.items.TamItem;

import javax.annotation.Nonnull;

public class DebugItem extends TamItem {

	public DebugItem(String n) {
		super(AoVTabs.tabAoV, n, 1);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		if (world.isRemote)
			return super.onItemRightClick(world, player, hand);
		if (player.hasCapability(CapabilityList.AOV, null)) {
			IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
			if (cap != null)
				cap.addExp(player, cap.getExpNeededToLevel(), null);
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

}
