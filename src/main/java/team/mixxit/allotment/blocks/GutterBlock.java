package team.mixxit.allotment.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.client.model.b3d.B3DModel;

public class GutterBlock extends HorizontalFaceBlock implements IWaterLoggable {
    static final double HEIGHT = 1.0D;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape BOTTOM_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, HEIGHT, 16.0D);
    protected static final VoxelShape    TOP_AABB = Block.makeCuboidShape(0.0D, 16.0D - HEIGHT, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape   EAST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, HEIGHT, 16.0D, 16.0D);
    protected static final VoxelShape   WEST_AABB = Block.makeCuboidShape(16.0D - HEIGHT, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape  SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, HEIGHT);
    protected static final VoxelShape  NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 16.0D - HEIGHT, 16.0D, 16.0D, 16.0D);

    public GutterBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState()
                .with(FACE, AttachFace.FLOOR)
                .with(HORIZONTAL_FACING, Direction.EAST)
                .with(WATERLOGGED, Boolean.FALSE));
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return true;
    }

    public static boolean isSideSolidForDirection(IWorldReader reader, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.offset(direction);
        return reader.getBlockState(blockpos).isSolidSide(reader, blockpos, direction.getOpposite());
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        AttachFace face = state.get(FACE);
        Direction direction = state.get(HORIZONTAL_FACING);
        if (AttachFace.FLOOR.equals(face)) {
            return BOTTOM_AABB;
        } else if (AttachFace.CEILING.equals(face)) {
            return TOP_AABB;
        } else if (AttachFace.WALL.equals(face)) {
            if (Direction.EAST.equals(direction)) {
                return EAST_AABB;
            } else if (Direction.NORTH.equals(direction)) {
                return NORTH_AABB;
            } else if (Direction.SOUTH.equals(direction)) {
                return SOUTH_AABB;
            } else if (Direction.WEST.equals(direction)) {
                return WEST_AABB;
            } else
            return null;
        }
        return null;
    }

    static final int PART_LEFT = 0;
    static final int PART_MIDDLE = 1;
    static final int PART_RIGHT = 2;

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = this.getDefaultState();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        Direction direction = context.getFace();
        final double xpos = context.getHitVec().x - (double) context.getPos().getX();
        final double ypos = context.getHitVec().y - (double) context.getPos().getY();
        final double zpos = context.getHitVec().z - (double) context.getPos().getZ();
        if (!context.replacingClickedOnBlock() && direction.getAxis().isHorizontal()) {
            double hpos = 0.5D;

            if (direction.equals(Direction.SOUTH))
                hpos = xpos;
            else if (direction.equals(Direction.WEST))
                hpos = zpos;
            else if (direction.equals(Direction.NORTH))
                hpos = 1 - xpos;
            else if (direction.equals(Direction.EAST))
                hpos = 1 - zpos;

            int part = PART_MIDDLE;

            if (hpos < 0.25D)
                part = PART_LEFT;
            else if (hpos > 0.75D)
                part = PART_RIGHT;

            if (part == PART_MIDDLE && ypos > 0.75D)
                blockstate = blockstate.with(FACE, AttachFace.CEILING).with(HORIZONTAL_FACING, direction);
            else if (part == PART_MIDDLE && ypos < 0.25D)
                blockstate = blockstate.with(FACE, AttachFace.FLOOR).with(HORIZONTAL_FACING, direction);
            else if (part == PART_LEFT && ypos >= 0.25D && ypos <= 0.75D)
                blockstate = blockstate.with(FACE, AttachFace.WALL).with(HORIZONTAL_FACING, direction.rotateYCCW());
            else if (part == PART_RIGHT && ypos >= 0.25D && ypos <= 0.75D)
                blockstate = blockstate.with(FACE, AttachFace.WALL).with(HORIZONTAL_FACING, direction.rotateY());
            else
                blockstate = blockstate.with(FACE, AttachFace.WALL).with(HORIZONTAL_FACING, direction);
        }  else {
            if (xpos >= 0.25D && xpos <= 0.75D && zpos < 0.25D)
                blockstate = blockstate.with(FACE, AttachFace.WALL).with(HORIZONTAL_FACING, Direction.SOUTH);
            else if (xpos >= 0.25D && xpos <= 0.75D && zpos > 0.75D)
                blockstate = blockstate.with(FACE, AttachFace.WALL).with(HORIZONTAL_FACING, Direction.NORTH);
            else if (zpos >= 0.25D && zpos <= 0.75D && xpos < 0.25D)
                blockstate = blockstate.with(FACE, AttachFace.WALL).with(HORIZONTAL_FACING, Direction.EAST);
            else if (zpos >= 0.25D && zpos <= 0.75D && xpos > 0.75D)
                blockstate = blockstate.with(FACE, AttachFace.WALL).with(HORIZONTAL_FACING, Direction.WEST);
            else
                blockstate = blockstate.with(FACE, direction == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING);

            if (!direction.equals(Direction.UP) && !direction.equals(Direction.DOWN)) {
                blockstate = blockstate.with(HORIZONTAL_FACING, direction);
            }
        }

        return blockstate.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, FACE, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
