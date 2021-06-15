package team.mixxit.allotment.setup;

import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.ModVineBlock;

import java.util.ArrayList;

public class ModItems {
    public static final RegistryObject<Item> DRIED_BAMBOO = Registration.ITEMS.register("dried_bamboo", () ->
            new Item(new Item.Properties().group(AllotmentMod.MAIN_GROUP)));

    public static final RegistryObject<Item> PAMPAS_GRASS = Registration.ITEMS.register("pampas_grass", () ->
            new BlockNamedItem(ModBlocks.PAMPAS_GRASS.get(), new Item.Properties().group(AllotmentMod.MAIN_GROUP)));

    public static final RegistryObject<Item> PAMPAS_GRASS_PINK = Registration.ITEMS.register("pink_pampas_grass", () ->
            new BlockNamedItem(ModBlocks.PAMPAS_GRASS_PINK.get(), new Item.Properties().group(AllotmentMod.MAIN_GROUP)));

    public static final RegistryObject<Item> STRAW = Registration.ITEMS.register("straw", () ->
            new Item(new Item.Properties().group(AllotmentMod.MAIN_GROUP)));

    public static final ArrayList<RegistryObject<Item>> _COLLECTION_TINTED_OVERLAY_VINES = new ArrayList<>();

    static void register() {
        for (RegistryObject<ModVineBlock> _vine : ModBlocks._COLLECTION_TINTED_OVERLAY_VINES) {
            _COLLECTION_TINTED_OVERLAY_VINES.add(Registration.ITEMS.register(_vine.getId().getPath(), () -> new BlockNamedItem(_vine.get(), new Item.Properties().group(AllotmentMod.MAIN_GROUP))));
        }
    }
}
