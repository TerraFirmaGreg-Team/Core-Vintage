package net.dries007.tfc.objects.items;

import net.minecraft.item.ItemStack;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.util.OreDictionaryHelper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;

@SuppressWarnings("WeakerAccess")
@ParametersAreNonnullByDefault
public class ItemPowder extends ItemTFC {

  private static final EnumMap<Powder, ItemPowder> MAP = new EnumMap<>(Powder.class);
  private final Powder powder;

  public ItemPowder(Powder powder) {
    this.powder = powder;
    if (MAP.put(powder, this) != null) {throw new IllegalStateException("There can only be one.");}
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

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.SMALL; // Stored everywhere
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.VERY_LIGHT; // Stacksize = 64
  }

  @Nonnull
  public Powder getPowder() {
    return powder;
  }
}
