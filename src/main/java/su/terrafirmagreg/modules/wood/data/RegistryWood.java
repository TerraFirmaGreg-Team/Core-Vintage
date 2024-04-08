package su.terrafirmagreg.modules.wood.data;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.api.recipes.LoomRecipe;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public final class RegistryWood {

    public static IForgeRegistry<LoomRecipe> LOOM;

    public static void onRegister() {

        LOOM = new RegistryBuilder<LoomRecipe>()
                .setName(ModUtils.getID("loom_recipe"))
                .setType(LoomRecipe.class)
                .allowModification()
                .create();
    }
}
