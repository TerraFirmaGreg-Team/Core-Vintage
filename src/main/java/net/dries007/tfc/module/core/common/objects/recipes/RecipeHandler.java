package net.dries007.tfc.module.core.common.objects.recipes;

import net.dries007.tfc.common.objects.recipes.handlers.*;
import net.dries007.tfc.module.core.common.objects.recipes.handlers.*;

public final class RecipeHandler {

    public static void init() {
        AlloyRecipes.register();
        AnvilRecipes.register();
        BarrelRecipes.register();
        BlastFurnaceRecipes.register();
        BloomeryRecipes.register();
        ChiselRecipes.register();
        HeatRecipes.register();
        KnappingRecipes.register();
        LoomRecipes.register();
        QuernRecipes.register();
        WeldingRecipes.register();
        WorkbenchRecipes.register();
    }
}
