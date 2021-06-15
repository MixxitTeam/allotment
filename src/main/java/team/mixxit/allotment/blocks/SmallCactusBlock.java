package team.mixxit.allotment.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import team.mixxit.allotment.setup.ModTags;

public class SmallCactusBlock extends BushBlock {
    private DyeItem dyeItem;

    public DyeItem getDyeItem() {
        return dyeItem;
    }

    public SmallCactusBlock(DyeItem dye) {
        super(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.PLANT));
        dyeItem = dye;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return ModTags.Blocks.CAN_SUSTAIN_SMALL_CACTI.contains(state.getBlock());
    }
}
