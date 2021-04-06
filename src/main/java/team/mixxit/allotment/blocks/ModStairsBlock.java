package team.mixxit.allotment.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class ModStairsBlock extends StairsBlock {
    public String ForBlock;

    public ModStairsBlock(BlockState state, Properties properties, String forBlock) {
        super(state, properties);
        ForBlock = forBlock;
    }
}
