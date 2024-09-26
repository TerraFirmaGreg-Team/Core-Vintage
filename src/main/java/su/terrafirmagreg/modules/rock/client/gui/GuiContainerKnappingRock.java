package su.terrafirmagreg.modules.rock.client.gui;

import net.dries007.tfc.api.recipes.knapping.KnappingTypes;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import su.terrafirmagreg.modules.core.client.gui.GuiContainerKnapping;

public class GuiContainerKnappingRock extends GuiContainerKnapping {

  public GuiContainerKnappingRock(Container container, InventoryPlayer inventoryPlayer, ResourceLocation buttonTexture) {
    super(container, inventoryPlayer, KnappingTypes.STONE, buttonTexture);
  }
}
