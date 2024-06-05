package su.terrafirmagreg.modules.device.plugin.jei.quern;

import su.terrafirmagreg.modules.device.init.RegistryDevice;


import java.util.List;
import java.util.stream.Collectors;

public class QuernRecipeMaker {

    public static List<QuernRecipeWrapper> getRecipes() {
        return RegistryDevice.QUERN.recipes()
                .stream()
                .map(QuernRecipeWrapper::new)
                .collect(Collectors.toList());
    }
}
