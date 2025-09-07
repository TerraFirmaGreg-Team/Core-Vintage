package su.terrafirmagreg.modules.device.content.tile;

import su.terrafirmagreg.framework.manager.content.base.tile.spi.BaseTileInventory;

import net.minecraft.item.ItemStack;

public class TileBloom extends BaseTileInventory {

  public TileBloom() {
    super(1);

  }

  public void setBloom(ItemStack stack) {

    this.inventory.setStackInSlot(0, stack);
  }
}
