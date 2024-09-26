package su.terrafirmagreg.modules.device.plugin.jei.quern;

import su.terrafirmagreg.modules.device.init.RegistriesDevice;

import java.util.List;
import java.util.stream.Collectors;

public class QuernRecipeMaker {

  public static List<QuernRecipeWrapper> getRecipes() {
    return RegistriesDevice.QUERN.recipes()
                                 .stream()
                                 .map(QuernRecipeWrapper::new)
                                 .collect(Collectors.toList());
  }
}
