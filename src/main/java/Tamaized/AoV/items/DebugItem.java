package Tamaized.AoV.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.CureLightWounds;
import Tamaized.AoV.gui.GuiHandler;
import Tamaized.AoV.registry.IBasicAoV;

public class DebugItem extends Item implements IBasicAoV{

	private final String name;

	public DebugItem(String n) {
		super();
		name = n;
		setUnlocalizedName(name);
		GameRegistry.registerItem(this, "items/"+n);
	}

	@Override
	public String getName() {
		return name;
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
		//FMLNetworkHandler.openGui(playerIn, AoV.instance, GuiHandler.GUI_SKILLS, worldIn, pos.getX(), pos.getY(), pos.getZ());
		//if(worldIn.isRemote) AbilityBase.fromName(CureLightWounds.getName()).activate(playerIn, AoV.clientAoVCore.getPlayer(playerIn), null);
		//if(worldIn.isRemote) Tamaized.AoV.common.client.ClientProxy.bar.setSlot(AbilityBase.fromName(CureLightWounds.getName()), 0);
		//if(!worldIn.isRemote) AoV.serverAoVCore.getPlayer(playerIn).setCurrentDivinePower(AoV.serverAoVCore.getPlayer(playerIn).getMaxDivinePower());
		if(!worldIn.isRemote){
			AoV.serverAoVCore.getPlayer(playerIn).addSkillPoints(1);
		}
        return true;
    }

}
