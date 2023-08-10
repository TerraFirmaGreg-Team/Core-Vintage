package net.dries007.tfc.types;

import net.dries007.tfc.objects.recipes.category.*;
import net.minecraftforge.fml.common.Mod;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public final class DefaultRecipes {

    public static void init() {
        AnvilRecipes.registerAnvilRecipes();
        BarrelRecipes.registerBarrelRecipes();
        BlastFurnaceRecipes.registerBlastFurnaceRecipes();
        BloomeryRecipes.registerBloomeryRecipes();
        ChiselRecipes.registerChiselRecipes();
        HeatRecipes.registerHeatRecipes();
        KnappingRecipes.registerKnappingRecipes();
        LoomRecipes.registerLoomRecipes();
        QuernRecipes.registerQuernRecipes();
        WeldingRecipes.registerWeldingRecipes();
        WorkbenchRecipes.onRegisterWorkbenchRecipes();
    }
}
