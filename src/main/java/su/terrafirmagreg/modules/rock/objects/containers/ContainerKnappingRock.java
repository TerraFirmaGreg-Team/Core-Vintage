package su.terrafirmagreg.modules.rock.objects.containers;

import su.terrafirmagreg.modules.core.objects.container.ContainerBaseKnapping;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.recipes.knapping.KnappingTypes;

public class ContainerKnappingRock extends ContainerBaseKnapping {

  public ContainerKnappingRock(InventoryPlayer playerInv, ItemStack stack) {
    super(KnappingTypes.STONE, playerInv, stack);
  }
}
