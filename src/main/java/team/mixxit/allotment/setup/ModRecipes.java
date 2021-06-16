package team.mixxit.allotment.setup;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.crafting.ToolUsageRecipe;

import java.util.function.Supplier;

public class ModRecipes {
    public static final RegistryObject<IRecipeSerializer<?>> CRAFTING_SHAPELESS_WITH_TOOL =
            register("crafting_shapeless_with_tool", ToolUsageRecipe.Serializer::new);

    public static RegistryObject<IRecipeSerializer<?>> register(String name, Supplier<IRecipeSerializer<?>> sup) {
        return Registration.RECIPES.register(name, sup);
    }

    public static void register() {
    }
}
