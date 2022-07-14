package team.mixxit.allotment.debug;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.function.ToIntFunction;

public class ItemMetadata {
    private static final Logger LOGGER = LogManager.getLogger();

    public String className;
    public String registryName;
    public String translationKey;
    public int maxStackSize;
    public String rarity;

    public static ItemMetadata fromRegistryObject(RegistryObject<Item> entry) {
        ItemMetadata meta = new ItemMetadata();

        Item item = entry.get();
        ItemStack stack = new ItemStack(item);

        meta.className = item.getClass().getName();
        meta.registryName = item.getRegistryName().toString();
        meta.translationKey = item.getTranslationKey();
        meta.maxStackSize = stack.getMaxStackSize();
        meta.rarity = stack.getRarity().name();

        return meta;
    }
}
