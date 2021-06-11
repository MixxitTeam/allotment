package team.mixxit.allotment.blocks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallHeight;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.Map;

public class TallWallBlock extends ModWallBlock {
    private final Map<BlockState, VoxelShape> stateToShapeMap;
    private final Map<BlockState, VoxelShape> stateToCollisionShapeMap;
    public TallWallBlock(Properties properties, String forBlock) {
        super(properties, forBlock);
        this.setDefaultState(this.stateContainer.getBaseState().with(UP, Boolean.valueOf(true)).with(WALL_HEIGHT_NORTH, WallHeight.NONE).with(WALL_HEIGHT_EAST, WallHeight.NONE).with(WALL_HEIGHT_SOUTH, WallHeight.NONE).with(WALL_HEIGHT_WEST, WallHeight.NONE).with(WATERLOGGED, Boolean.valueOf(false)));
        this.stateToShapeMap = this.makeShapes(3.0F, 3.0F, 16.0F, 0.0F, 16.0F, 16.0F);
        this.stateToCollisionShapeMap = this.makeShapes(3.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
    }

    private Map<BlockState, VoxelShape> makeShapes(float postRadius, float baseXBegin, float postHeight, float baseYPos, float baseHeightLow, float baseHeightTall) {
        float offsetPostWidth1 = 8.0F - postRadius;
        float offsetPostWidth2 = 8.0F + postRadius;
        float offsetBaseSize1 = 8.0F - baseXBegin;
        float offsetBaseSize2 = 8.0F + baseXBegin;
        VoxelShape postShape = Block.makeCuboidShape((double)offsetPostWidth1, 0.0D, (double)offsetPostWidth1, (double)offsetPostWidth2, (double)postHeight, (double)offsetPostWidth2);
        VoxelShape northShapeLow = Block.makeCuboidShape((double)offsetBaseSize1, (double)baseYPos, 0.0D, (double)offsetBaseSize2, (double)baseHeightLow, (double)offsetBaseSize2);
        VoxelShape southShapeLow = Block.makeCuboidShape((double)offsetBaseSize1, (double)baseYPos, (double)offsetBaseSize1, (double)offsetBaseSize2, (double)baseHeightLow, 16.0D);
        VoxelShape westShapeLow = Block.makeCuboidShape(0.0D, (double)baseYPos, (double)offsetBaseSize1, (double)offsetBaseSize2, (double)baseHeightLow, (double)offsetBaseSize2);
        VoxelShape eastShapeLow = Block.makeCuboidShape((double)offsetBaseSize1, (double)baseYPos, (double)offsetBaseSize1, 16.0D, (double)baseHeightLow, (double)offsetBaseSize2);
        VoxelShape northShapeTall = Block.makeCuboidShape((double)offsetBaseSize1, (double)baseYPos, 0.0D, (double)offsetBaseSize2, (double)baseHeightTall, (double)offsetBaseSize2);
        VoxelShape southShapeTall = Block.makeCuboidShape((double)offsetBaseSize1, (double)baseYPos, (double)offsetBaseSize1, (double)offsetBaseSize2, (double)baseHeightTall, 16.0D);
        VoxelShape westShapeTall = Block.makeCuboidShape(0.0D, (double)baseYPos, (double)offsetBaseSize1, (double)offsetBaseSize2, (double)baseHeightTall, (double)offsetBaseSize2);
        VoxelShape eastShapeTall = Block.makeCuboidShape((double)offsetBaseSize1, (double)baseYPos, (double)offsetBaseSize1, 16.0D, (double)baseHeightTall, (double)offsetBaseSize2);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for(Boolean isUp : UP.getAllowedValues()) {
            for(WallHeight wallheightEast : WALL_HEIGHT_EAST.getAllowedValues()) {
                for(WallHeight wallheightNorth : WALL_HEIGHT_NORTH.getAllowedValues()) {
                    for(WallHeight wallheightWest : WALL_HEIGHT_WEST.getAllowedValues()) {
                        for(WallHeight wallheightSouth : WALL_HEIGHT_SOUTH.getAllowedValues()) {
                            VoxelShape shapeForState = VoxelShapes.empty();
                            shapeForState = getHeightAlteredShape(shapeForState, wallheightEast, eastShapeLow, eastShapeTall);
                            shapeForState = getHeightAlteredShape(shapeForState, wallheightWest, westShapeLow, westShapeTall);
                            shapeForState = getHeightAlteredShape(shapeForState, wallheightNorth, northShapeLow, northShapeTall);
                            shapeForState = getHeightAlteredShape(shapeForState, wallheightSouth, southShapeLow, southShapeTall);
                            if (isUp) {
                                shapeForState = VoxelShapes.or(shapeForState, postShape);
                            }

                            BlockState blockstate = this.getDefaultState().with(UP, isUp).with(WALL_HEIGHT_EAST, wallheightEast).with(WALL_HEIGHT_WEST, wallheightWest).with(WALL_HEIGHT_NORTH, wallheightNorth).with(WALL_HEIGHT_SOUTH, wallheightSouth);
                            builder.put(blockstate.with(WATERLOGGED, Boolean.FALSE), shapeForState);
                            builder.put(blockstate.with(WATERLOGGED, Boolean.TRUE), shapeForState);
                        }
                    }
                }
            }
        }

        return builder.build();
    }

    private static VoxelShape getHeightAlteredShape(VoxelShape baseShape, WallHeight height, VoxelShape lowShape, VoxelShape tallShape) {
        if (height == WallHeight.TALL) {
            return VoxelShapes.or(baseShape, tallShape);
        } else {
            return height == WallHeight.LOW ? VoxelShapes.or(baseShape, lowShape) : baseShape;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.stateToShapeMap.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.stateToCollisionShapeMap.get(state);
    }
}
