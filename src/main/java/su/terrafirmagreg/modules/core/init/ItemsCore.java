package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.modules.core.objects.items.ItemDebug;

public final class ItemsCore {

    public static ItemDebug WAND;

    public static void onRegister(Registry registry) {

        //==== Other =================================================================================================//

        registry.registerItem(WAND = new ItemDebug(), "wand");
    }
}
