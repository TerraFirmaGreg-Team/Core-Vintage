package net.dries007.tfc.client.button;

import su.terrafirmagreg.api.base.client.gui.button.api.IButtonTooltip;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import javax.annotation.Nonnull;

import static net.dries007.tfc.client.gui.GuiLargeVessel.LARGE_VESSEL_BACKGROUND;
import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFCF;

import net.dries007.tfc.objects.te.TEUrn;

public class GuiButtonUrn extends GuiButtonTFC implements IButtonTooltip {

  private final TEUrn tile;

  public GuiButtonUrn(TEUrn tile, int buttonId, int guiTop, int guiLeft) {
    super(buttonId, guiLeft + 123, guiTop + 35, 20, 20, "");
    this.tile = tile;
  }

  @Override
  public String getTooltip() {
    return TFCF + ".tooltip." + (tile.isSealed() ? "urn_unseal" : "urn_seal");
  }

  @Override
  public boolean hasTooltip() {
    return true;
  }

  @Override
  public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
    if (this.visible) {
      GlStateManager.color(1, 1, 1, 1);
      mc.getTextureManager().bindTexture(LARGE_VESSEL_BACKGROUND);
      hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
      if (tile.isSealed()) {
        drawModalRectWithCustomSizedTexture(x, y, 236, 0, 20, 20, 256, 256);
      } else {
        drawModalRectWithCustomSizedTexture(x, y, 236, 20, 20, 20, 256, 256);
      }
      mouseDragged(mc, mouseX, mouseY);
    }
  }
}
