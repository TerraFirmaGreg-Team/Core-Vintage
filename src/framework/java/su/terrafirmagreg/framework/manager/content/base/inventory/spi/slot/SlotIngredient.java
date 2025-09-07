package su.terrafirmagreg.framework.manager.content.base.inventory.spi.slot;

import su.terrafirmagreg.api.util.StackUtils;

import net.minecraft.inventory.IInventory;

public class SlotIngredient extends SlotFiltered {

  public SlotIngredient(IInventory inventoryIn, int index, int xPosition, int yPosition, Object obj) {
    super(inventoryIn, index, xPosition, yPosition, StackUtils.asIngredient(obj));
  }

}
