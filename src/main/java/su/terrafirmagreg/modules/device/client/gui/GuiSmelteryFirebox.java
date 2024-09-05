package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.objects.tiles.TileSmelteryFirebox;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;


import net.dries007.tfc.client.gui.GuiContainerTE;

public class GuiSmelteryFirebox extends GuiContainerTE<TileSmelteryFirebox> {

  private static final ResourceLocation BACKGROUND = ModUtils.resource(
      "textures/gui/container/smeltery_firebox.png");

  public GuiSmelteryFirebox(Container container, InventoryPlayer playerInv,
      TileSmelteryFirebox tile) {
    super(container, playerInv, tile, BACKGROUND);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    // Draw elements
    // if on fire
    if (tile.getField(1) > 0) {
      mc.getTextureManager().bindTexture(BACKGROUND);
      for (int i = 0; i < 4; i++) {
        this.drawTexturedModalRect(guiLeft + 54 + (i * 18), guiTop + 12, 0, 166, 14, 14);
      }
    }
  }
}
