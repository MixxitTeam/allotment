package team.mixxit.allotment.data.client;

import net.minecraft.block.FlowerBlock;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.setup.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, AllotmentMod.MOD_ID, exFileHelper);
    }

    private void flower(RegistryObject<FlowerBlock> block, String name){
        simpleBlock(block.get(), models().cross(name, modLoc("block/" + name)));
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.TEST_BLOCK.get());
        flower(ModBlocks.FLOWER_FORGET_ME_NOT, "forget_me_not");
        flower(ModBlocks.FLOWER_SMALL_PEONY_CORAL, "coral_small_peony");
        flower(ModBlocks.FLOWER_SMALL_PEONY, "small_peony");
        flower(ModBlocks.FLOWER_HYACINTH_BLUE, "blue_hyacinth");
        flower(ModBlocks.FLOWER_HYACINTH_CREAM, "cream_hyacinth");
        flower(ModBlocks.FLOWER_HYACINTH_ORANGE, "orange_hyacinth");
        flower(ModBlocks.FLOWER_HYACINTH_RED, "red_hyacinth");
        flower(ModBlocks.FLOWER_HYACINTH_VIOLET, "violet_hyacinth");
        flower(ModBlocks.FLOWER_HYACINTH_WHITE, "white_hyacinth");
        flower(ModBlocks.FLOWER_HYACINTH_YELLOW, "yellow_hyacinth");
    }
}
