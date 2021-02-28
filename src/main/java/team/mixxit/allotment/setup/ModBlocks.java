package team.mixxit.allotment.setup;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

public class ModBlocks {
    public static final RegistryObject<Block> TEST_BLOCK = register("test_block", () ->
            new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3, 10).sound(SoundType.STONE)));

    public static final RegistryObject<FlowerBlock> FLOWER_FORGET_ME_NOT = flower("forget_me_not");
    public static final RegistryObject<FlowerBlock> FLOWER_SMALL_PEONY_CORAL = flower("coral_small_peony");
    public static final RegistryObject<FlowerBlock> FLOWER_SMALL_PEONY = flower("small_peony");
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_BLUE = flower("blue_hyacinth");
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_CREAM = flower("cream_hyacinth");
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_ORANGE = flower("orange_hyacinth");
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_RED = flower("red_hyacinth");
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_VIOLET = flower("violet_hyacinth");
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_WHITE = flower("white_hyacinth");
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_YELLOW = flower("yellow_hyacinth");

    static void register() {}

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
        return ret;
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderTypes(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(FLOWER_FORGET_ME_NOT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWER_SMALL_PEONY_CORAL.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWER_SMALL_PEONY.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWER_HYACINTH_BLUE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWER_HYACINTH_CREAM.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWER_HYACINTH_ORANGE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWER_HYACINTH_RED.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWER_HYACINTH_VIOLET.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWER_HYACINTH_WHITE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(FLOWER_HYACINTH_YELLOW.get(), RenderType.getCutout());
    }

    private static RegistryObject<FlowerBlock> flower(String name)
    {
        return register(name, () -> new FlowerBlock(Effects.POISON, 5, AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.PLANT)));
    }
}
