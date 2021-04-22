package team.mixxit.allotment.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class ModStairsBlock extends StairsBlock {
    public String ForBlock;
    public String WithTexture;

    @Deprecated
    public ModStairsBlock(BlockState state, AbstractBlock.Properties properties, String forBlock) {
        super(state, properties);
        ForBlock = forBlock;
        WithTexture = forBlock;
    }

    public ModStairsBlock(java.util.function.Supplier<BlockState> state, AbstractBlock.Properties properties, String forBlock)
    {
        super(state, properties);
        ForBlock = forBlock;
        WithTexture = forBlock;
    }

    public ModStairsBlock(java.util.function.Supplier<BlockState> state, AbstractBlock.Properties properties, String forBlock, String withTexture)
    {
        super(state, properties);
        ForBlock = forBlock;
        WithTexture = withTexture;
    }
}
