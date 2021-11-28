package team.mixxit.allotment.data;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.ModFlowerBlock;
import team.mixxit.allotment.setup.ModBlocks;
import team.mixxit.allotment.setup.ModTags;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, AllotmentMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        Builder<Item> flowersBuilder = getOrCreateBuilder(ItemTags.FLOWERS);
        Builder<Item> smallFlowersBuilder = getOrCreateBuilder(ItemTags.SMALL_FLOWERS);
        for (RegistryObject<ModFlowerBlock> _flower : ModBlocks._COLLECTION_FLOWERS) {
            flowersBuilder.add(_flower.get().asItem());
            smallFlowersBuilder.add(_flower.get().asItem());
        }

        copy(BlockTags.LOGS, ItemTags.LOGS);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.DOORS, ItemTags.DOORS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
    }
}
