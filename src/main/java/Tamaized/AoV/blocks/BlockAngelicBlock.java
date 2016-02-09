package Tamaized.AoV.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.AoV.AoV;
import Tamaized.AoV.gui.GuiHandler;
import Tamaized.AoV.registry.IBasicAoV;
import Tamaized.AoV.tileentity.TileEntityAngelicBlock;

public class BlockAngelicBlock extends BlockContainer implements IBasicAoV{
	
	private final String name;

	public BlockAngelicBlock(Material materialIn, String n) {
		super(materialIn);
		name = n;
		this.useNeighborBrightness = true;
		setUnlocalizedName(name);
		GameRegistry.registerBlock(this, "blocks/"+n);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAngelicBlock();
	}

	@Override
	public String getName() {
		return name;
	}
	
	public int getRenderType(){
        return 2;
    }
	
	public int quantityDropped(Random random)
    {
        return 0;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }
    
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		FMLNetworkHandler.openGui(playerIn, AoV.instance, GuiHandler.GUI_SKILLS, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

}
