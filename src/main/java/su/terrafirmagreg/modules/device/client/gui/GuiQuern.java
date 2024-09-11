package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;


import net.dries007.tfc.client.gui.GuiContainerTFC;

public class GuiQuern extends GuiContainerTFC {

  public static final ResourceLocation BACKGROUND = ModUtils.resource(
          "textures/gui/container/small_inventory.png");

  public GuiQuern(Container container, InventoryPlayer playerInv) {
    super(container, playerInv, BACKGROUND);
  }
}
