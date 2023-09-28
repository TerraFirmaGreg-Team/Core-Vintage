package net.dries007.tfc.api.registries;

import net.dries007.tfc.api.recipes.*;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Get Registry instances for standard TFC objects here.
 */
public class TFCRegistries {
    public static final IForgeRegistry<AlloyRecipe> ALLOYS = GameRegistry.findRegistry(AlloyRecipe.class);
    public static final IForgeRegistry<KnappingRecipe> KNAPPING = GameRegistry.findRegistry(KnappingRecipe.class);
    public static final IForgeRegistry<AnvilRecipe> ANVIL = GameRegistry.findRegistry(AnvilRecipe.class);
    public static final IForgeRegistry<WeldingRecipe> WELDING = GameRegistry.findRegistry(WeldingRecipe.class);
    public static final IForgeRegistry<HeatRecipe> HEAT = GameRegistry.findRegistry(HeatRecipe.class);
    public static final IForgeRegistry<BarrelRecipe> BARREL = GameRegistry.findRegistry(BarrelRecipe.class);
    public static final IForgeRegistry<LoomRecipe> LOOM = GameRegistry.findRegistry(LoomRecipe.class);
    public static final IForgeRegistry<QuernRecipe> QUERN = GameRegistry.findRegistry(QuernRecipe.class);
    public static final IForgeRegistry<ChiselRecipe> CHISEL = GameRegistry.findRegistry(ChiselRecipe.class);
    public static final IForgeRegistry<BloomeryRecipe> BLOOMERY = GameRegistry.findRegistry(BloomeryRecipe.class);
    public static final IForgeRegistry<BlastFurnaceRecipe> BLAST_FURNACE = GameRegistry.findRegistry(BlastFurnaceRecipe.class);
}
