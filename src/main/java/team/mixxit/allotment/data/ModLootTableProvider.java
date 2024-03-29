package team.mixxit.allotment.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.blocks.*;
import team.mixxit.allotment.setup.ModBlocks;
import team.mixxit.allotment.setup.ModItems;
import team.mixxit.allotment.setup.Registration;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((p1, p2) -> LootTableManager.validateLootTable(validationtracker, p1, p2));
    }

    public static class ModBlockLootTables extends BlockLootTables {
        private static final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
        private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

        @Override
        protected void addTables() {
            for (RegistryObject<ModFlowerBlock> _flower : ModBlocks._COLLECTION_FLOWERS)
                registerDropSelfLootTable(_flower.get());

            for (RegistryObject<ModMushroomBlock> _mushroom : ModBlocks._COLLECTION_MUSHROOMS)
                registerDropSelfLootTable(_mushroom.get());

            for (RegistryObject<SmallCactusBlock> _cactus : ModBlocks._COLLECTION_SMALL_CACTI)
                registerDropSelfLootTable(_cactus.get());

            for (RegistryObject<? extends TallFlowerBlock> _tallFlower : ModBlocks._COLLECTION_TALL_FLOWERS)
                registerLootTable(_tallFlower.get(), (_b) -> droppingWhen(_b, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

            for (FlowerPotBlock _flowerPot : ModBlocks._COLLECTION_POTTED_PLANTS)
                registerFlowerPot(_flowerPot);

            for (RegistryObject<ModFenceBlock> _fence : ModBlocks._COLLECTION_FENCES)
                registerDropSelfLootTable(_fence.get());

            for (RegistryObject<ModFenceGateBlock> _fenceGate : ModBlocks._COLLECTION_FENCEGATES)
                registerDropSelfLootTable(_fenceGate.get());

            for (RegistryObject<MadeFromBlock> _plank : ModBlocks._COLLECTION_PLANKS)
                registerDropSelfLootTable(_plank.get());

            for (RegistryObject<ModStairsBlock> _stair : ModBlocks._COLLECTION_STAIRS)
                registerDropSelfLootTable(_stair.get());

            for (RegistryObject<ModSlabBlock> _slab : ModBlocks._COLLECTION_SLABS)
                registerLootTable(_slab.get(), BlockLootTables::droppingSlab);

            for (RegistryObject<ThinFenceBlock> _thinFence : ModBlocks._COLLECTION_THIN_FENCES)
                registerDropSelfLootTable(_thinFence.get());

            for (RegistryObject<TrapDoorBlock> _trapdoor : ModBlocks._COLLECTION_TRAPDOORS)
                registerDropSelfLootTable(_trapdoor.get());

            for (RegistryObject<TallWallBlock> _wall : ModBlocks._COLLECTION_TALL_WALLS)
                registerDropSelfLootTable(_wall.get());

            for (RegistryObject<ModVineBlock> _vine : ModBlocks._COLLECTION_VINES) {
                ModVineBlock _block = _vine.get();
                registerLootTable(_block, droppingSheared(_block));
            }

            for (RegistryObject<ModVineBlock> _vine : ModBlocks._COLLECTION_TINTED_OVERLAY_VINES) {
                ModVineBlock _block = _vine.get();
                registerLootTable(_block, droppingSheared(_block));
            }

            registerDropSelfLootTable(ModBlocks.BAMBOO_BLOCK.get());
            registerDropSelfLootTable(ModBlocks.DRIED_BAMBOO_BLOCK.get());
            registerDropSelfLootTable(ModBlocks.STRAW_BLOCK.get());
            registerDropSelfLootTable(ModBlocks.CRACKED_CLAY.get());
            registerDropSelfLootTable(ModBlocks.ELDER_LOG.get());
            registerDropSelfLootTable(ModBlocks.ELDER_WOOD.get());
            registerDropSelfLootTable(ModBlocks.ELDER_PLANKS.get());
            registerDropSelfLootTable(ModBlocks.HOSE_REEL.get());
            registerDropSelfLootTable(ModBlocks.HUMUS.get());
            registerDropSelfLootTable(ModBlocks.MULCH.get());
            registerDropSelfLootTable(ModBlocks.SPANISH_MOSS.get());
            registerDropSelfLootTable(ModBlocks.FERRALSOL.get());
            registerDropSelfLootTable(ModBlocks.STRIPPED_ELDER_LOG.get());
            registerDropSelfLootTable(ModBlocks.STRIPPED_ELDER_WOOD.get());
            registerDropSelfLootTable(ModBlocks.TERRA_PRETA.get());
            registerDropSelfLootTable(ModBlocks.TURF.get());
            registerDropSelfLootTable(ModBlocks.ZEN_GRAVEL_NORMAL.get());
            registerDropSelfLootTable(ModBlocks.ZEN_GRAVEL_STRAIGHT.get());
            registerDropSelfLootTable(ModBlocks.ZEN_GRAVEL_END.get());
            registerDropSelfLootTable(ModBlocks.ZEN_GRAVEL_CORNER.get());
            registerDropSelfLootTable(ModBlocks.FIREWOOD_SPRUCE.get());
            registerDropSelfLootTable(ModBlocks.PINCUSSION_MOSS.get());
            registerDropSelfLootTable(ModBlocks.CORRUGATED_IRON.get());
            registerDropSelfLootTable(ModBlocks.GUTTER.get());
            registerDropSelfLootTable(ModBlocks.ELDER_BUTTON.get());
            registerDropSelfLootTable(ModBlocks.TEST_FLOWER.get());
            registerDropSelfLootTable(ModBlocks.ELDER_SAPLING.get());

            registerLootTable(ModBlocks.PAMPAS_GRASS.get(), droppingSheared(ModBlocks.PAMPAS_GRASS.get()));
            registerLootTable(ModBlocks.PAMPAS_GRASS_PINK.get(), droppingSheared(ModBlocks.PAMPAS_GRASS_PINK.get()));

            registerLootTable(ModBlocks.LAWN_BLOCK.get(), (_b) -> droppingWithSilkTouch(_b, Blocks.DIRT));

            registerLootTable(ModBlocks.ELDER_LEAVES.get(), (leaves) -> droppingWithChancesAndSticks(leaves, ModBlocks.ELDER_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES));
            registerLootTable(ModBlocks.ELDER_LEAVES_FLOWERING.get(), (leaves) -> droppingWithChancesAndSticks(leaves, ModBlocks.ELDER_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES));

            registerNoDropLootTable(ModBlocks.ALLOTMENT_LOGO_1.get());
            registerNoDropLootTable(ModBlocks.ALLOTMENT_LOGO_2.get());
            registerNoDropLootTable(ModBlocks.DEBUG_BLOCK.get());
            registerNoDropLootTable(ModBlocks.DEBUG_TINT_BLOCK.get());
            registerNoDropLootTable(ModBlocks.DEBUG_FOLIAGE_BLOCK.get());

            registerLootTable(ModBlocks.ELDER_DOOR.get(), (_b) -> droppingWhenWithItem(_b, ModItems.ELDER_DOOR.get(), DoorBlock.HALF, DoubleBlockHalf.LOWER));
        }

        public void registerNoDropLootTable(Block block) {
            registerLootTable(block, blockNoDrop());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return Registration.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }

        protected static LootTable.Builder droppingSheared(Block sheared) {
            return droppingSheared(sheared, 1);
        }

        protected static LootTable.Builder droppingSheared(Block sheared, int count) {
            return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SHEARS).addEntry(ItemLootEntry.builder(sheared).acceptFunction(SetCount.builder(ConstantRange.of(count)))));
        }

        protected static <T extends Comparable<T> & IStringSerializable> LootTable.Builder droppingWhenWithItem(Block block, IItemProvider itemToDrop, Property<T> property, T value) {
            return LootTable
                    .builder()
                    .addLootPool(
                            withSurvivesExplosion(
                                    block,
                                    LootPool
                                            .builder()
                                            .rolls(
                                                    ConstantRange
                                                            .of(1)
                                            )
                                            .addEntry(
                                                    ItemLootEntry
                                                            .builder(itemToDrop)
                                                            .acceptCondition(
                                                                    BlockStateProperty
                                                                            .builder(block)
                                                                            .fromProperties(
                                                                                    StatePropertiesPredicate
                                                                                            .Builder
                                                                                            .newBuilder()
                                                                                            .withProp(
                                                                                                    property,
                                                                                                    value
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )
                    );
        }
    }
}
