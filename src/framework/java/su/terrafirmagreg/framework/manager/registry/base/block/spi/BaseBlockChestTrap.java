package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import net.minecraft.block.material.Material;


public abstract class BaseBlockChestTrap extends BaseBlockChest {


  public BaseBlockChestTrap() {
    super(Type.TRAP, Settings.of(Material.WOOD));
  }


}
