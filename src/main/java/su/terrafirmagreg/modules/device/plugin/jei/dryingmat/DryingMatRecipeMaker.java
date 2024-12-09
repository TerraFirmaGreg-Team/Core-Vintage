package su.terrafirmagreg.modules.device.plugin.jei.dryingmat;

import su.terrafirmagreg.modules.device.init.RegistriesDevice;

import java.util.List;
import java.util.stream.Collectors;

public class DryingMatRecipeMaker {

  public static List<DryingMatRecipeWrapper> getRecipes() {
    return RegistriesDevice.DRYING_MAT.recipes()
                                      .stream()
                                      .map(DryingMatRecipeWrapper::new)
                                      .collect(Collectors.toList());
  }
}
