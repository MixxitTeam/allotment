package team.mixxit.allotment.data.client;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.EnchantingTableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.LawnBlock;
import team.mixxit.allotment.blocks.TintedDoublePlantBlock;
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

        /*
        ModelFile layeredTintedCross = models().getExistingFile(modLoc("block/layered_tinted_cross"));
        ModelFile tintedCross = models().getExistingFile(mcLoc("block/tinted_cross"));

        BlockModelBuilder pampasGrassUpperHalf = models().getBuilder("pampas_grass").parent(layeredTintedCross)
                .texture("base", "block/pampas_grass_top_yellow")
                .texture("cross", "block/pampas_grass_top");

        BlockModelBuilder pampasGrassLowerHalf = models().getBuilder("pampas_grass").parent(tintedCross)
                .texture("cross", "block/pampas_grass_bottom");

        VariantBlockStateBuilder variantBuilder = getVariantBuilder(ModBlocks.PAMPAS_GRASS.get());
        variantBuilder.partialState()
                .with(TintedDoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(ConfiguredModel.allRotations(pampasGrassLowerHalf, false))
                .with(TintedDoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(ConfiguredModel.allRotations(pampasGrassUpperHalf, false));
         */

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
