package team.mixxit.allotment.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.mixxit.allotment.setup.ModRecipes;
import team.mixxit.allotment.util.ReflectionHelper;

public class ToolUsageRecipe implements ICraftingRecipe {
    private final ResourceLocation id;
    private final String group;
    private final ItemStack recipeOutput;
    private final NonNullList<Ingredient> recipeItems;
    private final boolean isSimple;

    private static final Logger LOGGER = LogManager.getLogger();

    public ToolUsageRecipe(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn) {
        this.id = idIn;
        this.group = groupIn;
        this.recipeOutput = recipeOutputIn;
        this.recipeItems = recipeItemsIn;
        this.isSimple = recipeItemsIn.stream().allMatch(Ingredient::isSimple);
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.CRAFTING_SHAPELESS_WITH_TOOL.get();
    } // TODO

    /**
     * Recipes with equal group are combined into one button in the recipe book
     */
    public String getGroup() {
        return this.group;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(CraftingInventory inv, World worldIn) {
        RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (!itemstack.isEmpty()) {
                ++i;
                if (isSimple)
                    recipeitemhelper.func_221264_a(itemstack, 1);
                else inputs.add(itemstack);
            }
        }

        return i == this.recipeItems.size() && (isSimple ? recipeitemhelper.canCraft(this, (IntList)null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.recipeItems) != null);
    }

    public NonNullList<ItemStack> getRemainingItems(final CraftingInventory inv) {
        final NonNullList<ItemStack> remainingItems = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < remainingItems.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack.hasContainerItem() && itemstack.getItem() != Items.BUCKET) {
                remainingItems.set(i, itemstack.getContainerItem());
            } else if (itemstack.getItem() instanceof AxeItem) {
                final int currDmg = itemstack.getDamage();
                final int maxDmg = itemstack.getMaxDamage();
                final int durLeft = maxDmg - currDmg;
                if (durLeft > 0) {
                    final ItemStack stack = itemstack.copy();
                    stack.setDamage(currDmg + 1);
                    remainingItems.set(i, stack);
                }
                /*else {
                    // TODO Maybe play "tool broken" sound to player?
                }*/
            }
        }
        return remainingItems;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return this.recipeOutput.copy();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height) {
        return width * height >= this.recipeItems.size();
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ToolUsageRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("allotment", "crafting_shapeless_with_tool");
        public ToolUsageRecipe read(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getString(json, "group", "");
            NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));

            int srMaxWidth = 3;
            int srMaxHeight = 3;
            try {
                srMaxWidth = (int)ReflectionHelper.forceGetPrivateField(ShapedRecipe.class, "MAX_WIDTH");
                srMaxHeight = (int)ReflectionHelper.forceGetPrivateField(ShapedRecipe.class, "MAX_HEIGHT");
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }

            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (nonnulllist.size() > srMaxWidth * srMaxHeight) {
                throw new JsonParseException("Too many ingredients for shapeless recipe the max is " + (srMaxWidth * srMaxHeight));
            } else {
                ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
                return new ToolUsageRecipe(recipeId, s, itemstack, nonnulllist);
            }
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.deserialize(ingredientArray.get(i));
                if (!ingredient.hasNoMatchingItems()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        public ToolUsageRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            String s = buffer.readString(32767);
            int i = buffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.read(buffer));
            }

            ItemStack itemstack = buffer.readItemStack();
            return new ToolUsageRecipe(recipeId, s, itemstack, nonnulllist);
        }

        public void write(PacketBuffer buffer, ToolUsageRecipe recipe) {
            buffer.writeString(recipe.group);
            buffer.writeVarInt(recipe.recipeItems.size());

            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.recipeOutput);
        }
    }
}
