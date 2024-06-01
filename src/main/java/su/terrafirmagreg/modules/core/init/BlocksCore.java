package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.blocks.BlockAggregate;
import su.terrafirmagreg.modules.core.objects.blocks.BlockDebug;
import su.terrafirmagreg.modules.core.objects.blocks.BlockJackOLantern;
import su.terrafirmagreg.modules.core.objects.blocks.BlockPuddle;
import su.terrafirmagreg.modules.core.objects.blocks.BlockThatch;

public final class BlocksCore {

    public static BlockDebug DEBUG;
    public static BlockPuddle PUDDLE; //TODO: отключить регистрацию itemBlock
    public static BlockThatch THATCH;
    public static BlockAggregate AGGREGATE;
    public static BlockJackOLantern JACK_O_LANTERN;

    public static void onRegister(RegistryManager registry) {

        //==== Other =================================================================================================//

        DEBUG = registry.block(new BlockDebug());
        PUDDLE = registry.block(new BlockPuddle());
        THATCH = registry.block(new BlockThatch());
        AGGREGATE = registry.block(new BlockAggregate());

    }

}
