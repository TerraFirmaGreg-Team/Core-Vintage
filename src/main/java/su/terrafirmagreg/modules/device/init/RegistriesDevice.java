package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.modules.device.object.recipe.quern.IQuernRecipeManager;
import su.terrafirmagreg.modules.device.object.recipe.quern.QuernRecipeManager;

public final class RegistriesDevice {

  public static IQuernRecipeManager QUERN;

  public static void onRegister() {

    QUERN = new QuernRecipeManager();
  }

}
