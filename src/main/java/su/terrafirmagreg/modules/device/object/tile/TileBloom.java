package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.tile.BaseTileInventory;

import net.minecraft.item.ItemStack;

public class TileBloom extends BaseTileInventory {

  public TileBloom() {
    super(1);

  }

  public void setBloom(ItemStack stack) {

    inventory.setStackInSlot(0, stack);
  }
}
