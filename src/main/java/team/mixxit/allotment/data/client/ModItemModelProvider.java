package team.mixxit.allotment.data.client;

import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.*;
import team.mixxit.allotment.setup.ModBlocks;
import team.mixxit.allotment.setup.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AllotmentMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected  void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

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
        block("flowering_elder_leaves");
        block("bamboo_block");
        block("dried_bamboo_block");
        block("straw_block");
        block("cracked_clay");
        block("humus");
        block("turf");
        block("ferralsol");
        block("mulch");
        block("terra_preta");
        block("spanish_moss");
        block("pincussion_moss");
        block("corrugated_iron");

        block( "debug_block");
        builderWithBlockTexture(itemGenerated, "debug_tint_block", "debug_tint_block");
        builderWithBlockTexture(itemGenerated, "debug_foliage_block", "debug_foliage_block");

        // TODO All firewood bundles in one array; iterate with for loop
        block("spruce_firewood_bundle");

        builder(itemGenerated, "dried_bamboo");
        builder(itemGenerated, "pink_pampas_grass");
        builder(itemGenerated, "pampas_grass");
        builder(itemGenerated, "straw");

        gutter("gutter");

        button("elder_button");

        for (RegistryObject<ModFlowerBlock> _flower : ModBlocks._COLLECTION_FLOWERS) {
            builderForBlock(itemGenerated, _flower.getId().getPath());
        }

        for (RegistryObject<ModMushroomBlock> _mushroom : ModBlocks._COLLECTION_MUSHROOMS) {
            builderForBlock(itemGenerated, _mushroom.getId().getPath());
        }

        for (RegistryObject<SmallCactusBlock> _cactus : ModBlocks._COLLECTION_SMALL_CACTI) {
            builderForBlock(itemGenerated, _cactus.getId().getPath());
        }

        for (RegistryObject<MadeFromBlock> _plank : ModBlocks._COLLECTION_PLANKS) {
            block(_plank.getId().getPath());
        }

        for (RegistryObject<TrapDoorBlock> _trapdoor : ModBlocks._COLLECTION_TRAPDOORS) {
            trapdoor(_trapdoor.getId().getPath());
        }

        for (RegistryObject<? extends TallFlowerBlock> _tallflower : ModBlocks._COLLECTION_TALL_FLOWERS) {
            builderTallFlower(itemGenerated, _tallflower.getId().getPath());
        }

        for (RegistryObject<ThinFenceBlock> _thinFence : ModBlocks._COLLECTION_THIN_FENCES) {
            withExistingParent(_thinFence.getId().getPath(), modLoc("block/thin_fence_inventory"))
                    .texture("all", modLoc("block/" + _thinFence.getId().getPath()));
        }

        for (RegistryObject<ModFenceBlock> _fence : ModBlocks._COLLECTION_FENCES) {
            fenceInventory(_fence.getId().getPath(), modLoc("block/" + _fence.get().ForBlock));
        }

        for (RegistryObject<ModFenceGateBlock> _fencegate : ModBlocks._COLLECTION_FENCEGATES) {
            fenceGate(_fencegate.getId().getPath(), modLoc("block/" + _fencegate.get().ForBlock));
        }

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

        for (RegistryObject<TallWallBlock> _wall : ModBlocks._COLLECTION_TALL_WALLS) {
            tallWall(_wall.getId().getPath(), mcLoc("block/" + _wall.get().ForBlock.getRegistryName().getPath()));
        }

        for (RegistryObject<ModVineBlock> _vine : ModBlocks._COLLECTION_VINES) {
            builderForBlock(itemGenerated, _vine.getId().getPath());
        }

        for (RegistryObject<Item> _item : ModItems._COLLECTION_TINTED_OVERLAY_VINES) {
            System.out.println("[[DEBUG] TintedOverlayVines]: " + _item.getId().getPath());
            builder(itemGenerated, _item.getId().getPath());
        }

        builderWithBlockTexture(itemGenerated, "test_flower", "guzmania");

        String elderSaplingPath = ModBlocks.ELDER_SAPLING.getId().getPath();
        builderWithBlockTexture(itemGenerated, elderSaplingPath, elderSaplingPath);

        builder(itemGenerated, "elder_door");
    }

    public void tallWall(String name, ResourceLocation texture) {
        singleTexture(name, modLoc( "block/tall_wall_inventory"), "wall", texture);
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

    private ItemModelBuilder gutter(String name) {
        return withExistingParent(name, modLoc("block/" + name));
    }

    private ItemModelBuilder button(String name) {
        return withExistingParent(name, modLoc("block/" + name + "_inventory"));
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

    private ItemModelBuilder builderWithTexture(ModelFile itemGenerated, String name, String texture) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", texture);
    }

    private ItemModelBuilder builderWithItemTexture(ModelFile itemGenerated, String name, String texture) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + texture);
    }

    private ItemModelBuilder builderWithBlockTexture(ModelFile itemGenerated, String name, String texture) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "block/" + texture);
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
