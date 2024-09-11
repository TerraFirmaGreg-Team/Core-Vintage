package net.dries007.tfc.objects.items;

import net.minecraft.item.ItemStack;


import org.jetbrains.annotations.NotNull;


import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;


import tfcflorae.util.OreDictionaryHelper;

@SuppressWarnings("WeakerAccess")
//public class ItemMiscTFCF extends ItemMisc
public class ItemMiscTFCF extends ItemTFCF implements ICapabilitySize {

  private final Size size;
  private final Weight weight;

  public ItemMiscTFCF(Size size, Weight weight, Object... oreNameParts) {
    this(size, weight);

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {
        OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        OreDictionaryHelper.register(this, obj);
      }
    }
  }

  public ItemMiscTFCF(Size size, Weight weight) {
    this.size = size;
    this.weight = weight;
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return weight;
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return size;
  }
}
