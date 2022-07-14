package team.mixxit.allotment.debug;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class BlockMetadata {
    private static final Logger LOGGER = LogManager.getLogger();

    public String className;
    public String registryName;
    public String translationKey;
    public String harvestTool;
    public float blastResistance;
    public float hardness;
    public float harvestLevel;
    public boolean solid;
    public boolean requiresTool;
    public int maxStackSize;
    public int lightLevel;

    public static BlockMetadata fromRegistryObject(RegistryObject<Block> entry) {
        BlockMetadata meta = new BlockMetadata();

        Block block = entry.get();
        BlockState state = block.getDefaultState();

        meta.className = block.getClass().getName();
        meta.registryName = block.getRegistryName().toString();
        meta.translationKey = block.getTranslationKey();
        try {
            meta.harvestTool = block.getHarvestTool(state).getName();
        } catch (NullPointerException e) {
            e.printStackTrace();
            meta.harvestTool = null;
        }
        meta.harvestLevel = block.getHarvestLevel(state);

        try {
            Field propertiesField = AbstractBlock.class.getDeclaredField("properties");
            propertiesField.setAccessible(true);
            AbstractBlock.Properties properties = (AbstractBlock.Properties) propertiesField.get(block);

            Material mat = (Material) getValue(properties, "material");

            meta.blastResistance = (float) getValue(properties, "resistance");
            meta.hardness = (float) getValue(properties, "hardness");
            meta.requiresTool = (boolean) getValue(properties, "requiresTool");
            meta.solid = mat.isSolid();
            meta.maxStackSize = new ItemStack(block.asItem()).getMaxStackSize();

            if (meta.harvestTool == null) {
                ToolType tt = (ToolType) getValue(properties, "harvestTool");
                if (tt != null) {
                    meta.harvestTool = tt.getName();
                }
            }

            ToIntFunction<BlockState> fnLightLevel = (ToIntFunction<BlockState>) getValue(properties, "lightLevel");
            meta.lightLevel = fnLightLevel.applyAsInt(state);
        } catch (NoSuchFieldException e) {
            LOGGER.error("AMBLOCKS_META NOSUCHFIELD");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOGGER.error("AMBLOCKS_META ILLEGALACCESS");
            e.printStackTrace();
        }

        return meta;
    }

    private static Object getValue(AbstractBlock.Properties properties, String name) throws NoSuchFieldException, IllegalAccessException {
        Field f = properties.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(properties);
    }
}
