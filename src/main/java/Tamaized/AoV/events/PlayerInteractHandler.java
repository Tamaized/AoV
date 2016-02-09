package Tamaized.AoV.events;

import Tamaized.AoV.AoV;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerInteractHandler {
	
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent e){
		if(e.entityPlayer != null && e.action == e.action.RIGHT_CLICK_BLOCK && e.entityPlayer.worldObj.getBlockState(e.pos) == Blocks.stone.getStateFromMeta(2)){
			EntityPlayer player = e.entityPlayer;
			if(player.getCurrentEquippedItem() == null) return;
			if(player.getCurrentEquippedItem().getItem() == Items.diamond){
				if(doChecks(player.worldObj, e.pos, e.face)){
					player.getCurrentEquippedItem().stackSize--;
					player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, e.pos.getX(), e.pos.getY()+2, e.pos.getZ()));
					player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, e.pos.getX()+2, e.pos.getY()-1, e.pos.getZ()+2));
					player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, e.pos.getX()-2, e.pos.getY()-1, e.pos.getZ()+2));
					player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, e.pos.getX()+2, e.pos.getY()-1, e.pos.getZ()-2));
					player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, e.pos.getX()-2, e.pos.getY()-1, e.pos.getZ()-2));
					setBlocks(player.worldObj, e.pos);
				}
			}
		}
	}

	private boolean doChecks(World world, BlockPos pos, EnumFacing face){
		boolean flag = false;
		if(world.getBlockState(pos) == Blocks.stone.getStateFromMeta(2)){
			boolean isX = (face == EnumFacing.NORTH || face == EnumFacing.SOUTH) ? true : false;
			if(
					world.getBlockState(pos.add(0, -1, 0)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(0, -2, 0)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(1, -2, 1)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(1, -2, 0)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(1, -2, -1)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(0, -2, 1)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(0, -2, -1)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(-1, -2, 1)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(-1, -2, 0)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(-1, -2, -1)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(2, -2, 2)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(-2, -2, 2)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(-2, -2, -2)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(2, -2, -2)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(2, -2, 1)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(2, -2, 0)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(2, -2, -1)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(-2, -2, 1)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(-2, -2, 0)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(-2, -2, -1)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(1, -2, 2)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(0, -2, 2)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(-1, -2, 2)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(1, -2, -2)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(0, -2, -2)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(-1, -2, -2)) == Blocks.stone.getStateFromMeta(2) &&
					world.getBlockState(pos.add(0, 1, 0)) == Blocks.stone.getStateFromMeta(6) &&
					world.getBlockState(pos.add(0, 2, 0)).getBlock() == Blocks.air
					){
				if(isX){
					if(
							world.getBlockState(pos.add(-5, 0, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(-5, 1, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(-4, 1, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(-3, 1, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(-4, 2, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(-3, 2, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(-2, 2, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(-1, 2, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(-3, 3, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(-2, 3, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(5, 0, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(5, 1, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(4, 1, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(3, 1, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(4, 2, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(3, 2, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(2, 2, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(1, 2, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(3, 3, 0)) == Blocks.stone.getStateFromMeta(4) &&
							world.getBlockState(pos.add(2, 3, 0)) == Blocks.stone.getStateFromMeta(4)
							){
						flag = true;
					}
				}else{
					
				}
			}
		}
		return flag;
	}
	
	private void setBlocks(World world, BlockPos pos){
		for(int x = -5; x<6; x++){
			for(int y = -1; y<5; y++){
				for(int z = -5; z<6; z++){
					world.setBlockToAir(pos.add(x, y, z));
				}
			}
		}
		world.setBlockState(pos.add(0, -1, 0), AoV.blocks.angelicBlock.getDefaultState());
	}
	
}
