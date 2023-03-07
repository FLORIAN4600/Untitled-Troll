package fr.florian4600.untitledtroll.recipe;

import fr.florian4600.untitledtroll.MainClass;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class UTRecipes {

    public static final RecipeSerializer<TrappedBlockRecipe> TRAPPED_BLOCK;

    public UTRecipes() {

    }

    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, MainClass.newId("block_trapping"), TRAPPED_BLOCK);
    }

    static {
        TRAPPED_BLOCK = new TrappingRecipeSerializer<>(TrappedBlockRecipe::new);
    }

}
