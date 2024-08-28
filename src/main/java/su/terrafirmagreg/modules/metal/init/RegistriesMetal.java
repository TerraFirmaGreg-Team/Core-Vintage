package su.terrafirmagreg.modules.metal.init;

import su.terrafirmagreg.modules.metal.objects.recipe.anvil.AnvilRecipeManager;
import su.terrafirmagreg.modules.metal.objects.recipe.anvil.IAnvilRecipeManager;

public final class RegistriesMetal {

    public static IAnvilRecipeManager ANVIL;

    public static void onRegister() {

        ANVIL = new AnvilRecipeManager();
    }

}
