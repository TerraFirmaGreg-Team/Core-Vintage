package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfc.api.types.IFruitTree;

import java.util.HashMap;
import java.util.Map;

public class ItemFruitPole extends ItemMisc {

  private static final Map<IFruitTree, ItemFruitPole> MAP = new HashMap<>();

  public ItemFruitPole(IFruitTree tree) {
    super(Size.SMALL, Weight.MEDIUM);
    if (MAP.put(tree, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
  }

  public static ItemFruitPole get(IFruitTree tree) {
    return MAP.get(tree);
  }
}
