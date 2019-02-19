package tamaized.aov.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import tamaized.aov.common.gui.GuiHandler;

import javax.annotation.Nonnull;

public class BlockAngelicBlock extends Block {

	public static final EnumProperty<EnumFacing.Axis> AXIS = EnumProperty.create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);

	private final ClassType type;

	public BlockAngelicBlock(ClassType type, Block.Properties properties) {
		super(properties);
		this.setDefaultState(stateContainer.getBaseState().with(AXIS, EnumFacing.Axis.X));
		this.type = type;
	}

	@Nonnull
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@Deprecated
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if ((type != ClassType.ALL || playerIn.isCreative()) && playerIn instanceof EntityPlayerMP)
			GuiHandler.openGui(GuiHandler.GUI.SKILLS, type, (EntityPlayerMP) playerIn);
		else
			return false;
		return true;
	}

	@Override
	public IBlockState rotate(IBlockState state, IWorld world, BlockPos pos, Rotation direction) {
		switch (direction) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:

				switch (state.get(AXIS)) {
					case X:
						return state.with(AXIS, EnumFacing.Axis.Z);
					case Z:
						return state.with(AXIS, EnumFacing.Axis.X);
					default:
						return state;
				}

			default:
				return state;
		}
	}

	public enum ClassType {

		ALL,

		FAVOREDSOUL,

		CLERIC,

		PALADIN,

		ASTRO,

		DRUID;

		public static final ClassType[] values = values();

	}

}
