package team.mixxit.allotment.blocks;

import net.minecraft.block.FenceGateBlock;

public class ModFenceGateBlock extends FenceGateBlock {
    public String ForBlock;

    public ModFenceGateBlock(Properties properties, String forBlock) {
        super(properties);
        ForBlock = forBlock;
    }
}
