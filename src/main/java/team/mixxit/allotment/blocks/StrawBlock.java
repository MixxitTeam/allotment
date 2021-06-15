package team.mixxit.allotment.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import team.mixxit.allotment.util.ReflectionHelper;

public class StrawBlock extends Block {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public StrawBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.fullCube();
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        // Using entityIn.setMotionMultiplier resets entityIn.fallDistance back to (see Entity.java#2301).
        // WE DO NOT WANT THIS! Instead trying to set entityIn.motionMultiplier directly to preserve fall damage.

        // FIXME Allow jumping
        try {
            ReflectionHelper.forceSetPrivateField(entityIn, "motionMultiplier", new Vector3d(1.0D, 0.75D, 1.0D));
        } catch (IllegalAccessException e) {
            LOGGER.error(e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
        }
    }

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, 0.5F);
    }
}
