package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.modules.core.objects.blocks.BlockDebug;

public final class BlocksCore {

    public static BlockDebug DEBUG;

    public static void onRegister(Registry registry) {

        //==== Other =================================================================================================//

        registry.registerBlock(DEBUG = new BlockDebug(), DEBUG.getItemBlock(), "debug");
    }

}
