package team.mixxit.allotment.setup;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
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
import team.mixxit.allotment.blocks.LawnBlock;
import team.mixxit.allotment.blocks.TintedDoublePlantBlock;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ModBlocks {
    //public static final RegistryObject<Block> TEST_BLOCK = register("test_block", () ->
    //        new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3, 10).sound(SoundType.STONE)));

    public static final RegistryObject<Block> LAWN_BLOCK = register("lawn_block", () ->
            new LawnBlock(AbstractBlock.Properties.create(Material.EARTH).hardnessAndResistance(0.65F).sound(SoundType.PLANT)));

    public static final RegistryObject<Block> PAMPAS_GRASS = register("pampas_grass", () ->
            new TintedDoublePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));

    public static final RegistryObject<Block> PAMPAS_GRASS_PINK = register("pink_pampas_grass", () ->
            new TintedDoublePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));

    public static final RegistryObject<FlowerBlock>[] _COLLECTION_FLOWERS = new RegistryObject[]{
            flower("forget_me_not"),
            flower("coral_small_peony"),
            flower("small_peony"),
            flower("blue_hyacinth"),
            flower("cream_hyacinth"),
            flower("orange_hyacinth"),
            flower("red_hyacinth"),
            flower("violet_hyacinth"),
            flower("white_hyacinth"),
            flower("yellow_hyacinth"),
            flower("lantana"),
            flower("actaea_racemosa"),
            flower("marigold"),
            flower("blue_convolvulus_tricolor"),
            flower("red_convolvulus_tricolor"),
            flower("yellow_hollyhock"),
            flower("blue_agapanthus"),
            flower("pink_agapanthus"),
            flower("white_agapanthus"),
            flower("anemone"),
            flower("love_in_a_mist"),
            flower("geranium"),
            flower("green_carnation"),
            flower("red_anthurium")
    };

    public static final RegistryObject<FlowerBlock> FLOWER_FORGET_ME_NOT = _COLLECTION_FLOWERS[0];
    public static final RegistryObject<FlowerBlock> FLOWER_SMALL_PEONY_CORAL = _COLLECTION_FLOWERS[1];
    public static final RegistryObject<FlowerBlock> FLOWER_SMALL_PEONY = _COLLECTION_FLOWERS[2];
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_BLUE = _COLLECTION_FLOWERS[3];
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_CREAM = _COLLECTION_FLOWERS[4];
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_ORANGE = _COLLECTION_FLOWERS[5];
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_RED = _COLLECTION_FLOWERS[6];
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_VIOLET = _COLLECTION_FLOWERS[7];
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_WHITE = _COLLECTION_FLOWERS[8];
    public static final RegistryObject<FlowerBlock> FLOWER_HYACINTH_YELLOW = _COLLECTION_FLOWERS[9];
    public static final RegistryObject<FlowerBlock> FLOWER_LANTANA = _COLLECTION_FLOWERS[10];
    public static final RegistryObject<FlowerBlock> FLOWER_ACTAEA_RACEMOSA = _COLLECTION_FLOWERS[11];
    public static final RegistryObject<FlowerBlock> FLOWER_MARIGOLD = _COLLECTION_FLOWERS[12];
    public static final RegistryObject<FlowerBlock> FLOWER_CONVOLVULUS_TRICOLOR_BLUE = _COLLECTION_FLOWERS[13];
    public static final RegistryObject<FlowerBlock> FLOWER_CONVOLVULUS_TRICOLOR_RED = _COLLECTION_FLOWERS[14];
    public static final RegistryObject<FlowerBlock> FLOWER_YELLOW_HOLLYHOCK = _COLLECTION_FLOWERS[15];
    public static final RegistryObject<FlowerBlock> FLOWER_AGAPANTHUS_BLUE = _COLLECTION_FLOWERS[16];
    public static final RegistryObject<FlowerBlock> FLOWER_AGAPANTHUS_PINK = _COLLECTION_FLOWERS[17];
    public static final RegistryObject<FlowerBlock> FLOWER_AGAPANTHUS_WHITE = _COLLECTION_FLOWERS[18];
    public static final RegistryObject<FlowerBlock> FLOWER_ANEMONE = _COLLECTION_FLOWERS[19];
    public static final RegistryObject<FlowerBlock> FLOWER_LOVE_IN_A_MIST = _COLLECTION_FLOWERS[20];
    public static final RegistryObject<FlowerBlock> FLOWER_GERANIUM = _COLLECTION_FLOWERS[21];
    public static final RegistryObject<FlowerBlock> FLOWER_GREEN_CARNATION = _COLLECTION_FLOWERS[22];
    public static final RegistryObject<FlowerBlock> FLOWER_RED_ANTHURIUM = _COLLECTION_FLOWERS[23];

    public static ArrayList<FlowerPotBlock> _COLLECTION_POTTED_FLOWERS;

    static void register() {
        _COLLECTION_POTTED_FLOWERS = new ArrayList<>();

        Block flowerPot = Blocks.FLOWER_POT;

        Supplier<FlowerPotBlock> flowerPotSupplier = () -> (FlowerPotBlock)flowerPot;

        for (RegistryObject<FlowerBlock> _flower : _COLLECTION_FLOWERS) {
            Block.Properties props = Block.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance();
            FlowerPotBlock potted = new FlowerPotBlock(flowerPotSupplier, () -> _flower.get().getBlock(), props);

            Registration.BLOCKS.register("potted_" + _flower.getId().getPath(), () -> potted);

            ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(_flower.getId(), () -> potted);

            _COLLECTION_POTTED_FLOWERS.add(potted);
        }
    }

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
        for (RegistryObject<FlowerBlock> _flower : _COLLECTION_FLOWERS) {
            RenderTypeLookup.setRenderLayer(_flower.get(), RenderType.getCutout());
        }
        for (FlowerPotBlock _flowerPotBlock : _COLLECTION_POTTED_FLOWERS) {
            RenderTypeLookup.setRenderLayer(_flowerPotBlock, RenderType.getCutout());
        }
        RenderTypeLookup.setRenderLayer(LAWN_BLOCK.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(PAMPAS_GRASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(PAMPAS_GRASS_PINK.get(), RenderType.getCutout());
    }

    private static RegistryObject<FlowerBlock> flower(String name)
    {
        return register(name, () -> new FlowerBlock(Effects.POISON, 5, AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.PLANT)));
    }
}
