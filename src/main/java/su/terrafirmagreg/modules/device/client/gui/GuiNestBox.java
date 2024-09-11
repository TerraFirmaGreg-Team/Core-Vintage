package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.base.gui.BaseGuiContainer;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiNestBox extends BaseGuiContainer {

  public static final ResourceLocation BACKGROUND = ModUtils.resource(
          "textures/gui/container/small_inventory.png");

  public GuiNestBox(Container container, InventoryPlayer playerInv) {
    super(container, playerInv, BACKGROUND);
  }
}
