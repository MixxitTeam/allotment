package team.mixxit.allotment.setup;

import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.DarkOakFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.ModFlowerBlock;
import team.mixxit.allotment.blocks.SmallCactusBlock;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = AllotmentMod.MOD_ID)
public class ModFeatures {
    private static boolean configuredFeaturesRegistered = false;
    private static final Map<ResourceLocation, Lazy<ConfiguredFeature<? extends IFeatureConfig, ?>>> CONFIGURED_FEATURES = new LinkedHashMap<>();

    public static final Lazy<ConfiguredFeature<? extends IFeatureConfig, ?>> ELDER_TREE = register("elder_tree", () ->
            Feature.TREE.withConfiguration(
                    (new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(ModBlocks.ELDER_LOG.get().getDefaultState()),
                            new SimpleBlockStateProvider(ModBlocks.ELDER_LEAVES.get().getDefaultState()),
                            //                                            Radius                         | Offset       | Height
                            new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                            //new DarkOakFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1)),
                            //new BushFoliagePlacer(FeatureSpread.func_242252_a(3), FeatureSpread.func_242252_a(0), 3),
                            //new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 4),

                            //                      Base height | Height Rand A | Height Rand B
                            new StraightTrunkPlacer(4, 2, 0),


                            new TwoLayerFeature(1, 0, 1))
                    )
                            .setIgnoreVines()
                            .build()
            )
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                    .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.1f, 1)))
    );

    public static final Lazy<ConfiguredFeature<? extends IFeatureConfig, ?>> OVERWORLD_FLOWERS = register("overworld_flowers", () ->
            Feature.FLOWER
                    .withConfiguration(Configs.NORMAL_FLOWER_CONFIG)
                    .withPlacement(Features.Placements.VEGETATION_PLACEMENT)
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2)
    );

    public static final Lazy<ConfiguredFeature<? extends IFeatureConfig, ?>> DESERT_FLOWERS = register("desert_flowers", () ->
            Feature.FLOWER
                    .withConfiguration(Configs.DESERT_FLOWER_CONFIG)
                    .withPlacement(Features.Placements.VEGETATION_PLACEMENT)
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2)
    );

    public static final class States {
        protected static final BlockState ELDER_TREE_LOG = ModBlocks.ELDER_LOG.get().getDefaultState();
        protected static final BlockState ELDER_TREE_LEAVES = ModBlocks.ELDER_LEAVES.get().getDefaultState();
    }

    public static final class Placements {

    }

    private static final Logger LOGGER = LogManager.getLogger();

    public static final class Configs {
        public static final BlockClusterFeatureConfig NORMAL_FLOWER_CONFIG;
        public static final BlockClusterFeatureConfig DESERT_FLOWER_CONFIG;

        static {
            {
                WeightedBlockStateProvider normalFlowerWeightedBlockStateProvider = new WeightedBlockStateProvider();

                for (RegistryObject<ModFlowerBlock> _flower : ModBlocks._COLLECTION_FLOWERS)
                    normalFlowerWeightedBlockStateProvider.addWeightedBlockstate(_flower.get().getDefaultState(), 1);

                BlockClusterFeatureConfig normalFlowerConfig = (
                        new BlockClusterFeatureConfig.Builder(
                                normalFlowerWeightedBlockStateProvider,
                                SimpleBlockPlacer.PLACER)
                ).tries(64).build();

                NORMAL_FLOWER_CONFIG = normalFlowerConfig;
            }
            {
                WeightedBlockStateProvider desertFlowerWeightedBlockStateProvider = new WeightedBlockStateProvider();

                for (RegistryObject<SmallCactusBlock> _cactus : ModBlocks._COLLECTION_SMALL_CACTI)
                    desertFlowerWeightedBlockStateProvider.addWeightedBlockstate(_cactus.get().getDefaultState(), 1);

                BlockClusterFeatureConfig desertFlowerConfig = (
                        new BlockClusterFeatureConfig.Builder(
                                desertFlowerWeightedBlockStateProvider,
                                SimpleBlockPlacer.PLACER)
                ).tries(64).build();

                DESERT_FLOWER_CONFIG = desertFlowerConfig;
            }
        }
    }

    private static void registerConfiguredFeatures() {
        if (configuredFeaturesRegistered) return;

        configuredFeaturesRegistered = true;

        CONFIGURED_FEATURES.forEach((name, lazy) -> registerConfiguredFeature(name, lazy.get()));
    }

    private static void registerConfiguredFeature(ResourceLocation name, ConfiguredFeature<?, ?> configuredFeature) {
        LOGGER.debug("Register configured feature '{}'", name);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, name, configuredFeature);
    }

    @SubscribeEvent
    public static void registerFeatures(BiomeLoadingEvent event) {
        registerConfiguredFeatures();

        LOGGER.debug("RegisterFeatures called for biome " + event.getCategory().getName());

        // Normal flowers
        if (
                event.getCategory() == Biome.Category.PLAINS ||
                event.getCategory() == Biome.Category.FOREST ||
                event.getCategory() == Biome.Category.EXTREME_HILLS
        ) {
            LOGGER.debug("Biome is of expected type, registering features: OVERWORLD_FLOWERS");
            event.getGeneration()
                    .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.OVERWORLD_FLOWERS.get());
        }

        // Small cacti
        if (
                event.getCategory() == Biome.Category.DESERT
        ) {
            LOGGER.debug("Biome is of expected type, registering features: DESERT_FLOWERS");
            event.getGeneration()
                    .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.DESERT_FLOWERS.get());
        }

        // Elder Trees
        if (
                event.getCategory() == Biome.Category.FOREST ||
                event.getCategory() == Biome.Category.EXTREME_HILLS
        ) {
            LOGGER.debug("Biome is of expected type, registering features: ELDER_TREE");
            event.getGeneration()
                    .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.ELDER_TREE.get());
        }
    }

    public static void register(){}

    @Deprecated
    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String p_243968_0_, ConfiguredFeature<FC, ?> p_243968_1_) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, p_243968_0_, p_243968_1_);
    }

    private static Lazy<ConfiguredFeature<? extends IFeatureConfig, ?>> register(String name, Supplier<ConfiguredFeature<?, ?>> configuredFeature) {
        Lazy<ConfiguredFeature<? extends IFeatureConfig, ?>> lazy = Lazy.of(configuredFeature);
        CONFIGURED_FEATURES.put(AllotmentMod.getId(name), lazy);
        return lazy;
    }
}
