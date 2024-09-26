package su.terrafirmagreg.modules.metal.plugin.jei.anvil;

import su.terrafirmagreg.modules.metal.init.RegistriesMetal;

import java.util.List;
import java.util.stream.Collectors;

public class MetalAnvilRecipeMaker {

  public static List<MetalAnvilRecipeWrapper> getRecipes() {
    return RegistriesMetal.ANVIL.recipes()
                                .stream()
                                .map(MetalAnvilRecipeWrapper::new)
                                .collect(Collectors.toList());
  }
}
