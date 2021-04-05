package team.mixxit.allotment.setup;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import team.mixxit.allotment.AllotmentMod;

import java.util.List;

public class ModTags {
    public static final class Blocks {
        public static final ITag.INamedTag<Block> CAN_SUSTAIN_SMALL_CACTI = mod("can_sustain_small_cacti");
        public static final ITag.INamedTag<Block> SMALL_CACTI = mod("small_cacti");

        private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.makeWrapperTag(new ResourceLocation(AllotmentMod.MOD_ID, path).toString());
        }
    }
    public static final class Items {
        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.makeWrapperTag(new ResourceLocation(AllotmentMod.MOD_ID, path).toString());
        }
    }
}
