package team.mixxit.allotment.setup;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.*;
import team.mixxit.allotment.itemgroups.MainItemGroup;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ModBlocks {
    //public static final RegistryObject<Block> TEST_BLOCK = register("test_block", () ->
    //        new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3, 10).sound(SoundType.STONE)));

    public static final RegistryObject<Block> ALLOTMENT_LOGO_1 = registerNoCreative("allotment_logo_1", () ->
            new Block(AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));

    public static final RegistryObject<Block> ALLOTMENT_LOGO_2 = registerNoCreative("allotment_logo_2", () ->
            new Block(AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));

    public static final RegistryObject<Block> LAWN_BLOCK = register("lawn_block", () ->
            new LawnBlock(AbstractBlock.Properties.create(Material.EARTH).hardnessAndResistance(0.65F).sound(SoundType.PLANT)));

    public static final RegistryObject<Block> PAMPAS_GRASS = registerNoItem("pampas_grass", () ->
            new TintedDoublePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));

    public static final RegistryObject<Block> PAMPAS_GRASS_PINK = registerNoItem("pink_pampas_grass", () ->
            new TintedDoublePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));

    public static final RegistryObject<Block> ZEN_GRAVEL_NORMAL = register("zen_gravel", () ->
            new RotatableFallingBlock(AbstractBlock.Properties.create(Material.SAND).hardnessAndResistance(0.5F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)));

    public static final RegistryObject<Block> ZEN_GRAVEL_STRAIGHT = register("zen_gravel_straight", () ->
            new RotatableFallingBlock(AbstractBlock.Properties.create(Material.SAND).hardnessAndResistance(0.5F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)));

    public static final RegistryObject<Block> ZEN_GRAVEL_CORNER = register("zen_gravel_corner", () ->
            new RotatableFallingBlock(AbstractBlock.Properties.create(Material.SAND).hardnessAndResistance(0.5F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)));

    public static final RegistryObject<Block> ZEN_GRAVEL_END = register("zen_gravel_end", () ->
            new RotatableFallingBlock(AbstractBlock.Properties.create(Material.SAND).hardnessAndResistance(0.5F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)));

    public static final RegistryObject<RotatedPillarBlock> ELDER_LOG = register("elder_log", () -> createLogBlock(MaterialColor.SAND, MaterialColor.LIGHT_GRAY));
    public static final RegistryObject<Block> ELDER_PLANKS = register("elder_planks", () ->
            new Block(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ELDER_LEAVES = register("elder_leaves", () -> createLeavesBlock());

    public static final RegistryObject<Block> FIREWOOD_SPRUCE = register("spruce_firewood_bundle", () -> new FirewoodBundleBlock());
    public static final RegistryObject<Block> HOSE_REEL = register("hose_reel", () -> new HoseReelBlock());

    public static final RegistryObject<RotatedPillarBlock> BAMBOO_BLOCK = register("bamboo_block", () -> createRotatableBlock(MaterialColor.GREEN, MaterialColor.GREEN, 1.0f, 1.0f, Material.BAMBOO, SoundType.BAMBOO));
    public static final RegistryObject<RotatedPillarBlock> DRIED_BAMBOO_BLOCK = register("dried_bamboo_block", () -> createRotatableBlock(MaterialColor.SAND, MaterialColor.SAND, 0.8f, 0.8f, Material.EARTH, SoundType.PLANT));

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
            flower("red_anthurium"),
            flower("anigozanthos_manglesii"),
            flower("guzmania"),
            flower("tillandsia_cyanea"),
            flower("tropical_orchid"),
            flower("violet"),
            flower("westringia"),
            flower("white_anthurium"),
            flower("white_narcissus"),
            flower("yellow_narcissus")
    };

    public static final RegistryObject<SmallCactusBlock>[] _COLLECTION_SMALL_CACTI = new RegistryObject[]{
            smallCactus("bunny_ears"),
            smallCactus("geohintonia"),
            smallCactus("obregonia"),
            smallCactus("small_desert_rose")
    };

    public static final RegistryObject<ModMushroomBlock>[] _COLLECTION_MUSHROOMS = new RegistryObject[]{
            mushroom("boletus_edulis"),
            mushroom("button_mushroom"),
            mushroom("chanterelle"),
            mushroom("cookeina")
    };

    public static final RegistryObject<TrapDoorBlock>[] _COLLECTION_TRAPDOORS = new RegistryObject[]{
            trapdoor("bamboo_trapdoor", MaterialColor.GREEN, SoundType.BAMBOO),
            trapdoor("dried_bamboo_trapdoor", MaterialColor.SAND, SoundType.BAMBOO),
            trapdoor("elder_trapdoor", MaterialColor.SAND, SoundType.WOOD)
    };

    //region Plants Index Constants
    public static final int FLOWER_FORGET_ME_NOT = 0;
    public static final int FLOWER_SMALL_PEONY_CORAL = 1;
    public static final int FLOWER_SMALL_PEONY = 2;
    public static final int FLOWER_HYACINTH_BLUE = 3;
    public static final int FLOWER_HYACINTH_CREAM = 4;
    public static final int FLOWER_HYACINTH_ORANGE = 5;
    public static final int FLOWER_HYACINTH_RED = 6;
    public static final int FLOWER_HYACINTH_VIOLET = 7;
    public static final int FLOWER_HYACINTH_WHITE = 8;
    public static final int FLOWER_HYACINTH_YELLOW = 9;
    public static final int FLOWER_LANTANA = 10;
    public static final int FLOWER_ACTAEA_RACEMOSA = 11;
    public static final int FLOWER_MARIGOLD = 12;
    public static final int FLOWER_CONVOLVULUS_TRICOLOR_BLUE = 13;
    public static final int FLOWER_CONVOLVULUS_TRICOLOR_RED = 14;
    public static final int FLOWER_YELLOW_HOLLYHOCK = 15;
    public static final int FLOWER_AGAPANTHUS_BLUE = 16;
    public static final int FLOWER_AGAPANTHUS_PINK = 17;
    public static final int FLOWER_AGAPANTHUS_WHITE = 18;
    public static final int FLOWER_ANEMONE = 19;
    public static final int FLOWER_LOVE_IN_A_MIST = 20;
    public static final int FLOWER_GERANIUM = 21;
    public static final int FLOWER_GREEN_CARNATION = 22;
    public static final int FLOWER_RED_ANTHURIUM = 23;
    public static final int FLOWER_ANIGOZANTHOS_MANGLESII = 24;
    public static final int FLOWER_GUZMANIA = 25;
    public static final int FLOWER_TILLANDSIA_CYANEA = 26;
    public static final int FLOWER_TROPICAL_ORCHID = 27;
    public static final int FLOWER_VIOLET = 28;
    public static final int FLOWER_WESTRINGIA = 29;
    public static final int FLOWER_WHITE_ANTHURIUM = 30;
    public static final int FLOWER_WHITE_NARCISSUS = 31;
    public static final int FLOWER_YELLOW_NARCISSUS = 32;

    public static final int SMALL_CACTUS_BUNNY_EARS = 0;
    public static final int SMALL_CACTUS_GEOHINTONIA = 1;
    public static final int SMALL_CACTUS_OBREGONIA = 2;
    public static final int SMALL_CACTUS_SMALL_DESERT_ROSE = 3;

    public static final int MUSHROOM_BOLETUS_EDULIS = 0;
    public static final int MUSHROOM_BUTTON_MUSHROOM = 1;
    public static final int MUSHROOM_CHANTERELLE = 2;
    public static final int MUSHROOM_COOKEINA = 3;
//endregion

    public static ArrayList<FlowerPotBlock> _COLLECTION_POTTED_PLANTS;
    public static ArrayList<RegistryObject<Block>> _COLLECTION_PLANKS = new ArrayList<>();

    static void register() {
        final String[] plankNames = new String[]{
                "acacia",
                "birch",
                "crimson",
                "dark_oak",
                "elder",
                "jungle",
                "oak",
                "spruce",
                "warped"
        };

        for (String _plank : plankNames) {
            _COLLECTION_PLANKS.add(plank("chipped", _plank));
            _COLLECTION_PLANKS.add(plank("weathered", _plank));
        }

        _COLLECTION_POTTED_PLANTS = new ArrayList<>();

        Block flowerPot = Blocks.FLOWER_POT;

        Supplier<FlowerPotBlock> flowerPotSupplier = () -> (FlowerPotBlock)flowerPot;

        for (RegistryObject<FlowerBlock> _flower : _COLLECTION_FLOWERS) {
            Block.Properties props = Block.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance();
            FlowerPotBlock potted = new FlowerPotBlock(flowerPotSupplier, () -> _flower.get().getBlock(), props);

            Registration.BLOCKS.register("potted_" + _flower.getId().getPath(), () -> potted);

            ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(_flower.getId(), () -> potted);

            _COLLECTION_POTTED_PLANTS.add(potted);
        }
        for (RegistryObject<ModMushroomBlock> _mushroom : _COLLECTION_MUSHROOMS) {
            Block.Properties props = Block.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance();
            FlowerPotBlock potted = new FlowerPotBlock(flowerPotSupplier, () -> _mushroom.get().getBlock(), props);

            Registration.BLOCKS.register("potted_" + _mushroom.getId().getPath(), () -> potted);

            ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(_mushroom.getId(), () -> potted);

            _COLLECTION_POTTED_PLANTS.add(potted);
        }
        for (RegistryObject<SmallCactusBlock> _cactus : _COLLECTION_SMALL_CACTI) {
            Block.Properties props = Block.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance();
            FlowerPotBlock potted = new FlowerPotBlock(flowerPotSupplier, () -> _cactus.get().getBlock(), props);

            Registration.BLOCKS.register("potted_" + _cactus.getId().getPath(), () -> potted);

            ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(_cactus.getId(), () -> potted);

            _COLLECTION_POTTED_PLANTS.add(potted);
        }
    }

    static void postRegister() {
        for (RegistryObject<FlowerBlock> _flower : _COLLECTION_FLOWERS) {
            registerCompostable(0.65F, _flower);
        }
        for (RegistryObject<ModMushroomBlock> _mushroom : _COLLECTION_MUSHROOMS) {
            registerCompostable(0.65F, _mushroom);
        }
    }

    private static void registerCompostable(float chance, RegistryObject<? extends Block> itemIn) {
        ComposterBlock.CHANCES.put(itemIn.get().asItem(), chance);
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().group(AllotmentMod.MAIN_GROUP)));
        return ret;
    }

    private static <T extends Block> RegistryObject<T> registerNoCreative(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return ret;
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderTypes(FMLClientSetupEvent event) {
        for (RegistryObject<FlowerBlock> _flower : _COLLECTION_FLOWERS) {
            RenderTypeLookup.setRenderLayer(_flower.get(), RenderType.getCutout());
        }
        for (RegistryObject<ModMushroomBlock> _mushroom : _COLLECTION_MUSHROOMS) {
            RenderTypeLookup.setRenderLayer(_mushroom.get(), RenderType.getCutout());
        }
        for (RegistryObject<SmallCactusBlock> _cactus : _COLLECTION_SMALL_CACTI) {
            RenderTypeLookup.setRenderLayer(_cactus.get(), RenderType.getCutout());
        }
        for (FlowerPotBlock _flowerPotBlock : _COLLECTION_POTTED_PLANTS) {
            RenderTypeLookup.setRenderLayer(_flowerPotBlock, RenderType.getCutout());
        }
        for (RegistryObject<TrapDoorBlock> _trapdoor : _COLLECTION_TRAPDOORS) {
            RenderTypeLookup.setRenderLayer(_trapdoor.get(), RenderType.getCutout());
        }
        RenderTypeLookup.setRenderLayer(LAWN_BLOCK.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(PAMPAS_GRASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(PAMPAS_GRASS_PINK.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ALLOTMENT_LOGO_1.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ALLOTMENT_LOGO_2.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ELDER_LEAVES.get(), RenderType.getCutoutMipped());
    }

    private static RegistryObject<TrapDoorBlock> trapdoor(String name, MaterialColor color, SoundType sound) {
        return register(name, () -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, color).hardnessAndResistance(3.0F).sound(sound).notSolid().setAllowsSpawn(ModBlocks::neverAllowSpawn)));
    }

    private static RegistryObject<FlowerBlock> flower(String name)
    {
        return register(name, () -> new FlowerBlock(Effects.POISON, 5, AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.PLANT)));
    }

    private static RegistryObject<SmallCactusBlock> smallCactus(String name)
    {
        return register(name, () -> new SmallCactusBlock());
    }

    private static RegistryObject<Block> plank(String prefix, String name)
    {
        return register(prefix + "_" + name + "_planks", () -> new Block(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    }

    private static RegistryObject<ModMushroomBlock> mushroom(String name)
    {
        return register(name, () -> new ModMushroomBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT).setLightLevel((state) -> 1).setNeedsPostProcessing(ModBlocks::needsPostProcessing)));
    }

    private static RotatedPillarBlock createRotatableBlock(MaterialColor topColor, MaterialColor barkColor, float hardness, float resistance, Material material, SoundType soundType) {
        return new RotatedPillarBlock(AbstractBlock.Properties.create(material, (state) -> state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).hardnessAndResistance(hardness, resistance).sound(soundType));
    }

    private static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
        return new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, (state) -> state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
    }

    private static ModLeavesBlock createLeavesBlock() {
        return new ModLeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid().setAllowsSpawn(ModBlocks::allowsSpawnOnLeaves).setSuffocates(ModBlocks::isntSolid).setBlocksVision(ModBlocks::isntSolid));
    }

    private static Boolean neverAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return (boolean)false;
    }

    private static Boolean alwaysAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return (boolean)true;
    }

    private static Boolean allowsSpawnOnLeaves(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    private static boolean needsPostProcessing(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }
}
