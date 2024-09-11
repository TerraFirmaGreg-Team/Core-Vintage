package su.terrafirmagreg.modules.wood.client.gui;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class GuiWoodSupplyCart extends GuiChest {

  public GuiWoodSupplyCart(Container container, InventoryPlayer playerInv,
          IInventory plowInventory) {
    super(playerInv, plowInventory);
  }

}
