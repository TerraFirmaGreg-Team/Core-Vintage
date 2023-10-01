package net.dries007.tfc.common.objects.recipes;

import net.dries007.tfc.common.objects.recipes.handlers.*;

public final class RecipeHandler {

    public static void init() {
        AlloyRecipes.register();
        AnvilRecipes.register();
        BlastFurnaceRecipes.register();
        BloomeryRecipes.register();
        ChiselRecipes.register();
        HeatRecipes.register();
        KnappingRecipes.register();
        QuernRecipes.register();
        WeldingRecipes.register();
        WorkbenchRecipes.register();
    }
}
