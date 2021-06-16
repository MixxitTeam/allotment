package team.mixxit.allotment.data;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
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

        // FIXME

        Ingredient axeIngredients = Ingredient.fromItems(
                () -> Items.WOODEN_AXE,
                () -> Items.STONE_AXE,
                () -> Items.GOLDEN_AXE,
                () -> Items.IRON_AXE,
                () -> Items.DIAMOND_AXE,
                () -> Items.NETHERITE_AXE
        );

        ToolUsageRecipeBuilder.begin(ModBlocks._COLLECTION_PLANKS.get(0).get())
                .addIngredient(Blocks.ACACIA_PLANKS)
                .addIngredient(axeIngredients)
                .addCriterion("has_plank", hasItem(Blocks.ACACIA_PLANKS))
                .build(consumer);
    }

    private ResourceLocation modLoc(String name) {
        return new ResourceLocation(AllotmentMod.MOD_ID, name);
    }

    private ResourceLocation mcLoc(String name) {
        return new ResourceLocation(name);
    }
}
