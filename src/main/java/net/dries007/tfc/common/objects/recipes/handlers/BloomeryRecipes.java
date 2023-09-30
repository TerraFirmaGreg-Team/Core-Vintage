package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.unification.material.Materials;
import net.dries007.tfc.api.recipes.BloomeryRecipe;
import net.dries007.tfc.module.core.api.util.fuel.FuelManager;
import net.dries007.tfc.module.core.init.RegistryCore;

public class BloomeryRecipes {

    public static void register() {
        var registry = RegistryCore.BLOOMERY;

        registry.registerAll(
                new BloomeryRecipe(Materials.Iron, FuelManager::isItemBloomeryFuel)
        );
    }
}
