package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.block.BlockAggregate;
import su.terrafirmagreg.modules.core.objects.block.BlockDebug;
import su.terrafirmagreg.modules.core.objects.block.BlockIngotPile;
import su.terrafirmagreg.modules.core.objects.block.BlockJackOLantern;
import su.terrafirmagreg.modules.core.objects.block.BlockPuddle;
import su.terrafirmagreg.modules.core.objects.block.BlockThatch;

public final class BlocksCore {

  public static BlockDebug DEBUG;
  public static BlockPuddle PUDDLE; //TODO: отключить регистрацию itemBlock
  public static BlockThatch THATCH;
  public static BlockAggregate AGGREGATE;
  public static BlockIngotPile INGOT_PILE;
  public static BlockJackOLantern JACK_O_LANTERN;

  public static void onRegister(RegistryManager registry) {

    //==== Other =================================================================================================//

    DEBUG = registry.block(new BlockDebug());
    PUDDLE = registry.block(new BlockPuddle());
    THATCH = registry.block(new BlockThatch());
    AGGREGATE = registry.block(new BlockAggregate());
    INGOT_PILE = registry.block(new BlockIngotPile());

  }

}

