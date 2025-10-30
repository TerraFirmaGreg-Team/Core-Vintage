package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.animal.content.block.BlockNestBox;

public final class BlocksAnimal {

  public static BlockNestBox NEST_BOX;

  public static void onRegister(IContentRegistrar registrar) {

    NEST_BOX = registrar.addBlock("nest_box", new BlockNestBox());
  }

}
