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
            new LawnBlock(AbstractBlock.Properties.create(Material.EARTH).hardnessAndResistance(0.65F).harvestTool(ToolType.SHOVEL).sound(SoundType.PLANT)));

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

    public static final RegistryObject<RotatedPillarBlock> ELDER_LOG = register("elder_log", () ->
            createLogBlock(MaterialColor.SAND, MaterialColor.LIGHT_GRAY));

    public static final RegistryObject<RotatedPillarBlock> STRIPPED_ELDER_LOG = register("stripped_elder_log", () ->
            createLogBlock(MaterialColor.SAND, MaterialColor.SAND));

    public static final RegistryObject<RotatedPillarBlock> ELDER_WOOD = register("elder_wood", () ->
            createLogBlock(MaterialColor.LIGHT_GRAY, MaterialColor.LIGHT_GRAY));

    public static final RegistryObject<RotatedPillarBlock> STRIPPED_ELDER_WOOD = register("stripped_elder_wood", () ->
            createLogBlock(MaterialColor.SAND, MaterialColor.SAND));

    public static final RegistryObject<Block> ELDER_PLANKS = register("elder_planks", () ->
            new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> ELDER_LEAVES = register("elder_leaves", () ->
            createLeavesBlock());

    public static final RegistryObject<Block> FIREWOOD_SPRUCE = register("spruce_firewood_bundle", () -> new FirewoodBundleBlock());
    public static final RegistryObject<Block> HOSE_REEL = register("hose_reel", () -> new HoseReelBlock());

    public static final RegistryObject<RotatedPillarBlock> BAMBOO_BLOCK = register("bamboo_block",
            () -> createRotatableBlock(AbstractBlock.Properties.create(Material.BAMBOO, (state) -> MaterialColor.GREEN).hardnessAndResistance(1.0F).harvestTool(ToolType.AXE).sound(SoundType.BAMBOO)));

    public static final RegistryObject<RotatedPillarBlock> DRIED_BAMBOO_BLOCK = register("dried_bamboo_block",
            () -> createRotatableBlock(AbstractBlock.Properties.create(Material.EARTH, (state) -> MaterialColor.SAND).harvestTool(ToolType.HOE).hardnessAndResistance(0.8F).sound(SoundType.PLANT)));

    public static final RegistryObject<HayBlock> STRAW_BLOCK = register("straw", () ->
            new HayBlock(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F).harvestTool(ToolType.HOE).sound(SoundType.PLANT)));

    public static final RegistryObject<Block> CRACKED_CLAY = register("cracked_clay", () ->
            new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN).hardnessAndResistance(0.9F, 2.0F).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE)));

    public static final RegistryObject<SoilBlock> HUMUS = register("humus", () ->
            new SoilBlock(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.BROWN).hardnessAndResistance(0.5F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)));

    public static final RegistryObject<SoilBlock> TURF = register("turf", () ->
            new SoilBlock(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.9F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)));

    public static final RegistryObject<SoilBlock> FERRALSOL = register("ferralsol", () ->
            new SoilBlock(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.ADOBE).hardnessAndResistance(0.6F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)));

    public static final RegistryObject<SoilBlock> MULCH = register("mulch", () ->
            new SoilBlock(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.BROWN).hardnessAndResistance(0.3F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)));

    public static final RegistryObject<SoilBlock> TERRA_PRETA = register("terra_preta", () ->
            new SoilBlock(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.BLACK).hardnessAndResistance(0.45F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)));

    public static final RegistryObject<TransparentBlock> SPANISH_MOSS = register("spanish_moss", () ->
            new TransparentBlock(AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.CYAN).hardnessAndResistance(0.2F).harvestTool(ToolType.HOE).sound(SoundType.PLANT).notSolid().setAllowsSpawn(ModBlocks::allowsSpawnOnLeaves).setSuffocates(ModBlocks::isntSolid).setBlocksVision(ModBlocks::isntSolid)));

    //public static RegistryObject<StairsBlock> ELDER_STAIRS;
    //public static RegistryObject<SlabBlock> ELDER_SLAB;

    //public static final RegistryObject<FlowerBlock> TEST_PLANT = flower("test_plant");

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

    public static final RegistryObject<? extends TallFlowerBlock>[] _COLLECTION_TALL_FLOWERS = new RegistryObject[]{
            doublePlant("crown_imperial"),
            doublePlant("orange_gladioli"),
            doublePlant("purple_fountain_grass"),
            doublePlant("purple_loosestrife"),
            doublePlant("reed"),
            doublePlant("tall_desert_rose"),
            tallPhistle("tall_phistle")
    };

    public static final RegistryObject<ThinFenceBlock>[] _COLLECTION_THIN_FENCES = new RegistryObject[]{
            thinFence("chain_link_fence", AbstractBlock.Properties.create(Material.IRON, MaterialColor.EMERALD).harvestTool(ToolType.PICKAXE).sound(SoundType.CHAIN).hardnessAndResistance(2.5F, 4.0F)),
            thinFence("jaktop_criss_cross_fence", AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F))
    };

    public static final RegistryObject<ModFenceBlock>[] _COLLECTION_FENCES = new RegistryObject[]{
            modFence("elder_fence", "elder_planks", AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)),
            modFence("dried_bamboo_fence", "dried_bamboo_block_side", AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)),
            modFence("bamboo_fence", "bamboo_block_side", AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GREEN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.BAMBOO))
    };

    public static final RegistryObject<ModFenceGateBlock>[] _COLLECTION_FENCEGATES = new RegistryObject[]{
            modFenceGate("elder_fence_gate", "elder_planks", AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)),
            modFenceGate("dried_bamboo_fence_gate", "dried_bamboo_block_side", AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)),
            modFenceGate("bamboo_fence_gate", "bamboo_block_side", AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GREEN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.BAMBOO))
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

    public static final ArrayList<FlowerPotBlock> _COLLECTION_POTTED_PLANTS = new ArrayList<>();
    public static final ArrayList<RegistryObject<Block>> _COLLECTION_PLANKS = new ArrayList<>();
    public static final ArrayList<RegistryObject<ModStairsBlock>> _COLLECTION_STAIRS = new ArrayList<>();
    public static final ArrayList<RegistryObject<ModSlabBlock>> _COLLECTION_SLABS = new ArrayList<>();
    public static final ArrayList<RegistryObject<ModStandingSignBlock>> _COLLECTION_STANDING_SIGNS = new ArrayList<>();
    public static final ArrayList<RegistryObject<ModWallSignBlock>> _COLLECTION_WALL_SIGNS = new ArrayList<>();

    public static final int SIGN_ELDER = 0;

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

        _COLLECTION_STAIRS.add(register("elder_stairs", () ->
                new ModStairsBlock(() -> ELDER_PLANKS.get().getDefaultState(), AbstractBlock.Properties.from(ELDER_PLANKS.get()),
        "elder_planks")));
        _COLLECTION_STAIRS.add(register("bamboo_stairs", () ->
                new ModStairsBlock(() -> BAMBOO_BLOCK.get().getDefaultState(), AbstractBlock.Properties.from(BAMBOO_BLOCK.get()),
        "bamboo_block", "bamboo_block_side")));
        _COLLECTION_STAIRS.add(register("dried_bamboo_stairs", () ->
                new ModStairsBlock(() -> DRIED_BAMBOO_BLOCK.get().getDefaultState(), AbstractBlock.Properties.from(DRIED_BAMBOO_BLOCK.get()),
                        "dried_bamboo_block", "dried_bamboo_block_side")));

        _COLLECTION_SLABS.add(register("elder_slab", () ->
                new ModSlabBlock(AbstractBlock.Properties.from(ELDER_PLANKS.get()),
        "elder_planks")));
        _COLLECTION_SLABS.add(register("bamboo_slab", () ->
                new ModSlabBlock(AbstractBlock.Properties.create(Material.BAMBOO, (state) -> MaterialColor.GREEN).hardnessAndResistance(1.0F).sound(SoundType.BAMBOO),
        "bamboo_block", "bamboo_block_end", "bamboo_block_side")));
        _COLLECTION_SLABS.add(register("dried_bamboo_slab", () ->
                new ModSlabBlock(AbstractBlock.Properties.create(Material.EARTH, (state) -> MaterialColor.SAND).hardnessAndResistance(0.8F).sound(SoundType.PLANT),
        "dried_bamboo_block", "dried_bamboo_block_end", "dried_bamboo_block_side")));

        for (ModWoodType _woodType : ModWoodType.VALUES) {
            // FIXME
            //modSign(_woodType);
        }
    }

    static void postRegister() {
        for (RegistryObject<FlowerBlock> _flower : _COLLECTION_FLOWERS) {
            registerCompostable(0.65F, _flower);
        }
        for (RegistryObject<ModMushroomBlock> _mushroom : _COLLECTION_MUSHROOMS) {
            registerCompostable(0.65F, _mushroom);
        }
        for (RegistryObject<? extends TallFlowerBlock> _tallflower : _COLLECTION_TALL_FLOWERS) {
            registerCompostable(0.65F, _tallflower);
        }
        registerCompostable(0.65F, PAMPAS_GRASS);
        registerCompostable(0.65F, PAMPAS_GRASS_PINK);
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
        for (RegistryObject<? extends TallFlowerBlock> _tallflower : _COLLECTION_TALL_FLOWERS) {
            RenderTypeLookup.setRenderLayer(_tallflower.get(), RenderType.getCutout());
        }
        for (RegistryObject<ThinFenceBlock> _thinFence : _COLLECTION_THIN_FENCES) {
            RenderTypeLookup.setRenderLayer(_thinFence.get(), RenderType.getCutout());
        }
        RenderTypeLookup.setRenderLayer(LAWN_BLOCK.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(PAMPAS_GRASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(PAMPAS_GRASS_PINK.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ALLOTMENT_LOGO_1.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ALLOTMENT_LOGO_2.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ELDER_LEAVES.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(SPANISH_MOSS.get(), RenderType.getCutout());

        //RenderTypeLookup.setRenderLayer(TEST_PLANT.get(), RenderType.getCutout());
    }

    private static void modSign(ModWoodType woodTypeIn) {
        RegistryObject<ModStandingSignBlock> standingSign = register(woodTypeIn.getName() + "_sign", () ->
                new ModStandingSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), woodTypeIn));

        RegistryObject<ModWallSignBlock> wallSign = registerNoItem(woodTypeIn.getName() + "_wall_sign", () ->
                new ModWallSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), woodTypeIn));

        _COLLECTION_STANDING_SIGNS.add(standingSign);
        _COLLECTION_WALL_SIGNS.add(wallSign);
    }

    private static RegistryObject<ModFenceGateBlock> modFenceGate(String name, String forBlock, AbstractBlock.Properties properties) {
        return register(name, () -> new ModFenceGateBlock(properties, forBlock));
    }

    private static RegistryObject<ModFenceBlock> modFence(String name, String forBlock, AbstractBlock.Properties properties) {
        return register(name, () -> new ModFenceBlock(properties, forBlock));
    }

    private static RegistryObject<FenceBlock> fence(String name, AbstractBlock.Properties properties) {
        return register(name, () -> new FenceBlock(properties));
    }

    private static RegistryObject<ThinFenceBlock> thinFence(String name, AbstractBlock.Properties properties) {
        return register(name, () -> new ThinFenceBlock(properties));
    }

    private static RegistryObject<TrapDoorBlock> trapdoor(String name, MaterialColor color, SoundType sound) {
        return register(name, () -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, color).hardnessAndResistance(3.0F).harvestTool(ToolType.AXE).sound(sound).notSolid().setAllowsSpawn(ModBlocks::neverAllowSpawn)));
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

    private static RegistryObject<TallFlowerBlock> tallPhistle(String name) {
        return register(name, () -> new TallPhistleBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    }

    private static RegistryObject<TallFlowerBlock> doublePlant(String name) {
        return register(name, () -> new TallFlowerBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    }

    private static RotatedPillarBlock createRotatableBlock(AbstractBlock.Properties properties) {
        return new RotatedPillarBlock(properties);
    }

    private static RotatedPillarBlock createRotatableBlock(MaterialColor topColor, MaterialColor barkColor, float hardness, float resistance, Material material, SoundType soundType) {
        return new RotatedPillarBlock(AbstractBlock.Properties.create(material, (state) -> state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).hardnessAndResistance(hardness, resistance).sound(soundType));
    }

    private static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
        return new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, (state) -> state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).harvestTool(ToolType.AXE).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
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
