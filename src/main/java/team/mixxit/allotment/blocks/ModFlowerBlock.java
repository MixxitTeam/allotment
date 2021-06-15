package team.mixxit.allotment.blocks;

import net.minecraft.block.FlowerBlock;
import net.minecraft.item.DyeItem;
import net.minecraft.potion.Effect;

public class ModFlowerBlock extends FlowerBlock {
    private DyeItem dyeItem;

    public DyeItem getDyeItem() {
        return dyeItem;
    }

    public ModFlowerBlock(Effect effect, int effectDuration, DyeItem dye, Properties properties) {
        super(effect, effectDuration, properties);
        dyeItem = dye;
    }
}
