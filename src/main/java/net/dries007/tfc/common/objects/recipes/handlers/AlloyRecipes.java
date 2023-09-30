package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.unification.material.Materials;
import net.dries007.tfc.module.core.api.recipes.AlloyRecipe;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.module.core.init.RegistryCore;

public class AlloyRecipes {
    public static void register() {
        var registry = RegistryCore.ALLOYS;

        registry.registerAll(
                new AlloyRecipe
                        .Builder(Materials.BismuthBronze)
                        .add(Materials.Zinc, 0.2, 0.3)
                        .add(Materials.Copper, 0.5, 0.65)
                        .add(Materials.Bismuth, 0.1, 0.2).build(),

                new AlloyRecipe
                        .Builder(Materials.BlackBronze)
                        .add(Materials.Copper, 0.5, 0.7)
                        .add(Materials.Silver, 0.1, 0.25)
                        .add(Materials.Gold, 0.1, 0.25).build(),

                new AlloyRecipe
                        .Builder(Materials.Bronze)
                        .add(Materials.Copper, 0.88, 0.92)
                        .add(Materials.Tin, 0.08, 0.12).build(),

                new AlloyRecipe
                        .Builder(Materials.Brass)
                        .add(Materials.Copper, 0.88, 0.92)
                        .add(Materials.Zinc, 0.08, 0.12).build(),

                new AlloyRecipe.Builder(Materials.RoseGold)
                        .add(Materials.Copper, 0.15, 0.3)
                        .add(Materials.Gold, 0.7, 0.85).build(),

                new AlloyRecipe.Builder(Materials.SterlingSilver)
                        .add(Materials.Copper, 0.2, 0.4)
                        .add(Materials.Silver, 0.6, 0.8).build(),

                new AlloyRecipe.Builder(TFGMaterials.WeakSteel)
                        .add(Materials.Steel, 0.5, 0.7)
                        .add(Materials.Nickel, 0.15, 0.25)
                        .add(Materials.BlackBronze, 0.15, 0.25).build(),

                new AlloyRecipe.Builder(TFGMaterials.WeakBlueSteel)
                        .add(Materials.BlackSteel, 0.5, 0.55)
                        .add(Materials.Steel, 0.2, 0.25)
                        .add(Materials.BismuthBronze, 0.1, 0.15)
                        .add(Materials.SterlingSilver, 0.1, 0.15).build(),

                new AlloyRecipe
                        .Builder(TFGMaterials.WeakRedSteel)
                        .add(Materials.BlackSteel, 0.5, 0.55)
                        .add(Materials.Steel, 0.2, 0.25)
                        .add(Materials.Brass, 0.1, 0.15)
                        .add(Materials.RoseGold, 0.1, 0.15).build()
        );
    }
}
