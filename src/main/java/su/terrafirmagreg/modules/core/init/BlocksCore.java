package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.object.block.BlockDebug;

import java.util.function.Supplier;

public final class BlocksCore {

  public static Supplier<BlockDebug> DEBUG;

  public static void onRegister(IRegistryManager registry) {

    DEBUG = registry.block(new BlockDebug());
  }
}
