package su.terrafirmagreg.modules.device.content.gui;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.base.gui.inventory.spi.BaseGuiContainer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiLogPile extends BaseGuiContainer {

  public static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/small_inventory.png");

  public GuiLogPile(Container container, InventoryPlayer playerInv) {
    super(container, playerInv, BACKGROUND);
  }
}
