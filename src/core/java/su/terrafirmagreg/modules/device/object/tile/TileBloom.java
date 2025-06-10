package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.framework.manager.registry.base.tile.spi.BaseTileInventory;

import net.minecraft.item.ItemStack;

public class TileBloom extends BaseTileInventory {

  public TileBloom() {
    super(1);

  }

  public void setBloom(ItemStack stack) {

    this.inventory.setStackInSlot(0, stack);
  }
}
