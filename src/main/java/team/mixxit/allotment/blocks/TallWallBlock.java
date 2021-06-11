package team.mixxit.allotment.blocks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallHeight;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.Map;

public class TallWallBlock extends ModWallBlock {
    public TallWallBlock(Properties properties, String forBlock) {
        super(properties, forBlock);
    }

    private Map<BlockState, VoxelShape> makeShapes(float p_235624_1_, float p_235624_2_, float p_235624_3_, float p_235624_4_, float p_235624_5_, float p_235624_6_) {
        float f = 8.0F - p_235624_1_;
        float f1 = 8.0F + p_235624_1_;
        float f2 = 8.0F - p_235624_2_;
        float f3 = 8.0F + p_235624_2_;
        VoxelShape voxelshape = Block.makeCuboidShape((double)f, 0.0D, (double)f, (double)f1, (double)p_235624_3_, (double)f1);
        VoxelShape voxelshape1 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, 0.0D, (double)f3, (double)p_235624_5_, (double)f3);
        VoxelShape voxelshape2 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_5_, 16.0D);
        VoxelShape voxelshape3 = Block.makeCuboidShape(0.0D, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_5_, (double)f3);
        VoxelShape voxelshape4 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, 16.0D, (double)p_235624_5_, (double)f3);
        VoxelShape voxelshape5 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, 0.0D, (double)f3, (double)p_235624_6_, (double)f3);
        VoxelShape voxelshape6 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_6_, 16.0D);
        VoxelShape voxelshape7 = Block.makeCuboidShape(0.0D, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_6_, (double)f3);
        VoxelShape voxelshape8 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, 16.0D, (double)p_235624_6_, (double)f3);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for(Boolean obool : UP.getAllowedValues()) {
            for(WallHeight wallheight : WALL_HEIGHT_EAST.getAllowedValues()) {
                for(WallHeight wallheight1 : WALL_HEIGHT_NORTH.getAllowedValues()) {
                    for(WallHeight wallheight2 : WALL_HEIGHT_WEST.getAllowedValues()) {
                        for(WallHeight wallheight3 : WALL_HEIGHT_SOUTH.getAllowedValues()) {
                            VoxelShape voxelshape9 = VoxelShapes.empty();
                            voxelshape9 = getHeightAlteredShape(voxelshape9, wallheight, voxelshape4, voxelshape8);
                            voxelshape9 = getHeightAlteredShape(voxelshape9, wallheight2, voxelshape3, voxelshape7);
                            voxelshape9 = getHeightAlteredShape(voxelshape9, wallheight1, voxelshape1, voxelshape5);
                            voxelshape9 = getHeightAlteredShape(voxelshape9, wallheight3, voxelshape2, voxelshape6);
                            if (obool) {
                                voxelshape9 = VoxelShapes.or(voxelshape9, voxelshape);
                            }

                            BlockState blockstate = this.getDefaultState().with(UP, obool).with(WALL_HEIGHT_EAST, wallheight).with(WALL_HEIGHT_WEST, wallheight2).with(WALL_HEIGHT_NORTH, wallheight1).with(WALL_HEIGHT_SOUTH, wallheight3);
                            builder.put(blockstate.with(WATERLOGGED, Boolean.valueOf(false)), voxelshape9);
                            builder.put(blockstate.with(WATERLOGGED, Boolean.valueOf(true)), voxelshape9);
                        }
                    }
                }
            }
        }

        return builder.build();
    }

    private static VoxelShape getHeightAlteredShape(VoxelShape baseShape, WallHeight height, VoxelShape lowShape, VoxelShape tallShape) {
        return baseShape;
    }
}
