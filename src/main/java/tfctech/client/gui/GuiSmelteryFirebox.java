package tfctech.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.client.gui.GuiContainerTE;
import tfctech.objects.tileentities.TESmelteryFirebox;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

public class GuiSmelteryFirebox extends GuiContainerTE<TESmelteryFirebox> {

  private static final ResourceLocation FIREBOX_BACKGROUND = new ResourceLocation(TFCTECH, "textures/gui/smeltery_firebox.png");

  public GuiSmelteryFirebox(Container container, InventoryPlayer playerInv, TESmelteryFirebox tile) {
    super(container, playerInv, tile, FIREBOX_BACKGROUND);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    // Draw elements
    // if on fire
    if (tile.getField(1) > 0) {
      mc.getTextureManager().bindTexture(FIREBOX_BACKGROUND);
      for (int i = 0; i < 4; i++) {
        this.drawTexturedModalRect(guiLeft + 54 + (i * 18), guiTop + 12, 0, 166, 14, 14);
      }
    }
  }
}
