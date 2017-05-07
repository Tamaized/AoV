package Tamaized.AoV.items;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.TamModized.items.TamItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class DebugItem extends TamItem {

	public DebugItem(String n) {
		super(AoV.tabs.tabAoV, n, 1);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		// ItemStack stack = player.getHeldItem(hand);
		// FMLNetworkHandler.openGui(playerIn, AoV.instance, GuiHandler.GUI_SKILLS, worldIn, pos.getX(), pos.getY(), pos.getZ());
		// if(worldIn.isRemote) AbilityBase.fromName(CureLightWounds.getName()).activate(playerIn, AoV.clientAoVCore.getPlayer(playerIn), null);
		// if(worldIn.isRemote) Tamaized.AoV.common.client.ClientProxy.bar.setSlot(AbilityBase.fromName(CureLightWounds.getName()), 0);
		// if(!worldIn.isRemote) AoV.serverAoVCore.getPlayer(playerIn).setCurrentDivinePower(AoV.serverAoVCore.getPlayer(playerIn).getMaxDivinePower());
		// if(!worldIn.isRemote) AoV.serverAoVCore.getPlayer(playerIn).addSkillPoints(1);
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap != null) {
			cap.addExp(player, cap.getExpNeededToLevel(), null);
		}
		// if(worldIn.isRemote) AoVOverlay.addFloatyText("test");
		return super.onItemRightClick(stack, world, player, hand);
	}

}
