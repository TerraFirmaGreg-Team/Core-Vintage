package su.terrafirmagreg.modules.soil.object.container;

import su.terrafirmagreg.modules.core.object.container.ContainerBaseKnapping;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.recipes.knapping.KnappingTypes;

public class ContainerKnappingMud extends ContainerBaseKnapping {

  public ContainerKnappingMud(InventoryPlayer playerInv, ItemStack stack) {
    super(KnappingTypes.MUD, playerInv, stack);
  }
}
