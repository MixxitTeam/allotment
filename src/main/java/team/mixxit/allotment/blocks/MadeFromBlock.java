package team.mixxit.allotment.blocks;

import net.minecraft.block.Block;

import java.util.function.Supplier;

public class MadeFromBlock extends Block {
    private final Supplier<? extends Block> madeFrom;

    public <T extends Supplier<U>, U extends Block> MadeFromBlock(Properties properties, T madeFrom) {
        super(properties);
        this.madeFrom = madeFrom;
    }

    public Supplier<? extends Block> getMadeFrom() {
        return madeFrom;
    }
}
