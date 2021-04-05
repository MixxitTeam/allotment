package team.mixxit.allotment.setup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;

public class ModItems {
    //public static final RegistryObject<Item> TEST_ITEM = Registration.ITEMS.register("test_item", () ->
    //        new Item(new Item.Properties().group(ItemGroup.MISC)));

    public static final RegistryObject<Item> DRIED_BAMBOO = Registration.ITEMS.register("dried_bamboo", () ->
            new Item(new Item.Properties().group(AllotmentMod.MAIN_GROUP)));

    static void register() {}
}
