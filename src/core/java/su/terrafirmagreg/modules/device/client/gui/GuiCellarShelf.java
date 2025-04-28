package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.base.client.gui.inventory.spi.BaseGuiContainerTile;
import su.terrafirmagreg.api.data.Unicode;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.object.tile.TileCellarShelf;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiCellarShelf extends BaseGuiContainerTile<TileCellarShelf> {

  public static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/cellar_shelf.png");


  public GuiCellarShelf(Container container, InventoryPlayer playerInv, TileCellarShelf tile) {
    super(container, playerInv, tile, BACKGROUND);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);

    if (mouseX >= guiLeft + 5 && mouseX <= guiLeft + 15 && mouseY >= guiTop + 5
        && mouseY <= guiTop + 15) {
      List<String> infoText = new ArrayList<String>();
      float temperature = tile.getTemperature();

      if (temperature <= -1000) {
        infoText.add("[!] The shelf is not inside a cellar");
      } else {
        if (temperature < 0) {
          infoText.add("Temperature: below zero");
        } else {
          infoText.add("Temperature: " + String.format("%.2f", temperature) + Unicode.CELSIUS);
        }
      }

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    this.mc.getTextureManager().bindTexture(BACKGROUND);
    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
  }

}
