package su.terrafirmagreg.modules.metal.plugin.jei.welding;

import su.terrafirmagreg.modules.device.init.RegistriesDevice;


import java.util.List;
import java.util.stream.Collectors;

public class WeldingRecipeMaker {

  public static List<WeldingRecipeWrapper> getRecipes() {
    return RegistriesDevice.QUERN.recipes()
        .stream()
        .map(WeldingRecipeWrapper::new)
        .collect(Collectors.toList());
  }
}
