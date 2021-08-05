package team.mixxit.allotment.data;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.MadeFromBlock;
import team.mixxit.allotment.blocks.ModFlowerBlock;
import team.mixxit.allotment.blocks.TallWallBlock;
import team.mixxit.allotment.crafting.ToolUsageRecipeBuilder;
import team.mixxit.allotment.setup.ModBlocks;
import team.mixxit.allotment.setup.ModItems;
import team.mixxit.allotment.setup.Registration;
import team.mixxit.allotment.util.RegistryHelper;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        for (RegistryObject<ModFlowerBlock> _flower : ModBlocks._COLLECTION_FLOWERS) {
            ModFlowerBlock flower = _flower.get();
            DyeItem dyeItem = flower.getDyeItem();
            ShapelessRecipeBuilder.shapelessRecipe(dyeItem, 1)
                    .addIngredient(flower)
                    .addCriterion("has_item", hasItem(flower))
                    .build(consumer, modLoc(dyeItem.getRegistryName().getPath() + "_from_" + flower.getRegistryName().getPath()));
        }

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.HOSE_REEL.get())
                .key('/', Items.IRON_INGOT)
                .key('@', Items.STRING)
                .patternLine(" / ")
                .patternLine("@@@")
                .patternLine("///")
                .addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT))
                .addCriterion("has_string", hasItem(Items.STRING))
                .build(consumer);

        for (RegistryObject<TallWallBlock> _wall : ModBlocks._COLLECTION_TALL_WALLS) {
            TallWallBlock wall = _wall.get();
            ShapedRecipeBuilder.shapedRecipe(wall)
                    .key('#', wall.ForBlock)
                    .patternLine("###")
                    .patternLine("###")
                    .addCriterion("has_item", hasItem(wall.ForBlock))
                    .setGroup("concrete_walls")
                    .build(consumer);
        }

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.GUTTER.get())
                .key('#', Items.IRON_BARS)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_item", hasItem(Items.IRON_BARS))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_THIN_FENCES[ModBlocks.CHAIN_LINK_FENCE].get())
                .key('/', Items.IRON_INGOT)
                .key('%', Items.CHAIN)
                .patternLine("/%/")
                .patternLine("/%/")
                .addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT))
                .addCriterion("has_chain", hasItem(Items.CHAIN))
                .build(consumer);


        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_THIN_FENCES[ModBlocks.JAKTOP_CRISS_CROSS_FENCE].get())
                .key('#', ItemTags.LOGS)
                .key('!', Items.STICK)
                .patternLine("#!#")
                .patternLine("#!#")
                .addCriterion("has_log", hasItem(ItemTags.LOGS))
                .addCriterion("has_stick", hasItem(Items.STICK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_THIN_FENCES[ModBlocks.BAR_MAT_FENCE].get())
                .key('/', Items.IRON_INGOT)
                .key('#', Items.IRON_BARS)
                .patternLine("/#/")
                .patternLine("/#/")
                .addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT))
                .addCriterion("has_chain", hasItem(Items.IRON_BARS))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.ELDER_BUTTON.get())
                .addIngredient(ModBlocks.ELDER_PLANKS.get())
                .addCriterion("has_item", hasItem(ModBlocks.ELDER_PLANKS.get()))
                .build(consumer);

        // TODO Replace with item tag
        Ingredient axeIngredients = Ingredient.fromItems(
                () -> Items.WOODEN_AXE,
                () -> Items.STONE_AXE,
                () -> Items.GOLDEN_AXE,
                () -> Items.IRON_AXE,
                () -> Items.DIAMOND_AXE,
                () -> Items.NETHERITE_AXE
        );

        for (RegistryObject<MadeFromBlock> _plank : ModBlocks._COLLECTION_PLANKS) {
            Block madeFrom = _plank.get().getMadeFrom().get();
            AllotmentMod.getLogger().debug("Registering recipe for " + _plank.getId().toString());
            AllotmentMod.getLogger().debug(" \\--> Made from block " + madeFrom.getRegistryName().toString());
            ToolUsageRecipeBuilder.begin(_plank.get())
                    .addIngredient(madeFrom)
                    .addIngredient(axeIngredients)
                    .addCriterion("has_plank", hasItem(madeFrom))
                    .build(consumer);
        }

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.ELDER_WOOD.get(), 3)
                .key('x', ModBlocks.ELDER_LOG.get())
                .patternLine("xx")
                .patternLine("xx")
                .addCriterion("has_item", hasItem(ModBlocks.ELDER_LOG.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.STRIPPED_ELDER_WOOD.get(), 3)
                .key('x', ModBlocks.STRIPPED_ELDER_LOG.get())
                .patternLine("xx")
                .patternLine("xx")
                .addCriterion("has_item", hasItem(ModBlocks.STRIPPED_ELDER_LOG.get()))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.ELDER_PLANKS.get(), 4)
                .addIngredient(ModBlocks.ELDER_LOG.get())
                .addCriterion("has_item", hasItem(ModBlocks.ELDER_PLANKS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.BAMBOO_BLOCK.get())
                .key('x', Items.BAMBOO)
                .patternLine("xx")
                .patternLine("xx")
                .addCriterion("has_item", hasItem(Items.BAMBOO))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.DRIED_BAMBOO_BLOCK.get())
                .key('x', ModItems.DRIED_BAMBOO.get())
                .patternLine("xx")
                .patternLine("xx")
                .addCriterion("has_item", hasItem(ModItems.DRIED_BAMBOO.get()))
                .build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.BAMBOO), ModItems.DRIED_BAMBOO.get(), 0.1f, 200)
                .addCriterion("has_item", hasItem(Items.BAMBOO))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_FENCES[ModBlocks.BAMBOO_FENCE].get())
                .key('b', Items.BAMBOO)
                .key('s', Items.STICK)
                .patternLine("bsb")
                .patternLine("bsb")
                .addCriterion("has_bamboo", hasItem(Items.BAMBOO))
                .addCriterion("has_stick", hasItem(Items.STICK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_FENCES[ModBlocks.DRIED_BAMBOO_FENCE].get())
                .key('b', ModItems.DRIED_BAMBOO.get())
                .key('s', Items.STICK)
                .patternLine("bsb")
                .patternLine("bsb")
                .addCriterion("has_bamboo", hasItem(ModItems.DRIED_BAMBOO.get()))
                .addCriterion("has_stick", hasItem(Items.STICK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_FENCES[ModBlocks.ELDER_FENCE].get())
                .key('p', ModBlocks.ELDER_PLANKS.get())
                .key('s', Items.STICK)
                .patternLine("psp")
                .patternLine("psp")
                .addCriterion("has_planks", hasItem(ModBlocks.ELDER_PLANKS.get()))
                .addCriterion("has_stick", hasItem(Items.STICK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_FENCEGATES[ModBlocks.BAMBOO_FENCE_GATE].get())
                .key('b', Items.BAMBOO)
                .key('s', Items.STICK)
                .patternLine("sbs")
                .patternLine("sbs")
                .addCriterion("has_bamboo", hasItem(Items.BAMBOO))
                .addCriterion("has_stick", hasItem(Items.STICK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_FENCEGATES[ModBlocks.DRIED_BAMBOO_FENCE_GATE].get())
                .key('b', ModItems.DRIED_BAMBOO.get())
                .key('s', Items.STICK)
                .patternLine("sbs")
                .patternLine("sbs")
                .addCriterion("has_bamboo", hasItem(ModItems.DRIED_BAMBOO.get()))
                .addCriterion("has_stick", hasItem(Items.STICK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_FENCEGATES[ModBlocks.ELDER_FENCE_GATE].get())
                .key('p', ModBlocks.ELDER_PLANKS.get())
                .key('s', Items.STICK)
                .patternLine("sps")
                .patternLine("sps")
                .addCriterion("has_planks", hasItem(ModBlocks.ELDER_PLANKS.get()))
                .addCriterion("has_stick", hasItem(Items.STICK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_STAIRS.get(ModBlocks.ELDER_STAIRS).get())
                .key('x', ModBlocks.ELDER_PLANKS.get())
                .patternLine("x  ")
                .patternLine("xx ")
                .patternLine("xxx")
                .addCriterion("has_item", hasItem(ModBlocks.ELDER_PLANKS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_STAIRS.get(ModBlocks.BAMBOO_STAIRS).get())
                .key('x', ModBlocks.BAMBOO_BLOCK.get())
                .patternLine("x  ")
                .patternLine("xx ")
                .patternLine("xxx")
                .addCriterion("has_item", hasItem(ModBlocks.BAMBOO_BLOCK.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_STAIRS.get(ModBlocks.DRIED_BAMBOO_STAIRS).get())
                .key('x', ModBlocks.DRIED_BAMBOO_BLOCK.get())
                .patternLine("x  ")
                .patternLine("xx ")
                .patternLine("xxx")
                .addCriterion("has_item", hasItem(ModBlocks.DRIED_BAMBOO_BLOCK.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_SLABS.get(ModBlocks.ELDER_SLAB).get())
                .key('x', ModBlocks.ELDER_PLANKS.get())
                .patternLine("xxx")
                .addCriterion("has_item", hasItem(ModBlocks.ELDER_PLANKS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_SLABS.get(ModBlocks.BAMBOO_SLAB).get())
                .key('x', ModBlocks.BAMBOO_BLOCK.get())
                .patternLine("xxx")
                .addCriterion("has_item", hasItem(ModBlocks.BAMBOO_BLOCK.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_SLABS.get(ModBlocks.DRIED_BAMBOO_SLAB).get())
                .key('x', ModBlocks.DRIED_BAMBOO_BLOCK.get())
                .patternLine("xxx")
                .addCriterion("has_item", hasItem(ModBlocks.DRIED_BAMBOO_BLOCK.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_TRAPDOORS[ModBlocks.ELDER_TRAPDOOR].get())
                .key('x', ModBlocks.ELDER_PLANKS.get())
                .patternLine("xxx")
                .patternLine("xxx")
                .addCriterion("has_item", hasItem(ModBlocks.ELDER_PLANKS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_TRAPDOORS[ModBlocks.BAMBOO_TRAPDOOR].get())
                .key('x', ModBlocks.BAMBOO_BLOCK.get())
                .patternLine("xxx")
                .patternLine("xxx")
                .addCriterion("has_item", hasItem(ModBlocks.BAMBOO_BLOCK.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks._COLLECTION_TRAPDOORS[ModBlocks.DRIED_BAMBOO_TRAPDOOR].get())
                .key('x', ModBlocks.DRIED_BAMBOO_BLOCK.get())
                .patternLine("xxx")
                .patternLine("xxx")
                .addCriterion("has_item", hasItem(ModBlocks.DRIED_BAMBOO_BLOCK.get()))
                .build(consumer);
    }

    private ResourceLocation modLoc(String name) {
        return new ResourceLocation(AllotmentMod.MOD_ID, name);
    }

    private ResourceLocation mcLoc(String name) {
        return new ResourceLocation(name);
    }
}
