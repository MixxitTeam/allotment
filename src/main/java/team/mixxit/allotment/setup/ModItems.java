package team.mixxit.allotment.setup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> TEST_ITEM = Registration.ITEMS.register("test_item", () ->
            new Item(new Item.Properties().group(ItemGroup.MISC)));

    static void register() {}
}
