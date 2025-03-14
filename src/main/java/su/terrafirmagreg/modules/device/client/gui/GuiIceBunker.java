package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.base.client.gui.inventory.spi.BaseGuiContainerTile;
import su.terrafirmagreg.api.data.Unicode;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.object.tile.TileIceBunker;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiIceBunker extends BaseGuiContainerTile<TileIceBunker> {

  private static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/ice_bunker.png");

  public GuiIceBunker(Container container, InventoryPlayer playerInv, TileIceBunker tile) {
    super(container, playerInv, tile, BACKGROUND);
    this.xSize = 176;
    this.ySize = 166;
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);

    if (mouseX >= guiLeft + 5 && mouseX <= guiLeft + 15 && mouseY >= guiTop + 5 && mouseY <= guiTop + 15) {
      List<String> infoText = new ArrayList<>();
      float temperature = tile.getTemperature();
      int coolant = tile.getCoolant();

      if (temperature <= -1000) { //TODO сделать перевод
        switch ((int) (temperature * 0.001f)) {
          case -1:
            infoText.add("[!] The cellar has a problem with walls or has no doors");
            break;
          case -2:
            infoText.add("[!] A block prevents the cellar from working correctly");
            break;
          case -3:
            infoText.add("[!] The cellar has no or more than one entrance");
            break;
        }
        infoText.add("Check the structure if the error will appear after 1 minute");
        infoText.add("or break and place the Ice Bunker block again");

      } else {
        if (temperature < 0) {
          infoText.add("Temperature: below zero");
        } else {
          infoText.add("Temperature: " + String.format("%.2f", temperature) + Unicode.CELSIUS);
        }
        infoText.add("Coolant: " + coolant + " units");
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
