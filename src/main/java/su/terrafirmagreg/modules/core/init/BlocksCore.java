package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.object.block.BlockAggregate;
import su.terrafirmagreg.modules.core.object.block.BlockDebug;
import su.terrafirmagreg.modules.core.object.block.BlockFireBricks;
import su.terrafirmagreg.modules.core.object.block.BlockFireClay;
import su.terrafirmagreg.modules.core.object.block.BlockIngotPile;
import su.terrafirmagreg.modules.core.object.block.BlockJackOLantern;
import su.terrafirmagreg.modules.core.object.block.BlockPuddle;
import su.terrafirmagreg.modules.core.object.block.BlockThatch;

public final class BlocksCore {

  public static BlockDebug DEBUG;
  public static BlockPuddle PUDDLE; //TODO: отключить регистрацию itemBlock
  public static BlockThatch THATCH;
  public static BlockAggregate AGGREGATE;
  public static BlockIngotPile INGOT_PILE;
  public static BlockFireClay FIRE_CLAY;
  public static BlockFireBricks FIRE_BRICKS;
  public static BlockJackOLantern JACK_O_LANTERN;

  public static void onRegister(RegistryManager registryManager) {

    //==== Other =================================================================================================//

    DEBUG = registryManager.block(new BlockDebug());
    PUDDLE = registryManager.block(new BlockPuddle());
    THATCH = registryManager.block(new BlockThatch());
    AGGREGATE = registryManager.block(new BlockAggregate());
    INGOT_PILE = registryManager.block(new BlockIngotPile());
    FIRE_CLAY = registryManager.block(new BlockFireClay());
    FIRE_BRICKS = registryManager.block(new BlockFireBricks());


  }

}

