package su.terrafirmagreg.modules.wood.object.inventory.capability;

import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ILockableContainer;


import org.jetbrains.annotations.NotNull;

public class InventoryWoodLargeChest extends InventoryLargeChest {

  private final ILockableContainer upperChest;
  private final ILockableContainer lowerChest;

  public InventoryWoodLargeChest(String nameIn, ILockableContainer upperChestIn,
          ILockableContainer lowerChestIn) {
    super(nameIn, upperChestIn, lowerChestIn);
    this.upperChest = upperChestIn;
    this.lowerChest = lowerChestIn;
  }

  @Override
  public boolean isItemValidForSlot(int index, @NotNull ItemStack stack) {
    if (index >= upperChest.getSizeInventory()) {
      return lowerChest.isItemValidForSlot(index, stack);
    } else {
      return upperChest.isItemValidForSlot(index, stack);
    }
  }
}
