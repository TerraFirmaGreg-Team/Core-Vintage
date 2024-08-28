package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.modules.device.objects.recipes.quern.IQuernRecipeManager;
import su.terrafirmagreg.modules.device.objects.recipes.quern.QuernRecipeManager;

public final class RegistriesDevice {

    public static IQuernRecipeManager QUERN;

    public static void onRegister() {

        QUERN = new QuernRecipeManager();
    }

}
