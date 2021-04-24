package team.mixxit.allotment.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class FirewoodBundleBlock extends Block implements IWaterLoggable {
    private static final VoxelShape SHAPE_NORTH_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 16.0D, 9.0D, 14.0D);
    private static final VoxelShape SHAPE_EAST_WEST = Block.makeCuboidShape(2.0D, 0.0D, 0.0D, 14.0D, 9.0D, 16.0D);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public FirewoodBundleBlock() {
        super(AbstractBlock.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(0.6F).sound(SoundType.WOOD).notSolid());

        BlockState defaultState = stateContainer.getBaseState()
                .with(WATERLOGGED, false)
                .with(FACING, Direction.NORTH)
                ;
        setDefaultState(defaultState);
    }

    @Override
    public StateContainer<Block, BlockState> getStateContainer() {
        return super.getStateContainer();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState()
                .with(WATERLOGGED, context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER)
                .with(FACING, context.getPlacementHorizontalFacing().getOpposite())
                ;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED).add(FACING);
    }

    public boolean isTransparent(BlockState state) {
        return true;
    }

    private VoxelShape getHitbox(BlockState state) {
        switch (state.get(FACING)) {
            case EAST:
            case WEST:
                return SHAPE_EAST_WEST;
            case NORTH:
            case SOUTH:
            default:
                return SHAPE_NORTH_SOUTH;
        }
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getHitbox(state);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getHitbox(state);
    }

    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return getHitbox(state);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState downState = worldIn.getBlockState(pos.down());
        return !worldIn.isAirBlock(pos.down())
                && downState.getMaterial().isSolid()
                && !(downState.getBlock() instanceof FirewoodBundleBlock);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }
}
