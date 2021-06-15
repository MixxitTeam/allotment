package team.mixxit.allotment.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import team.mixxit.allotment.AllotmentMod;

public class RegistryHelper {
    public static Block getBlockFromNamespacedId(String id) {
        return RegistryObject.of(mcLoc(id), ForgeRegistries.BLOCKS).get();
    }

    public static Block getBlockFromNamespacedId(ResourceLocation resourceLocation) {
        return RegistryObject.of(resourceLocation, ForgeRegistries.BLOCKS).get();
    }

    public static Item getItemFromNamespacedId(String id) {
        return RegistryObject.of(mcLoc(id), ForgeRegistries.ITEMS).get();
    }

    public static Item getItemFromNamespacedId(ResourceLocation resourceLocation) {
        return RegistryObject.of(resourceLocation, ForgeRegistries.ITEMS).get();
    }

    private static ResourceLocation modLoc(String name) {
        return new ResourceLocation(AllotmentMod.MOD_ID, name);
    }
    private static ResourceLocation mcLoc(String name) {
        return new ResourceLocation(name);
    }
}
