package team.mixxit.allotment.data.client;

import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.*;
import team.mixxit.allotment.setup.ModBlocks;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AllotmentMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected  void registerModels() {
        //block("test_block");
        block("lawn_block");
        block("hose_reel");
        block("zen_gravel");
        block("zen_gravel_straight");
        block("zen_gravel_corner");
        block("zen_gravel_end");
        block("elder_log");
        block("stripped_elder_log");
        block("elder_wood");
        block("stripped_elder_wood");
        block("elder_planks");
        block("elder_leaves");
        block("bamboo_block");
        block("dried_bamboo_block");

        // TODO All firewood bundles in one array; iterate with for loop
        block("spruce_firewood_bundle");

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile layeredTinted = getExistingFile(modLoc("item/layered_tinted"));

        builder(itemGenerated, "dried_bamboo");

        for (RegistryObject<FlowerBlock> _flower : ModBlocks._COLLECTION_FLOWERS) {
            builderForBlock(itemGenerated, _flower.getId().getPath());
        }
        for (RegistryObject<ModMushroomBlock> _mushroom : ModBlocks._COLLECTION_MUSHROOMS) {
            builderForBlock(itemGenerated, _mushroom.getId().getPath());
        }
        for (RegistryObject<SmallCactusBlock> _cactus : ModBlocks._COLLECTION_SMALL_CACTI) {
            builderForBlock(itemGenerated, _cactus.getId().getPath());
        }
        for (RegistryObject<Block> _plank : ModBlocks._COLLECTION_PLANKS) {
            block(_plank.getId().getPath());
        }
        for (RegistryObject<TrapDoorBlock> _trapdoor : ModBlocks._COLLECTION_TRAPDOORS) {
            trapdoor(_trapdoor.getId().getPath());
        }
        for (RegistryObject<? extends TallFlowerBlock> _tallflower : ModBlocks._COLLECTION_TALL_FLOWERS) {
            builderTallFlower(itemGenerated, _tallflower.getId().getPath());
        }
        builder(itemGenerated, "pink_pampas_grass");
        builder(itemGenerated, "pampas_grass");

        //fenceInventory("chain_link_fence", modLoc("block/chain_link_fence"));
        withExistingParent("chain_link_fence", modLoc("block/chain_link_fence_inventory"));

        block("straw");

        for (RegistryObject<ModFenceBlock> _fence : ModBlocks._COLLECTION_FENCES) {
            fenceInventory(_fence.getId().getPath(), modLoc("block/" + _fence.get().ForBlock));
        }
        for (RegistryObject<ModFenceGateBlock> _fencegate : ModBlocks._COLLECTION_FENCEGATES) {
            fenceGate(_fencegate.getId().getPath(), modLoc("block/" + _fencegate.get().ForBlock));
        }

        block("cracked_clay");
        block("humus");
        block("turf");
        block("ferralsol");
        block("mulch");
        block("terra_preta");
        block("spanish_moss");

        for (RegistryObject<ModStairsBlock> _stairs : ModBlocks._COLLECTION_STAIRS) {
            block(_stairs.getId().getPath());
        }

        for (RegistryObject<ModSlabBlock> _slab : ModBlocks._COLLECTION_SLABS) {
            slab(
                    _slab.getId().getPath(),
                    modLoc("block/" + _slab.get().WithTextureSides),
                    modLoc("block/" + _slab.get().WithTextureBottom),
                    modLoc("block/" + _slab.get().WithTextureTop)
            );
        }
    }

    private void slabAll(String name, ResourceLocation texture) {
        slab(name, texture, texture, texture);
    }

    private void block(String name) {
        withExistingParent(name, modLoc("block/" + name));
    }

    private void trapdoor(String name) {
        withExistingParent(name, modLoc("block/" + name + "_bottom"));
    }

    private void flower(String name) {
        singleTexture(name, modLoc("block/" + name), modLoc("block/" + name));
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

    private ItemModelBuilder builderWithTexture(ModelFile itemGenerated, String name, String texture) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + texture);
    }

    private ItemModelBuilder builderTallFlower(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "block/" + name + "_top");
    }

    private ItemModelBuilder builderForBlock(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "block/" + name);
    }
    private ItemModelBuilder builderForBlock(ModelFile itemGenerated, String name, String textureName) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "block/" + textureName);
    }
}
