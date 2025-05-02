package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.core.object.block.BlockDebug;
import su.terrafirmagreg.modules.core.object.block.BlockFireBricks;
import su.terrafirmagreg.modules.core.object.block.BlockPuddle;
import su.terrafirmagreg.modules.core.object.block.BlockThatch;

public final class BlocksCore {


  public static BlockDebug DEBUG;
  public static BlockPuddle PUDDLE;
  public static BlockFireBricks FIRE_BRICKS;
  public static BlockThatch THATCH;

  public static void onRegister(IRegistryRegistrar registry) {

    DEBUG = registry.addBlock(new BlockDebug());
    PUDDLE = registry.addBlock(new BlockPuddle());
    FIRE_BRICKS = registry.addBlock(new BlockFireBricks());
    THATCH = registry.addBlock(new BlockThatch());
  }
}
