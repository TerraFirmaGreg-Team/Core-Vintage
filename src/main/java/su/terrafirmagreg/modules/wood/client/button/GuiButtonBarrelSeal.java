package su.terrafirmagreg.modules.wood.client.button;

import su.terrafirmagreg.api.base.gui.component.button.BaseGuiButton;
import su.terrafirmagreg.api.base.gui.component.button.IButtonTooltip;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.client.gui.GuiWoodBarrel;
import su.terrafirmagreg.modules.wood.objects.tiles.TileWoodBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;


import org.jetbrains.annotations.NotNull;

public class GuiButtonBarrelSeal extends BaseGuiButton implements IButtonTooltip {

  private final TileWoodBarrel tile;

  public GuiButtonBarrelSeal(TileWoodBarrel tile, int buttonId, int guiTop, int guiLeft) {
    super(buttonId, guiLeft + 123, guiTop + 35, 20, 20, "");
    this.tile = tile;
  }

  @Override
  public String getTooltip() {
    return ModUtils.localize("tooltip", "") + (tile.isSealed() ? "barrel_unseal" : "barrel_seal");
  }

  @Override
  public boolean hasTooltip() {
    return true;
  }

  @Override
  public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
    if (this.visible) {
      GlStateManager.color(1, 1, 1, 1);
      mc.getTextureManager().bindTexture(GuiWoodBarrel.BACKGROUND);
      hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
          && mouseY < this.y + this.height;
      if (tile.isSealed()) {
        Gui.drawModalRectWithCustomSizedTexture(x, y, 236, 0, 20, 20, 256, 256);
      } else {
        Gui.drawModalRectWithCustomSizedTexture(x, y, 236, 20, 20, 20, 256, 256);
      }
      mouseDragged(mc, mouseX, mouseY);
    }
  }
}
