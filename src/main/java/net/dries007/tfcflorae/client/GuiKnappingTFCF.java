package net.dries007.tfcflorae.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.client.gui.GuiKnapping;

public class GuiKnappingTFCF extends GuiKnapping {

  private KnappingType type = null;
  private ResourceLocation backgroundTexture;

  public GuiKnappingTFCF(Container container, EntityPlayer player, KnappingType type, ResourceLocation buttonTexture) {
    super(container, player, type, buttonTexture);
    this.type = type;
  }

  public GuiKnappingTFCF(Container container, EntityPlayer player, KnappingType type, ResourceLocation buttonTexture, ResourceLocation backgroundTexture) {
    super(container, player, type, buttonTexture);
    this.type = type;
    this.backgroundTexture = backgroundTexture;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    if (type == KnappingType.MUD) {
      GlStateManager.color(1, 1, 1, 1);
      if (type == KnappingType.MUD) {
        mc.getTextureManager().bindTexture(backgroundTexture);
      }
      for (GuiButton button : buttonList) {
        if (!button.visible) {
          Gui.drawModalRectWithCustomSizedTexture(button.x, button.y, 0, 0, 16, 16, 16, 16);
        }
      }
    }
  }
}
