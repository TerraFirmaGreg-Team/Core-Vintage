package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

@SuppressWarnings("WeakerAccess")

public class ItemPowder extends ItemTFC {

  private static final EnumMap<Powder, ItemPowder> MAP = new EnumMap<>(Powder.class);
  private final Powder powder;

  public ItemPowder(Powder powder) {
    this.powder = powder;
    if (MAP.put(powder, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
    setMaxDamage(0);
    OreDictionaryHelper.register(this, "dust", powder);
    if (powder == Powder.LAPIS_LAZULI) {
      OreDictionaryHelper.register(this, "dust", "lapis");
    }
  }

  public static ItemPowder get(Powder powder) {
    return MAP.get(powder);
  }

  public static ItemStack get(Powder powder, int amount) {
    return new ItemStack(MAP.get(powder), amount);
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.VERY_LIGHT; // Stacksize = 64
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.SMALL; // Stored everywhere
  }

  @NotNull
  public Powder getPowder() {
    return powder;
  }
}
