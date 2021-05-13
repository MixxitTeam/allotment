package team.mixxit.allotment.itemgroups;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import team.mixxit.allotment.setup.ModBlocks;

public class MainItemGroup extends ItemGroup {

    public MainItemGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks._COLLECTION_FLOWERS[ModBlocks.FLOWER_MARIGOLD].get().asItem());
    }
}
