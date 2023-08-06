package net.dries007.tfc.types;

import net.minecraftforge.fml.common.Mod;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.recipes.category.AnvilRecipes.registerAnvilRecipes;
import static net.dries007.tfc.objects.recipes.category.BarrelRecipes.registerBarrelRecipes;
import static net.dries007.tfc.objects.recipes.category.BlastFurnaceRecipes.registerBlastFurnaceRecipes;
import static net.dries007.tfc.objects.recipes.category.BloomeryRecipes.registerBloomeryRecipes;
import static net.dries007.tfc.objects.recipes.category.ChiselRecipes.registerChiselRecipes;
import static net.dries007.tfc.objects.recipes.category.HeatRecipes.registerHeatRecipes;
import static net.dries007.tfc.objects.recipes.category.KnappingRecipes.registerKnappingRecipes;
import static net.dries007.tfc.objects.recipes.category.LoomRecipes.registerLoomRecipes;
import static net.dries007.tfc.objects.recipes.category.QuernRecipes.registerQuernRecipes;
import static net.dries007.tfc.objects.recipes.category.WeldingRecipes.registerWeldingRecipes;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public final class DefaultRecipes {

    public static void init() {
        registerAnvilRecipes();
        registerBarrelRecipes();
        registerBlastFurnaceRecipes();
        registerBloomeryRecipes();
        registerChiselRecipes();
        registerHeatRecipes();
        registerKnappingRecipes();
        registerLoomRecipes();
        registerQuernRecipes();
        registerWeldingRecipes();
        //onRegisterWorkbenchRecipes();
    }


}
