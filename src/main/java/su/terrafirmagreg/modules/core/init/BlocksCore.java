package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.object.block.BlockDebug;
import su.terrafirmagreg.modules.core.object.block.BlockFireBricks;
import su.terrafirmagreg.modules.core.object.block.BlockPuddle;
import su.terrafirmagreg.modules.core.object.block.BlockThatch;

import java.util.function.Supplier;

public final class BlocksCore {


  public static Supplier<BlockDebug> DEBUG;
  public static Supplier<BlockPuddle> PUDDLE;
  public static Supplier<BlockFireBricks> FIRE_BRICKS;
  public static Supplier<BlockThatch> THATCH;

  public static void onRegister(IRegistryManager registry) {

    DEBUG = registry.block(new BlockDebug());
    PUDDLE = registry.block(new BlockPuddle());
    FIRE_BRICKS = registry.block(new BlockFireBricks());
    THATCH = registry.block(new BlockThatch());
  }
}
