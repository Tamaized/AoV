package tamaized.aov.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import tamaized.aov.common.gui.GuiHandler;

import javax.annotation.Nonnull;

public class BlockAngelicBlock extends Block {

	public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.create("axis", Direction.Axis.class, Direction.Axis.X, Direction.Axis.Z);

	private final ClassType type;

	public BlockAngelicBlock(ClassType type, Block.Properties properties) {
		super(properties);
		this.setDefaultState(stateContainer.getBaseState().with(AXIS, Direction.Axis.X));
		this.type = type;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	@Nonnull
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@Deprecated
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if ((type != ClassType.ALL || player.isCreative()) && player instanceof ServerPlayerEntity)
			GuiHandler.openGui(GuiHandler.GUI.SKILLS, type, (ServerPlayerEntity) player);
		else
			return (type != ClassType.ALL || player.isCreative());
		return true;
	}

	@Override
	public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
		switch (direction) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:

				switch (state.get(AXIS)) {
					case X:
						return state.with(AXIS, Direction.Axis.Z);
					case Z:
						return state.with(AXIS, Direction.Axis.X);
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
