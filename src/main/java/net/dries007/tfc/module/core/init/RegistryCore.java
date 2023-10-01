package net.dries007.tfc.module.core.init;

import net.dries007.tfc.module.core.api.recipes.AlloyRecipe;
import net.dries007.tfc.module.core.api.recipes.BlastFurnaceRecipe;
import net.dries007.tfc.module.core.api.recipes.BloomeryRecipe;
import net.dries007.tfc.module.core.api.recipes.WeldingRecipe;
import net.dries007.tfc.module.core.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.module.core.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.module.core.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.module.core.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.rock.api.recipes.RecipeRockChisel;
import net.dries007.tfc.module.wood.api.recipes.LoomRecipe;
import net.dries007.tfc.module.wood.api.recipes.barrel.BarrelRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public class RegistryCore {

    public static final IForgeRegistry<AlloyRecipe> ALLOYS = GameRegistry.findRegistry(AlloyRecipe.class);
    public static final IForgeRegistry<KnappingRecipe> KNAPPING = GameRegistry.findRegistry(KnappingRecipe.class);
    public static final IForgeRegistry<AnvilRecipe> ANVIL = GameRegistry.findRegistry(AnvilRecipe.class);
    public static final IForgeRegistry<WeldingRecipe> WELDING = GameRegistry.findRegistry(WeldingRecipe.class);
    public static final IForgeRegistry<HeatRecipe> HEAT = GameRegistry.findRegistry(HeatRecipe.class);
    public static final IForgeRegistry<BarrelRecipe> BARREL = GameRegistry.findRegistry(BarrelRecipe.class);
    public static final IForgeRegistry<LoomRecipe> LOOM = GameRegistry.findRegistry(LoomRecipe.class);
    public static final IForgeRegistry<QuernRecipe> QUERN = GameRegistry.findRegistry(QuernRecipe.class);
    public static final IForgeRegistry<RecipeRockChisel> CHISEL = GameRegistry.findRegistry(RecipeRockChisel.class);
    public static final IForgeRegistry<BloomeryRecipe> BLOOMERY = GameRegistry.findRegistry(BloomeryRecipe.class);
    public static final IForgeRegistry<BlastFurnaceRecipe> BLAST_FURNACE = GameRegistry.findRegistry(BlastFurnaceRecipe.class);

    public static void createRegistries(RegistryEvent.NewRegistry event) {

        new RegistryBuilder<AlloyRecipe>()
                .setName(Helpers.getID("alloy_recipe"))
                .setType(AlloyRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<KnappingRecipe>()
                .setName(Helpers.getID("knapping_recipe"))
                .setType(KnappingRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<AnvilRecipe>()
                .setName(Helpers.getID("anvil_recipe"))
                .setType(AnvilRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<WeldingRecipe>()
                .setName(Helpers.getID("welding_recipe"))
                .setType(WeldingRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<HeatRecipe>()
                .setName(Helpers.getID("heat_recipe"))
                .setType(HeatRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<BarrelRecipe>()
                .setName(Helpers.getID("barrel_recipe"))
                .setType(BarrelRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<LoomRecipe>()
                .setName(Helpers.getID("loom_recipe"))
                .setType(LoomRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<QuernRecipe>()
                .setName(Helpers.getID("quern_recipe"))
                .setType(QuernRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<RecipeRockChisel>()
                .setName(Helpers.getID("chisel_recipe"))
                .setType(RecipeRockChisel.class)
                .allowModification()
                .create();

        new RegistryBuilder<BloomeryRecipe>()
                .setName(Helpers.getID("bloomery_recipe"))
                .setType(BloomeryRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<BlastFurnaceRecipe>()
                .setName(Helpers.getID("blast_furnace_recipe"))
                .setType(BlastFurnaceRecipe.class)
                .allowModification()
                .create();
    }

    private static <T extends IForgeRegistryEntry<T>> void newRegistry(ResourceLocation name, Class<T> tClass) {
        IForgeRegistry<T> reg = new RegistryBuilder<T>()
                .setName(name)
                .allowModification()
                .setType(tClass)
                .create();
    }
}
