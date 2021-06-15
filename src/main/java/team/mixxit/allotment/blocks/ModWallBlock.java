package team.mixxit.allotment.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class ModWallBlock extends WallBlock {
    public Block ForBlock;

    public ModWallBlock(Properties properties, Block forBlock) {
        super(properties);
        ForBlock = forBlock;
    }
}
