package net.dries007.tfc.objects.items.rock;

import net.minecraft.item.ItemStack;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class ItemBrickTFC extends ItemTFC implements IRockObject {

  private static final Map<Rock, ItemBrickTFC> MAP = new HashMap<>();
  private final Rock rock;

  public ItemBrickTFC(Rock rock) {
    this.rock = rock;
    if (MAP.put(rock, this) != null) {throw new IllegalStateException("There can only be one.");}
    setMaxDamage(0);
    OreDictionaryHelper.register(this, "brick");
    OreDictionaryHelper.register(this, "brick", rock.getRockCategory());
  }

  public static ItemBrickTFC get(Rock ore) {
    return MAP.get(ore);
  }

  public static ItemStack get(Rock ore, int amount) {
    return new ItemStack(MAP.get(ore), amount);
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.SMALL; // Stored everywhere
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.LIGHT; // Stacksize = 32
  }

  @Nonnull
  @Override
  public Rock getRock(ItemStack stack) {
    return rock;
  }

  @Nonnull
  @Override
  public RockCategory getRockCategory(ItemStack stack) {
    return rock.getRockCategory();
  }
}
