package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.animal.object.block.BlockNestBox;

import java.util.function.Supplier;

public final class BlocksAnimal {

  public static Supplier<BlockNestBox> NEST_BOX;

  public static void onRegister(IRegistryManager registry) {
    NEST_BOX = registry.block(new BlockNestBox());
  }

}
