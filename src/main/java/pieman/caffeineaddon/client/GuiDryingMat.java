package pieman.caffeineaddon.client;

import su.terrafirmagreg.api.base.gui.BaseGuiContainerTile;
import su.terrafirmagreg.api.util.GameUtils;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;


import net.dries007.tfc.objects.te.TEDryingMat;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.data.Constants.MODID_CAFFEINEADDON;

public class GuiDryingMat extends BaseGuiContainerTile<TEDryingMat> {

  private static final ResourceLocation BG_TEXTURE = new ResourceLocation(MODID_CAFFEINEADDON, "textures/gui/single_inventory.png");
  private final String translationKey;

  public GuiDryingMat(Container container, InventoryPlayer playerInv, TEDryingMat tile, String translationKey) {
    super(container, playerInv, tile, BG_TEXTURE);
    this.translationKey = translationKey;
  }

  @Override
  protected void renderHoveredToolTip(int mouseX, int mouseY) {
    super.renderHoveredToolTip(mouseX, mouseY);

    int relX = mouseX - guiLeft;
    int relY = mouseY - guiTop;
    int progress = (int) Math.min(100 * tile.getProgress(), 100);

    List<String> tooltip = new ArrayList<>();
    if (relX >= 79 && relY >= 51 && relX < 97 && relY < 57) {
      tooltip.add(progress + "%");
    }
    this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    if (GameUtils.isModLoaded("jei")) {
      drawTexturedModalRect(guiLeft + 103, guiTop + 35, 176, 0, 9, 14);
    }
    int progress = (int) Math.min(18 * tile.getProgress(), 18);
    if (tile.getProgress() > 0) {
      drawTexturedModalRect(guiLeft + 79, guiTop + 52, 185, 2, 18, 2);
      drawTexturedModalRect(guiLeft + 79, guiTop + 52, 185, 0, progress, 2);
    }
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    String name = I18n.format(translationKey + ".name");
    fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);

    String outputName = tile.getOutputName();
    fontRenderer.drawString(outputName, xSize / 2 - fontRenderer.getStringWidth(outputName) / 2, 23, 0x404040);

    String date = tile.getSealedDate();
    fontRenderer.drawString(date, xSize / 2 - fontRenderer.getStringWidth(date) / 2, 57, 0x404040);

    fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
  }
}
