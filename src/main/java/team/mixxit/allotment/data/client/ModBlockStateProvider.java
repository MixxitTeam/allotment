package team.mixxit.allotment.data.client;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.setup.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, AllotmentMod.MOD_ID, exFileHelper);
    }

    private void flower(RegistryObject<FlowerBlock> block, String name) {
        simpleBlock(block.get(), models().cross(name, modLoc("block/" + name)));
    }

    private void flowerPot(ModelFile base, Block block, String pot, String plant_texture_name) {
        simpleBlock(block, models().getBuilder(pot).parent(base).texture("plant", "block/" + plant_texture_name));
    }

    @Override
    protected void registerStatesAndModels() {
        //simpleBlock(ModBlocks.TEST_BLOCK.get());

        for (RegistryObject<FlowerBlock> _flower : ModBlocks._COLLECTION_FLOWERS) {
            flower(_flower, _flower.getId().getPath());
        }

        ModelFile flowerPotCross = models().getExistingFile(mcLoc("block/flower_pot_cross"));

        for (FlowerPotBlock _potted : ModBlocks._COLLECTION_POTTED_FLOWERS) {
            String plantName = _potted.getFlower().getRegistryName().getPath();
            if (plantName.equals("air"))
                throw new RuntimeException("FOUND AIR AS FLOWER");
            flowerPot(flowerPotCross, _potted, _potted.getRegistryName().getPath(), plantName);
        }
    }
}
