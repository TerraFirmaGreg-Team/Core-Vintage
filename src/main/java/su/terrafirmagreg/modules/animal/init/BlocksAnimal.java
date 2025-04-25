package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.animal.object.block.BlockNestBox;

import java.util.function.Supplier;

public final class BlocksAnimal {

  public static Supplier<BlockNestBox> NEST_BOX;

  public static void onRegister(IRegistryRegistrar registrar) {
    NEST_BOX = registrar.addBlock(new BlockNestBox());
  }

}
