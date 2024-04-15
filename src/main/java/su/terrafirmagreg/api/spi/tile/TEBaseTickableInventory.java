package su.terrafirmagreg.api.spi.tile;

import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemStackHandler;

/**
 * A merge of {@link TEBaseInventory} and {@link TEBaseTickable}
 */
public class TEBaseTickableInventory extends TEBaseInventory implements ITickable {

    protected boolean needsClientUpdate;

    protected TEBaseTickableInventory(int inventorySize) {
        super(inventorySize);
    }

    protected TEBaseTickableInventory(ItemStackHandler inventory) {
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
