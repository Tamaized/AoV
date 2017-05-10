package Tamaized.AoV.events;

import Tamaized.AoV.AoV;
import Tamaized.AoV.blocks.BlockAngelicBlock;
import Tamaized.AoV.capabilities.CapabilityList;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerInteractHandler {
	
	@SubscribeEvent
	public void onXPGain(PlayerPickupXpEvent e){
		EntityPlayer player = e.getEntityPlayer();
		if(player != null && player.hasCapability(CapabilityList.AOV, null)){
			player.getCapability(CapabilityList.AOV, null).addExp(player, e.getOrb().getXpValue(), null);
		}
	}

	@SubscribeEvent
	public void onInteract(PlayerInteractEvent.RightClickBlock e) {
		if (e.getEntityPlayer() != null && e.getEntityPlayer().world.getBlockState(e.getPos()) == Blocks.STONE.getStateFromMeta(2)) {
			EntityPlayer player = e.getEntityPlayer();
			if (e.getItemStack() == null) return;
			if (e.getItemStack().getItem() == Items.DIAMOND) {
				if (doChecks(player.world, e.getPos(), e.getFace())) {
					e.getItemStack().stackSize -= (1);
					setBlocks(player.world, e.getPos(), e.getFace());
					player.world.spawnEntity(new EntityLightningBolt(player.world, e.getPos().getX(), e.getPos().getY() + 2, e.getPos().getZ(), false));
					player.world.spawnEntity(new EntityLightningBolt(player.world, e.getPos().getX() + 2, e.getPos().getY() - 1, e.getPos().getZ() + 2, false));
					player.world.spawnEntity(new EntityLightningBolt(player.world, e.getPos().getX() - 2, e.getPos().getY() - 1, e.getPos().getZ() + 2, false));
					player.world.spawnEntity(new EntityLightningBolt(player.world, e.getPos().getX() + 2, e.getPos().getY() - 1, e.getPos().getZ() - 2, false));
					player.world.spawnEntity(new EntityLightningBolt(player.world, e.getPos().getX() - 2, e.getPos().getY() - 1, e.getPos().getZ() - 2, false));
				}
			}
		}
	}

	private boolean doChecks(World world, BlockPos pos, EnumFacing face) {
		boolean flag = false;
		if (world.getBlockState(pos) == Blocks.STONE.getStateFromMeta(2)) {
			if (world.getBlockState(pos.add(0, -1, 0)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(0, -2, 0)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(1, -2, 1)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(1, -2, 0)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(1, -2, -1)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(0, -2, 1)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(0, -2, -1)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(-1, -2, 1)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(-1, -2, 0)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(-1, -2, -1)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(2, -2, 2)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(-2, -2, 2)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(-2, -2, -2)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(2, -2, -2)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(2, -2, 1)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(2, -2, 0)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(2, -2, -1)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(-2, -2, 1)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(-2, -2, 0)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(-2, -2, -1)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(1, -2, 2)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(0, -2, 2)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(-1, -2, 2)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(1, -2, -2)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(0, -2, -2)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(-1, -2, -2)) == Blocks.STONE.getStateFromMeta(2) &&

					world.getBlockState(pos.add(0, 1, 0)) == Blocks.STONE.getStateFromMeta(6) &&

					world.getBlockState(pos.add(0, 2, 0)).getBlock() == Blocks.AIR) {

				if (face.getAxis() == face.getAxis().Z) {

					if (world.getBlockState(pos.add(-5, 0, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(-5, 1, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(-4, 1, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(-3, 1, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(-4, 2, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(-3, 2, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(-2, 2, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(-1, 2, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(-3, 3, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(-2, 3, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(5, 0, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(5, 1, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(4, 1, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(3, 1, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(4, 2, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(3, 2, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(2, 2, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(1, 2, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(3, 3, 0)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(2, 3, 0)) == Blocks.STONE.getStateFromMeta(4)) {
						flag = true;
					}

				} else {

					if (world.getBlockState(pos.add(0, 0, -5)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 1, -5)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 1, -4)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 1, -3)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 2, -4)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 2, -3)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 2, -2)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 2, -1)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 3, -3)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 3, -2)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 0, 5)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 1, 5)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 1, 4)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 1, 3)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 2, 4)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 2, 3)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 2, 2)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 2, 1)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 3, 3)) == Blocks.STONE.getStateFromMeta(4) &&

							world.getBlockState(pos.add(0, 3, 2)) == Blocks.STONE.getStateFromMeta(4)) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	private void setBlocks(World world, BlockPos pos, EnumFacing face) {
		for (int x = -5; x < 6; x++) {
			for (int y = -1; y < 5; y++) {
				for (int z = -5; z < 6; z++) {
					world.setBlockToAir(pos.add(x, y, z));
				}
			}
		}
		world.setBlockState(pos.add(2, -2, 2), Blocks.NETHERRACK.getDefaultState());
		world.setBlockState(pos.add(2, -2, -2), Blocks.NETHERRACK.getDefaultState());
		world.setBlockState(pos.add(-2, -2, 2), Blocks.NETHERRACK.getDefaultState());
		world.setBlockState(pos.add(-2, -2, -2), Blocks.NETHERRACK.getDefaultState());
		world.setBlockState(pos.add(0, -1, 0), AoV.blocks.angelicBlock.getDefaultState().withProperty(BlockAngelicBlock.AXIS, face.getAxis()));
	}

}
