package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.modules.device.object.recipe.dryingmat.DryingMatRecipeManager;
import su.terrafirmagreg.modules.device.object.recipe.dryingmat.IDryingMatRecipeManager;
import su.terrafirmagreg.modules.device.object.recipe.quern.IQuernRecipeManager;
import su.terrafirmagreg.modules.device.object.recipe.quern.QuernRecipeManager;

public final class RegistriesDevice {

  public static IQuernRecipeManager QUERN;
  public static IDryingMatRecipeManager DRYING_MAT;

  public static void onRegister() {

    QUERN = new QuernRecipeManager();
    DRYING_MAT = new DryingMatRecipeManager();
  }

}
