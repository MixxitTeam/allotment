package team.mixxit.allotment.data;

import net.minecraft.block.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.*;
import team.mixxit.allotment.setup.ModBlocks;
import team.mixxit.allotment.setup.ModTags;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, AllotmentMod.MOD_ID, existingFileHelper);
    }

    public Builder<Block> publicGetOrCreateBuilder(ITag.INamedTag<Block> tag) {
        return getOrCreateBuilder(tag);
    }

    @Override
    protected void registerTags() {
        // TODO Forge tags (or not, I didn't find any commonly used, relevant forge tag yet)
        Builder<Block> smallCactiBuilder = getOrCreateBuilder(ModTags.Blocks.SMALL_CACTI);
        Builder<Block> canSustainSmallCactiBuilder = getOrCreateBuilder(ModTags.Blocks.CAN_SUSTAIN_SMALL_CACTI);
        Builder<Block> flowersBuilder = getOrCreateBuilder(BlockTags.FLOWERS);
        Builder<Block> smallFlowersBuilder = getOrCreateBuilder(BlockTags.SMALL_FLOWERS);
        Builder<Block> flowerPotBuilder = getOrCreateBuilder(BlockTags.FLOWER_POTS);
        Builder<Block> planksBuilder = getOrCreateBuilder(BlockTags.PLANKS);
        Builder<Block> fenceBuilder = getOrCreateBuilder(BlockTags.FENCES);
        Builder<Block> woodenFenceBuilder = getOrCreateBuilder(BlockTags.WOODEN_FENCES);
        Builder<Block> wallBuilder = getOrCreateBuilder(BlockTags.WALLS);

        for (RegistryObject<SmallCactusBlock> _cactus : ModBlocks._COLLECTION_SMALL_CACTI) {
            smallCactiBuilder.add(_cactus.get());
        }

        canSustainSmallCactiBuilder
                .add(Blocks.SAND)
                .add(Blocks.RED_SAND);

        for (RegistryObject<ModFlowerBlock> _flower : ModBlocks._COLLECTION_FLOWERS) {
            flowersBuilder.add(_flower.get());
            smallFlowersBuilder.add(_flower.get());
        }

        for (FlowerPotBlock _flowerPot : ModBlocks._COLLECTION_POTTED_PLANTS) {
            flowerPotBuilder.add(_flowerPot);
        }

        planksBuilder.add(ModBlocks.ELDER_PLANKS.get());
        for (RegistryObject<Block> _plank : ModBlocks._COLLECTION_PLANKS) {
            planksBuilder.add(_plank.get());
        }

        getOrCreateBuilder(BlockTags.LEAVES)
            .add(ModBlocks.ELDER_LEAVES.get());

        getOrCreateBuilder(BlockTags.LOGS)
                .add(ModBlocks.ELDER_LOG.get());

        getOrCreateBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ELDER_LOG.get());

        for (RegistryObject<ThinFenceBlock> _thinFence : ModBlocks._COLLECTION_THIN_FENCES) {
            fenceBuilder.add(_thinFence.get());
            woodenFenceBuilder.add(_thinFence.get());
        }

        for (RegistryObject<ModFenceBlock> _fence : ModBlocks._COLLECTION_FENCES) {
            fenceBuilder.add(_fence.get());
            woodenFenceBuilder.add(_fence.get());
        }

        for (RegistryObject<TallWallBlock> _wall : ModBlocks._COLLECTION_TALL_WALLS) {
            wallBuilder.add(_wall.get());
        }
    }
}
