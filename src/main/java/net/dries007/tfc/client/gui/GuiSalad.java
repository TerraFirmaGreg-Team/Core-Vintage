package net.dries007.tfc.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFC;

public class GuiSalad extends GuiContainerTFC {

  private static final ResourceLocation BACKGROUND = new ResourceLocation(TFC, "textures/gui/salad.png");

  public GuiSalad(Container container, InventoryPlayer playerInv) {
    super(container, playerInv, BACKGROUND);
  }
}
