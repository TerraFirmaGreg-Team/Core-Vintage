package su.terrafirmagreg.api.base.tile;

import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemStackHandler;

/**
 * A merge of {@link BaseTileInventory} and {@link BaseTileTickable}
 */
public abstract class BaseTileTickableInventory extends BaseTileInventory implements ITickable {

  protected boolean needsClientUpdate;

  protected BaseTileTickableInventory(int inventorySize) {
    super(inventorySize);

  }

  protected BaseTileTickableInventory(ItemStackHandler inventory) {
    super(inventory);

  }

  @Override
  public void update() {
    if (!world.isRemote && needsClientUpdate) {
      // Batch sync requests into single packets rather than sending them every time markForSync is called
      needsClientUpdate = false;
      super.markForSync();
    }
  }

  @Override
  public void markForSync() {
    needsClientUpdate = true;
  }
}
