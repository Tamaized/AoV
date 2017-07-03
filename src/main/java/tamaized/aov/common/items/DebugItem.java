package tamaized.aov.common.items;

import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import tamaized.tammodized.common.items.TamItem;

public class DebugItem extends TamItem {

	public DebugItem(String n) {
		super(AoV.tabs.tabAoV, n, 1);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		// FMLNetworkHandler.openGui(playerIn, tamaized.aov.AoV.instance, GuiHandler.GUI_SKILLS, worldIn, pos.getX(), pos.getY(), pos.getZ());
		// if(worldIn.isRemote) AbilityBase.fromName(CureLightWounds.getName()).activate(playerIn, tamaized.aov.AoV.clientAoVCore.getPlayer(playerIn), null);
		// if(worldIn.isRemote) Tamaized.tamaized.aov.AoV.tamaized.aov.common.tamaized.aov.client.ClientProxy.bar.setSlot(AbilityBase.fromName(CureLightWounds.getName()), 0);
		// if(!worldIn.isRemote) tamaized.aov.AoV.serverAoVCore.getPlayer(playerIn).setCurrentDivinePower(tamaized.aov.AoV.serverAoVCore.getPlayer(playerIn).getMaxDivinePower());
		// if(!worldIn.isRemote) tamaized.aov.AoV.serverAoVCore.getPlayer(playerIn).addSkillPoints(1);
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap != null) {
			cap.addExp(player, cap.getExpNeededToLevel(), null);
		}
		// if(worldIn.isRemote) AoVOverlay.addFloatyText("test");
		return super.onItemRightClick(world, player, hand);
	}

}
