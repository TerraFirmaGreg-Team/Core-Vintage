package net.dries007.tfc.objects.items;

import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.item.ItemStack;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
//public class ItemMiscTFCF extends ItemMisc
public class ItemMiscTFCF extends ItemTFCF implements ICapabilitySize {

  private final Size size;
  private final Weight weight;

  public ItemMiscTFCF(Size size, Weight weight, Object... oreNameParts) {
    this(size, weight);

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {
        net.dries007.tfc.util.OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        OreDictionaryHelper.register(this, obj);
      }
    }
  }

  public ItemMiscTFCF(Size size, Weight weight) {
    this.size = size;
    this.weight = weight;
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack stack) {
    return size;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack stack) {
    return weight;
  }
}
