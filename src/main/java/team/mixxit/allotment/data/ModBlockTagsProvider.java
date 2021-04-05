package team.mixxit.allotment.data;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.SmallCactusBlock;
import team.mixxit.allotment.setup.ModBlocks;
import team.mixxit.allotment.setup.ModTags;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, AllotmentMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        // TODO Forge tags

        Builder<Block> smallCactiBuilder = getOrCreateBuilder(ModTags.Blocks.SMALL_CACTI);
        for (RegistryObject<SmallCactusBlock> _cactus : ModBlocks._COLLECTION_SMALL_CACTI) {
            smallCactiBuilder.add(_cactus.get());
        }

        getOrCreateBuilder(ModTags.Blocks.CAN_SUSTAIN_SMALL_CACTI)
                .add(Blocks.SAND)
                .add(Blocks.RED_SAND);

        Builder<Block> flowersBuilder = getOrCreateBuilder(BlockTags.FLOWERS);
        Builder<Block> smallFlowersBuilder = getOrCreateBuilder(BlockTags.SMALL_FLOWERS);
        for (RegistryObject<FlowerBlock> _flower : ModBlocks._COLLECTION_FLOWERS) {
            flowersBuilder.add(_flower.get());
            smallFlowersBuilder.add(_flower.get());
        }

        Builder<Block> flowerPotBuilder = getOrCreateBuilder(BlockTags.FLOWER_POTS);
        for (FlowerPotBlock _flowerPot : ModBlocks._COLLECTION_POTTED_PLANTS) {
            flowerPotBuilder.add(_flowerPot);
        }

        Builder<Block> planksBuilder = getOrCreateBuilder(BlockTags.PLANKS)
                .add(ModBlocks.ELDER_PLANKS.get());
        for (RegistryObject<Block> _plank : ModBlocks._COLLECTION_PLANKS) {
            planksBuilder.add(_plank.get());
        }

        getOrCreateBuilder(BlockTags.LEAVES)
            .add(ModBlocks.ELDER_LEAVES.get());

        getOrCreateBuilder(BlockTags.LOGS)
                .add(ModBlocks.ELDER_LOG.get());

        getOrCreateBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ELDER_LOG.get());
    }
}