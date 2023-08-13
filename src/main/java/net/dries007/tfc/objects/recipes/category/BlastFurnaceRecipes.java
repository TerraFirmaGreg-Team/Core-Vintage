package net.dries007.tfc.objects.recipes.category;

import gregtech.api.unification.material.Materials;
import net.dries007.tfc.api.recipes.BlastFurnaceRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

public class BlastFurnaceRecipes {

    public static void register() {
        var registry = TFCRegistries.BLAST_FURNACE;

        registry.registerAll(
                new BlastFurnaceRecipe(TFGMaterials.PigIron, Materials.Iron, IIngredient.of("dustFlux"))
        );
    }
}
