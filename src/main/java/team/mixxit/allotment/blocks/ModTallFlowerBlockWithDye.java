package team.mixxit.allotment.blocks;

import net.minecraft.block.TallFlowerBlock;
import net.minecraft.item.DyeItem;
import team.mixxit.allotment.interf.IFlowerDyeProvider;

public class ModTallFlowerBlockWithDye extends TallFlowerBlock implements IFlowerDyeProvider {
    private DyeItem dyeItem;

    public ModTallFlowerBlockWithDye(Properties properties, DyeItem dye) {
        super(properties);
        dyeItem = dye;
    }

    @Override
    public DyeItem getDye() {
        return dyeItem;
    }
}
