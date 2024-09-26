package su.terrafirmagreg.modules.soil.client.gui;

import su.terrafirmagreg.modules.core.client.gui.GuiContainerKnapping;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.recipes.knapping.KnappingTypes;

public class GuiContainerKnappingMud extends GuiContainerKnapping {

  public GuiContainerKnappingMud(Container container, InventoryPlayer inventoryPlayer, ResourceLocation buttonTexture) {
    super(container, inventoryPlayer, KnappingTypes.MUD, buttonTexture);

  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

    if (type == KnappingTypes.MUD) {
      GlStateManager.color(1, 1, 1, 1);
      mc.getTextureManager().bindTexture(buttonTexture);

      for (GuiButton button : buttonList) {
        if (!button.visible) {
          Gui.drawModalRectWithCustomSizedTexture(button.x, button.y, 0, 0, 16, 16, 16, 16);
        }
      }
    }
  }
}
