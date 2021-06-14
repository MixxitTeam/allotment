package team.mixxit.allotment.data.client;

import net.minecraft.block.*;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.data.BlockStateVariantBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.*;
import team.mixxit.allotment.setup.ModBlocks;

import javax.sound.midi.MidiChannel;

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
        ModelFile cross = models().getExistingFile(mcLoc("block/cross"));
        for (RegistryObject<? extends TallFlowerBlock> _tallflower : ModBlocks._COLLECTION_TALL_FLOWERS) {
            tallFlower(cross, _tallflower);
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

        for (RegistryObject<ModFenceBlock> _fence : ModBlocks._COLLECTION_FENCES) {
            fenceBlock(_fence.get(), modLoc("block/" + _fence.get().ForBlock));
        }

        for (RegistryObject<ModFenceGateBlock> _fencegate : ModBlocks._COLLECTION_FENCEGATES) {
            fenceGateBlock(_fencegate.get(), modLoc("block/" + _fencegate.get().ForBlock));
        }

        rotatable(ModBlocks.ZEN_GRAVEL_NORMAL, zenGravelModel("zen_gravel", modLoc("block/zen_gravel")));
        rotatable(ModBlocks.ZEN_GRAVEL_STRAIGHT, zenGravelModel("zen_gravel_straight", modLoc("block/zen_gravel_straight")));
        rotatable(ModBlocks.ZEN_GRAVEL_CORNER, zenGravelModel("zen_gravel_corner", modLoc("block/zen_gravel_corner")), 180);
        rotatable(ModBlocks.ZEN_GRAVEL_END, zenGravelModel("zen_gravel_end", modLoc("block/zen_gravel_end")), 90);

        logBlock(ModBlocks.ELDER_LOG.get());
        logBlock(ModBlocks.STRIPPED_ELDER_LOG.get());
        woodBlock(ModBlocks.ELDER_WOOD.get(), ModBlocks.ELDER_LOG.get());
        woodBlock(ModBlocks.STRIPPED_ELDER_WOOD.get(), ModBlocks.STRIPPED_ELDER_LOG.get());
        simpleBlock(ModBlocks.ELDER_PLANKS.get());
        floweringLeaves(ModBlocks.ELDER_LEAVES.get());

        axisBlock(ModBlocks.BAMBOO_BLOCK.get(), modLoc("block/bamboo_block"));
        axisBlock(ModBlocks.DRIED_BAMBOO_BLOCK.get(), modLoc("block/dried_bamboo_block"));

        simpleBlock(ModBlocks.STRAW_BLOCK.get());

        for (RegistryObject<ThinFenceBlock> _thinFence : ModBlocks._COLLECTION_THIN_FENCES) {
            ModelFile _post = models().withExistingParent(_thinFence.getId().getPath() + "_post", modLoc("block/thin_fence_post"))
                    .texture("all", modLoc("block/" + _thinFence.getId().getPath()));
            ModelFile _side = models().withExistingParent(_thinFence.getId().getPath() + "_side", modLoc("block/thin_fence_side"))
                    .texture("all", modLoc("block/" + _thinFence.getId().getPath()));
            fourWayBlock(_thinFence.get(), _post, _side);
        }

        simpleBlock(ModBlocks.CRACKED_CLAY.get());
        simpleBlock(ModBlocks.HUMUS.get());
        simpleBlock(ModBlocks.TURF.get());
        simpleBlock(ModBlocks.FERRALSOL.get());
        simpleBlock(ModBlocks.MULCH.get());
        simpleBlock(ModBlocks.TERRA_PRETA.get());
        simpleBlock(ModBlocks.PINCUSSION_MOSS.get());
        //simpleBlock(ModBlocks.CORRUGATED_IRON.get());
        tintedBlock(ModBlocks.DEBUG_TINT_BLOCK.get());

        rotatableBlock(ModBlocks.CORRUGATED_IRON.get());

        transparentBlock(ModBlocks.SPANISH_MOSS.get());

        for (RegistryObject<ModStairsBlock> _stair : ModBlocks._COLLECTION_STAIRS) {
            stairsBlock(_stair.get(), modLoc("block/" + _stair.get().WithTexture));
        }

        for (RegistryObject<ModSlabBlock> _slab : ModBlocks._COLLECTION_SLABS) {
            ModSlabBlock modSlabBlock = _slab.get();
            ResourceLocation model = modLoc("block/" + modSlabBlock.ForBlock);
            ResourceLocation textureTop = modLoc("block/" + modSlabBlock.WithTextureTop);
            ResourceLocation textureBottom = modLoc("block/" + modSlabBlock.WithTextureBottom);
            ResourceLocation textureSides = modLoc("block/" + modSlabBlock.WithTextureSides);
            slabBlock(modSlabBlock, model, textureSides, textureBottom, textureTop);
        }

        /*
        BlockModelBuilder testPlant = models().withExistingParent("test_plant", modLoc("block/bushy_plant"))
                .texture("fan", modLoc("block/test_plant_base"))
                .texture("cross", modLoc("block/test_plant_flower"));
        simpleBlock(ModBlocks.TEST_PLANT.get(), testPlant);
        */


        for (RegistryObject<TallWallBlock> _wall : ModBlocks._COLLECTION_TALL_WALLS) {
            final ModWallBlock block = _wall.get();
            final String forBlock = block.ForBlock;
            final ResourceLocation texture = mcLoc("block/" + forBlock);
            ModelFile _post = models().withExistingParent(_wall.getId().getPath() + "_post", modLoc("block/tall_wall_post"))
                    .texture("wall", texture);
            ModelFile _side = models().withExistingParent(_wall.getId().getPath() + "_side", modLoc("block/tall_wall_side"))
                    .texture("wall", texture);
            ModelFile _sideTall = models().withExistingParent(_wall.getId().getPath() + "_side", modLoc("block/tall_wall_side_tall"))
                    .texture("wall", texture);
            wallBlock(block, _post, _side, _sideTall);
        }

        for (RegistryObject<ModVineBlock> _vine : ModBlocks._COLLECTION_VINES) {
            String _id = _vine.getId().getPath();
            modVine(_vine.get(), _id);
        }

        for (RegistryObject<ModVineBlock> _vine : ModBlocks._COLLECTION_TINTED_OVERLAY_VINES) {
            String _id = _vine.getId().getPath();
            modLayeredVine(_vine.get(), _id);
        }
    }

    public void tintedBlock(TintedBlock block) {
        String name = block.getRegistryName().getPath();

        ModelFile base = models().getExistingFile(modLoc("block/tinted_cube_all"));
        BlockModelBuilder builder = models().getBuilder(block.getRegistryName().getPath()).parent(base)
                .texture("all", modLoc("block/" + name));
        simpleBlock(block, builder);
    }

    public void rotatableBlock(RotatableBlock block) {
        String name = block.getRegistryName().getPath();
        ModelFile model = models().cubeAll(name, modLoc("block/" + name));

        VariantBlockStateBuilder variantBuilder = getVariantBuilder(block);
        variantBuilder.partialState()
                .with(RotatableBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(model).rotationY(0).addModel();
        variantBuilder.partialState()
                .with(RotatableBlock.FACING, Direction.EAST)
                .modelForState().modelFile(model).rotationY(90).addModel();
        variantBuilder.partialState()
                .with(RotatableBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(model).rotationY(180).addModel();
        variantBuilder.partialState()
                .with(RotatableBlock.FACING, Direction.WEST)
                .modelForState().modelFile(model).rotationY(270).addModel();
    }

    public void modVine(ModVineBlock block, String name) {
        ModelFile vines_1 = models().withExistingParent(name + "_1", modLoc("block/vines_1")).texture("vine", modLoc("block/" + name));
        ModelFile vines_1u = models().withExistingParent(name + "_1u", modLoc("block/vines_1u")).texture("vine", modLoc("block/" + name));
        ModelFile vines_2 = models().withExistingParent(name + "_2", modLoc("block/vines_2")).texture("vine", modLoc("block/" + name));
        ModelFile vines_2u = models().withExistingParent(name + "_2u", modLoc("block/vines_2u")).texture("vine", modLoc("block/" + name));
        ModelFile vines_2u_opposite = models().withExistingParent(name + "_2u_opposite", modLoc("block/vines_2u_opposite")).texture("vine", modLoc("block/" + name));
        ModelFile vines_2_opposite = models().withExistingParent(name + "_2_opposite", modLoc("block/vines_2_opposite")).texture("vine", modLoc("block/" + name));
        ModelFile vines_3 = models().withExistingParent(name + "_3", modLoc("block/vines_3")).texture("vine", modLoc("block/" + name));
        ModelFile vines_3u = models().withExistingParent(name + "_3u", modLoc("block/vines_3u")).texture("vine", modLoc("block/" + name));
        ModelFile vines_4 = models().withExistingParent(name + "_4", modLoc("block/vines_4")).texture("vine", modLoc("block/" + name));
        ModelFile vines_4u = models().withExistingParent(name + "_4u", modLoc("block/vines_4u")).texture("vine", modLoc("block/" + name));
        ModelFile vines_u = models().withExistingParent(name + "_u", modLoc("block/vines_u")).texture("vine", modLoc("block/" + name));

        modVineBlockState(block, vines_1, vines_1u, vines_2, vines_2u, vines_2u_opposite, vines_2_opposite, vines_3, vines_3u, vines_4, vines_4u, vines_u);
    }

    public void modLayeredVine(ModVineBlock block, String name) {
        ModelFile vines_1 = models().withExistingParent(name + "_1", modLoc("block/layered_tinted_vines_1")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));
        ModelFile vines_1u = models().withExistingParent(name + "_1u", modLoc("block/layered_tinted_vines_1u")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));
        ModelFile vines_2 = models().withExistingParent(name + "_2", modLoc("block/layered_tinted_vines_2")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));
        ModelFile vines_2u = models().withExistingParent(name + "_2u", modLoc("block/layered_tinted_vines_2u")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));
        ModelFile vines_2u_opposite = models().withExistingParent(name + "_2u_opposite", modLoc("block/layered_tinted_vines_2u_opposite")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));
        ModelFile vines_2_opposite = models().withExistingParent(name + "_2_opposite", modLoc("block/layered_tinted_vines_2_opposite")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));
        ModelFile vines_3 = models().withExistingParent(name + "_3", modLoc("block/layered_tinted_vines_3")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));
        ModelFile vines_3u = models().withExistingParent(name + "_3u", modLoc("block/layered_tinted_vines_3u")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));
        ModelFile vines_4 = models().withExistingParent(name + "_4", modLoc("block/layered_tinted_vines_4")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));
        ModelFile vines_4u = models().withExistingParent(name + "_4u", modLoc("block/layered_tinted_vines_4u")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));
        ModelFile vines_u = models().withExistingParent(name + "_u", modLoc("block/layered_tinted_vines_u")).texture("vine", modLoc("block/" + name + "_base")).texture("overlay", modLoc("block/" + name + "_overlay"));

        modVineBlockState(block, vines_1, vines_1u, vines_2, vines_2u, vines_2u_opposite, vines_2_opposite, vines_3, vines_3u, vines_4, vines_4u, vines_u);
    }

    private void modVineBlockState(Block block, ModelFile vines_1, ModelFile vines_1u, ModelFile vines_2, ModelFile vines_2u, ModelFile vines_2u_opposite, ModelFile vines_2_opposite, ModelFile vines_3, ModelFile vines_3u, ModelFile vines_4, ModelFile vines_4u, ModelFile vines_u) {
        VariantBlockStateBuilder variantBuilder = getVariantBuilder(block);
        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_1).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_1).rotationY(90).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_u).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_1u).rotationY(90).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_1).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_2).rotationY(180).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_1u).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_2u).rotationY(180).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_1).rotationY(180).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_2).rotationY(270).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_1u).rotationY(180).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_2u).rotationY(270).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_2_opposite).rotationY(90).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_3).rotationY(180).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_2u_opposite).rotationY(90).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, false)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_3u).rotationY(180).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_1).rotationY(270).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_2_opposite).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_1u).rotationY(270).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_2u_opposite).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_2).rotationY(90).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_3).rotationY(90).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_2u).rotationY(90).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, false)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_3u).rotationY(90).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_2).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_3).rotationY(270).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_2u).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, false)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_3u).rotationY(270).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_3).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, false)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_4).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, false)
                .modelForState().modelFile(vines_3u).addModel();

        variantBuilder.partialState()
                .with(VineBlock.EAST, true)
                .with(VineBlock.NORTH, true)
                .with(VineBlock.SOUTH, true)
                .with(VineBlock.UP, true)
                .with(VineBlock.WEST, true)
                .modelForState().modelFile(vines_4u).addModel();
    }

    public void woodBlock(RotatedPillarBlock block, RotatedPillarBlock log) {
        axisBlock(block, blockTexture(log), blockTexture(log));
    }

    private void tallFlower(ModelFile cross, RegistryObject<? extends TallFlowerBlock> block) {
        BlockModelBuilder lowerHalf = models().getBuilder(block.getId().getPath() + "_bottom").parent(cross)
                .texture("cross", "block/" + block.getId().getPath() + "_bottom");

        BlockModelBuilder upperHalf = models().getBuilder(block.getId().getPath() + "_top").parent(cross)
                .texture("cross", "block/" + block.getId().getPath() + "_top");

        VariantBlockStateBuilder variantBuilder = getVariantBuilder(block.get());
        variantBuilder.partialState()
                .with(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER)
                .modelForState().modelFile(lowerHalf).addModel();
        variantBuilder.partialState()
                .with(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER)
                .modelForState().modelFile(upperHalf).addModel();
    }

    private void floweringLeaves(Block block) {
        ModelFile base = models().getExistingFile(modLoc("block/flowering_leaves"));
        BlockModelBuilder builder = models().getBuilder(block.getRegistryName().getPath()).parent(base)
                .texture("all", modLoc("block/" + block.getRegistryName().getPath()))
                .texture("overlay", modLoc("block/" + block.getRegistryName().getPath() + "_overlay"));
        simpleBlock(block, builder);
    }

    private void leaves(Block block) {
        ModelFile base = models().getExistingFile(mcLoc("block/leaves"));
        BlockModelBuilder builder = models().getBuilder(block.getRegistryName().getPath()).parent(base)
                .texture("all", modLoc("block/" + block.getRegistryName().getPath()));
        simpleBlock(block, builder);
    }

    private void transparentBlock(Block block) {
        ModelFile base = models().getExistingFile(modLoc("block/transparent_block"));
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
