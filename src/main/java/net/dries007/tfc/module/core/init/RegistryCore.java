package net.dries007.tfc.module.core.init;

import net.dries007.tfc.TerraFirmaGreg;
import net.dries007.tfc.api.recipes.*;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public class RegistryCore {

    public static void createRegistries(RegistryEvent.NewRegistry event) {

        new RegistryBuilder<AlloyRecipe>()
                .setName(TerraFirmaGreg.getID("alloy_recipe"))
                .setType(AlloyRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<KnappingRecipe>()
                .setName(TerraFirmaGreg.getID("knapping_recipe"))
                .setType(KnappingRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<AnvilRecipe>()
                .setName(TerraFirmaGreg.getID("anvil_recipe"))
                .setType(AnvilRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<WeldingRecipe>()
                .setName(TerraFirmaGreg.getID("welding_recipe"))
                .setType(WeldingRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<HeatRecipe>()
                .setName(TerraFirmaGreg.getID("heat_recipe"))
                .setType(HeatRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<BarrelRecipe>()
                .setName(TerraFirmaGreg.getID("barrel_recipe"))
                .setType(BarrelRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<LoomRecipe>()
                .setName(TerraFirmaGreg.getID("loom_recipe"))
                .setType(LoomRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<QuernRecipe>()
                .setName(TerraFirmaGreg.getID("quern_recipe"))
                .setType(QuernRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<ChiselRecipe>()
                .setName(TerraFirmaGreg.getID("chisel_recipe"))
                .setType(ChiselRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<BloomeryRecipe>()
                .setName(TerraFirmaGreg.getID("bloomery_recipe"))
                .setType(BloomeryRecipe.class)
                .allowModification()
                .create();

        new RegistryBuilder<BlastFurnaceRecipe>()
                .setName(TerraFirmaGreg.getID("blast_furnace_recipe"))
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
