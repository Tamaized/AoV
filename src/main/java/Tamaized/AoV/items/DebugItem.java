package Tamaized.AoV.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import Tamaized.AoV.AoV;
import Tamaized.TamModized.items.TamItem;

public class DebugItem extends TamItem {

	public DebugItem(String n) {
		super(AoV.tabs.tabAoV, n, 1);
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		// FMLNetworkHandler.openGui(playerIn, AoV.instance, GuiHandler.GUI_SKILLS, worldIn, pos.getX(), pos.getY(), pos.getZ());
		// if(worldIn.isRemote) AbilityBase.fromName(CureLightWounds.getName()).activate(playerIn, AoV.clientAoVCore.getPlayer(playerIn), null);
		// if(worldIn.isRemote) Tamaized.AoV.common.client.ClientProxy.bar.setSlot(AbilityBase.fromName(CureLightWounds.getName()), 0);
		// if(!worldIn.isRemote) AoV.serverAoVCore.getPlayer(playerIn).setCurrentDivinePower(AoV.serverAoVCore.getPlayer(playerIn).getMaxDivinePower());
		// if(!worldIn.isRemote) AoV.serverAoVCore.getPlayer(playerIn).addSkillPoints(1);
		if (!worldIn.isRemote) AoV.serverAoVCore.getPlayer(playerIn).addExp(AoV.serverAoVCore.getPlayer(playerIn).getXpNeededToLevel());
		else AoV.clientAoVCore.getPlayer(null).addExp(AoV.clientAoVCore.getPlayer(null).getXpNeededToLevel());
		// if(worldIn.isRemote) AoVOverlay.addFloatyText("test");
		return true;
	}

}
