package team.mixxit.allotment.blocks;

import net.minecraft.block.SlabBlock;

public class ModSlabBlock extends SlabBlock {
    public String ForBlock;
    public ModSlabBlock(Properties properties, String forBlock) {
        super(properties);
        ForBlock = forBlock;
    }
}
