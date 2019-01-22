package tamaized.aov.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.gui.GuiHandler;
import tamaized.tammodized.common.blocks.TamBlock;

import javax.annotation.Nonnull;

public class BlockAngelicBlock extends TamBlock {

	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);

	private final ClassType type;

	public BlockAngelicBlock(ClassType type, CreativeTabs tab, Material materialIn, String n, float f) {
		super(tab, materialIn, n, f, SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
		this.useNeighborBrightness = true;
		this.type = type;
	}

	public static int getMetaForAxis(EnumFacing.Axis axis) {
		if (axis == EnumFacing.Axis.X)
			return 1;
		else
			return axis == EnumFacing.Axis.Z ? 2 : 0;

	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer, EnumHand hand) {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(AXIS, placer.getHorizontalFacing().rotateY().getAxis());
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (type != ClassType.ALL || playerIn.isCreative())
			GuiHandler.openGUI(GuiHandler.GUI.SKILLS, type, playerIn, worldIn);
		else
			return false;
		return true;
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return getMetaForAxis(state.getValue(AXIS));
	}

	@Nonnull
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AXIS);
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public IBlockState withRotation(@Nonnull IBlockState state, Rotation rot) {
		switch (rot) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:

				switch (state.getValue(AXIS)) {
					case X:
						return state.withProperty(AXIS, EnumFacing.Axis.Z);
					case Z:
						return state.withProperty(AXIS, EnumFacing.Axis.X);
					default:
						return state;
				}

			default:
				return state;
		}
	}

	public enum ClassType {

		ALL,

		CASTER,

		HEALER,

		DEFENDER,

		ASTRO,

		DRUID;

		public static final ClassType[] values = values();

	}

}
