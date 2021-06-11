package team.mixxit.allotment.blocks;

import net.minecraft.block.WallBlock;

public class ModWallBlock extends WallBlock {
    public String ForBlock;

    public ModWallBlock(Properties properties, String forBlock) {
        super(properties);
        ForBlock = forBlock;
    }
}
