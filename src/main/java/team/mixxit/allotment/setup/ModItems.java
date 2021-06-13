package team.mixxit.allotment.setup;

import net.minecraft.block.Blocks;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import team.mixxit.allotment.AllotmentMod;

public class ModItems {
    //public static final RegistryObject<Item> TEST_ITEM = Registration.ITEMS.register("test_item", () ->
    //        new Item(new Item.Properties().group(ItemGroup.MISC)));

    public static final RegistryObject<Item> DRIED_BAMBOO = Registration.ITEMS.register("dried_bamboo", () ->
            new Item(new Item.Properties().group(AllotmentMod.MAIN_GROUP)));

    public static final RegistryObject<Item> PAMPAS_GRASS = Registration.ITEMS.register("pampas_grass", () ->
            new BlockNamedItem(ModBlocks.PAMPAS_GRASS.get(), new Item.Properties().group(AllotmentMod.MAIN_GROUP)));

    public static final RegistryObject<Item> PAMPAS_GRASS_PINK = Registration.ITEMS.register("pink_pampas_grass", () ->
            new BlockNamedItem(ModBlocks.PAMPAS_GRASS_PINK.get(), new Item.Properties().group(AllotmentMod.MAIN_GROUP)));

    public static final RegistryObject<Item> IVY = Registration.ITEMS.register("ivy", () ->
            new BlockNamedItem(ModBlocks._COLLECTION_TINTED_OVERLAY_VINES[ModBlocks.OVERLAY_VINES_IVY].get(), new Item.Properties().group(AllotmentMod.MAIN_GROUP)));

    static void register() {}
}
