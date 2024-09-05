package su.terrafirmagreg.modules.rock.client.gui;

import su.terrafirmagreg.modules.core.client.gui.GuiContainerKnapping;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;


import net.dries007.tfc.api.recipes.knapping.KnappingTypes;

public class GuiContainerKnappingRock extends GuiContainerKnapping {

  public GuiContainerKnappingRock(Container container, InventoryPlayer inventoryPlayer,
      ResourceLocation buttonTexture) {
    super(container, inventoryPlayer, KnappingTypes.STONE, buttonTexture);
  }
}
