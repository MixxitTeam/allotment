package team.mixxit.allotment.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import team.mixxit.allotment.setup.ModTags;

public class SmallCactusBlock extends BushBlock {
    public SmallCactusBlock() {
        super(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.PLANT));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return ModTags.Blocks.CAN_SUSTAIN_SMALL_CACTI.contains(state.getBlock());
    }
}
