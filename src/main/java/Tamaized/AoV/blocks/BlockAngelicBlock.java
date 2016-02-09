package Tamaized.AoV.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.AoV.AoV;
import Tamaized.AoV.gui.GuiHandler;
import Tamaized.AoV.registry.IBasicAoV;
import Tamaized.AoV.tileentity.TileEntityAngelicBlock;

public class BlockAngelicBlock extends BlockContainer implements IBasicAoV{
	
	private final String name;
	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Z});

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
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		int l = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int flag = 0;
		if(l == 0 || l == 2) flag = 1;
		else flag = 2;
		world.setBlockState(pos, this.getStateFromMeta(flag), 2);	
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
    
    @Override
    public IBlockState getStateFromMeta(int meta){
    	return this.getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
    }
	
    @Override
    public int getMetaFromState(IBlockState state){
    	return getMetaForAxis((EnumFacing.Axis)state.getValue(AXIS));
    }
    
    @Override
    protected BlockState createBlockState(){
    	return  new BlockState(this, new IProperty[] {AXIS});
    }
    
    public static int getMetaForAxis(EnumFacing.Axis axis){
    	return axis == EnumFacing.Axis.X ? 1 : (axis == EnumFacing.Axis.Z ? 2 : 0);
    }

}
