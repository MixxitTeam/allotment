package team.mixxit.allotment.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public class RotatableFallingBlock extends FallingBlock {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public RotatableFallingBlock(Properties properties) {
        super(properties);

        BlockState defaultState = stateContainer.getBaseState()
                .with(FACING, Direction.NORTH);
        setDefaultState(defaultState);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder
                .add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos().up());
        return getDefaultState()
                .with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public StateContainer<Block, BlockState> getStateContainer() {
        return super.getStateContainer();
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }
}
