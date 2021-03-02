package team.mixxit.allotment.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class HoseReelBlock extends Block {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 16.0D, 15.0D, 14.0D);

    //public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public HoseReelBlock() {
        super(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(4.0F).sound(SoundType.METAL).notSolid());
        //this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    public boolean isTransparent(BlockState state) {
        return true;
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState downState = worldIn.getBlockState(pos.down());
        return !worldIn.isAirBlock(pos.down())
                && downState.getMaterial().isSolid()
                && !(downState.getBlock() instanceof team.mixxit.allotment.blocks.FirewoodBundleBlock);
    }

/*
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }
 */
}
