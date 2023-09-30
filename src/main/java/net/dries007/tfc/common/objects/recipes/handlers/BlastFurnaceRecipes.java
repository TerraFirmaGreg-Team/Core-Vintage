package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.unification.material.Materials;
import net.dries007.tfc.module.core.api.recipes.BlastFurnaceRecipe;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.module.core.init.RegistryCore;

public class BlastFurnaceRecipes {

    public static void register() {
        var registry = RegistryCore.BLAST_FURNACE;

        registry.registerAll(
                new BlastFurnaceRecipe(TFGMaterials.PigIron, Materials.Iron, IIngredient.of("dustFlux"))
        );
    }
}
