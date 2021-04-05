package team.mixxit.allotment.data.client;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.ModMushroomBlock;
import team.mixxit.allotment.blocks.RotatableBlock;
import team.mixxit.allotment.blocks.SmallCactusBlock;
import team.mixxit.allotment.setup.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, AllotmentMod.MOD_ID, exFileHelper);
    }

    private void plant(RegistryObject<? extends Block> block) {
        String name = block.getId().getPath();
        simpleBlock(block.get(), models().cross(name, modLoc("block/" + name)));
    }

    private void flowerPot(ModelFile base, Block block, String pot, String plant_texture_name) {
        simpleBlock(block, models().getBuilder(pot).parent(base).texture("plant", "block/" + plant_texture_name));
    }

    private void plank(RegistryObject<Block> block) {
        simpleBlock(block.get());
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
            plant(_flower);
        }
        for (RegistryObject<ModMushroomBlock> _mushroom : ModBlocks._COLLECTION_MUSHROOMS) {
            plant(_mushroom);
        }
        for (RegistryObject<SmallCactusBlock> _cactus : ModBlocks._COLLECTION_SMALL_CACTI) {
            plant(_cactus);
        }
        for (RegistryObject<Block> _plank : ModBlocks._COLLECTION_PLANKS) {
            plank(_plank);
        }

        ModelFile flowerPotCross = models().getExistingFile(mcLoc("block/flower_pot_cross"));

        for (FlowerPotBlock _potted : ModBlocks._COLLECTION_POTTED_PLANTS) {
            String plantName = _potted.getFlower().getRegistryName().getPath();
            if (plantName.equals("air"))
                throw new RuntimeException("FOUND AIR AS FLOWER");
            flowerPot(flowerPotCross, _potted, _potted.getRegistryName().getPath(), plantName);
        }

        for (RegistryObject<TrapDoorBlock> _trapdoor : ModBlocks._COLLECTION_TRAPDOORS) {
            //trapdoorBlock(_trapdoor.get(), modLoc("block/" + _trapdoor.getId().getPath()), false);
            String baseName = _trapdoor.getId().getPath();
            ResourceLocation texture = modLoc("block/" + _trapdoor.getId().getPath());

            ModelFile bottom = models().trapdoorBottom(baseName + "_bottom", texture);
            ModelFile top = models().trapdoorTop(baseName + "_top", texture);
            ModelFile open = models().trapdoorOpen(baseName + "_open", texture);
            trapdoorBlock(_trapdoor.get(), bottom, top, open, false);
        }

        rotatable(ModBlocks.ZEN_GRAVEL_NORMAL, zenGravelModel("zen_gravel", modLoc("block/zen_gravel")));
        rotatable(ModBlocks.ZEN_GRAVEL_STRAIGHT, zenGravelModel("zen_gravel_straight", modLoc("block/zen_gravel_straight")));
        rotatable(ModBlocks.ZEN_GRAVEL_CORNER, zenGravelModel("zen_gravel_corner", modLoc("block/zen_gravel_corner")), 180);
        rotatable(ModBlocks.ZEN_GRAVEL_END, zenGravelModel("zen_gravel_end", modLoc("block/zen_gravel_end")), 90);

        logBlock(ModBlocks.ELDER_LOG.get());
        simpleBlock(ModBlocks.ELDER_PLANKS.get());
        leaves(ModBlocks.ELDER_LEAVES.get());

        axisBlock(ModBlocks.BAMBOO_BLOCK.get(), modLoc("block/bamboo_block"));
        axisBlock(ModBlocks.DRIED_BAMBOO_BLOCK.get(), modLoc("block/dried_bamboo_block"));
    }

    private void leaves(Block block) {
        ModelFile base = models().getExistingFile(mcLoc("block/leaves"));
        BlockModelBuilder builder = models().getBuilder(block.getRegistryName().getPath()).parent(base)
                .texture("all", modLoc("block/" + block.getRegistryName().getPath()));
        simpleBlock(block, builder);
    }

    private BlockModelBuilder zenGravelModel(String id, ResourceLocation topTexture) {
        ModelFile orientable = models().getExistingFile(mcLoc("block/orientable_with_bottom"));
        return models().getBuilder(id).parent(orientable)
                .texture("front", modLoc("block/zen_gravel"))
                .texture("side", modLoc("block/zen_gravel"))
                .texture("bottom", modLoc("block/zen_gravel"))
                .texture("top", topTexture);
    }

    private VariantBlockStateBuilder rotatable(RegistryObject<Block> block, ModelFile model) {
        return rotatable(block, model, 0);
    }

    private VariantBlockStateBuilder rotatable(RegistryObject<Block> block, ModelFile model, int baseRotation) {
        VariantBlockStateBuilder builder = getVariantBuilder(block.get());
        builder.partialState()
                .with(RotatableBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(model).rotationY((360 + baseRotation) % 360).addModel();
        builder.partialState()
                .with(RotatableBlock.FACING, Direction.EAST)
                .modelForState().modelFile(model).rotationY((450 + baseRotation) % 360).addModel();
        builder.partialState()
                .with(RotatableBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(model).rotationY((540 + baseRotation) % 360).addModel();
        builder.partialState()
                .with(RotatableBlock.FACING, Direction.WEST)
                .modelForState().modelFile(model).rotationY((630 + baseRotation) % 360).addModel();
        return builder;
    }
}
