package team.mixxit.allotment.blocks;

import net.minecraft.block.FenceBlock;

public class ModFenceBlock extends FenceBlock {
    public String ForBlock;

    public ModFenceBlock(Properties properties, String forBlock) {
        super(properties);
        ForBlock = forBlock;
    }
}
