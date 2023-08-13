package net.dries007.tfc.objects.recipes.category;

import gregtech.api.unification.material.Materials;
import net.dries007.tfc.api.recipes.BloomeryRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.util.fuel.FuelManager;

public class BloomeryRecipes {

    public static void register() {
        var registry = TFCRegistries.BLOOMERY;

        registry.registerAll(
                new BloomeryRecipe(Materials.Iron, FuelManager::isItemBloomeryFuel)
        );
    }
}
