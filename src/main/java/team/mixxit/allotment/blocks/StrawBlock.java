package team.mixxit.allotment.blocks;

import com.google.common.math.DoubleMath;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import team.mixxit.allotment.setup.ModBlocks;
import team.mixxit.allotment.util.ReflectionHelper;

import static team.mixxit.allotment.util.Constants.DOUBLE_EPSILON;

public class StrawBlock extends Block {
    static final double SINK_IN_DEPTH = 7.0D;
    static final double SLOWNESS_HORIZONTAL = 0.8D;
    static final double SLOWNESS_VERTICAL = 0.05D;

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D - SINK_IN_DEPTH, 16.0D);

    public StrawBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        // If this straw block is above another straw block, return a full cube hitbox
        if (worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock().matchesBlock(ModBlocks.STRAW_BLOCK.get()))
            return VoxelShapes.fullCube();
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
        // WE DO NOT WANT THIS! Instead we try to set entityIn.motionMultiplier directly to preserve fall damage.
        // (We don't actually use entityIn.motionMultiplier, see below)
        try {
            if (entityIn instanceof LivingEntity) {
                final LivingEntity livingEntityIn = (LivingEntity)entityIn;
                final boolean isJumping = (boolean)ReflectionHelper.forceGetPrivateField(livingEntityIn, "isJumping");

                double x = SLOWNESS_HORIZONTAL;
                double y = 1.0D;
                double z = SLOWNESS_HORIZONTAL;
                final Vector3d m = livingEntityIn.getMotion();

                // The addition of 0.0 to the y axis motion is necessary in order to
                // properly deal with -0.0; see IEEE 754 and
                // https://web.archive.org/web/20050921090806im_/http://www.concentric.net/~Ttwang/tech/javafloat.htm#:~:text=Operations%20Involving%20Negative%20Zero
                final double my = (m.y + 0.0D);

                if (!isJumping && my < 0.0) {
                    y = SLOWNESS_VERTICAL;
                } else if (DoubleMath.fuzzyEquals(my, 0.0, DOUBLE_EPSILON)) {
                    // y stays at 1
                } else return;

                // Using Entity.motionMultiplier resets the motion to zero (see Entity.java#583) which
                // prevents jumping. Instead we directly manipulate the motion property. This also does not
                // require any additional, ugly reflection work.
                livingEntityIn.setMotion(m.mul(x, y, z));
            } else {
                // Entity is not a living entity. We can just directly set the motion multiplier as a
                // non-living entity is unlikely to jump.
                entityIn.setMotionMultiplier(state, new Vector3d(SLOWNESS_HORIZONTAL, SLOWNESS_VERTICAL, SLOWNESS_HORIZONTAL));
            }
        } catch (IllegalAccessException | IllegalArgumentException e) {
            LOGGER.error(e);
        }
    }

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, 0.5F);
    }
}
