package net.dries007.tfc.objects.items.wood;

import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemLumberTFC extends ItemTFC {

  private static final Map<Tree, ItemLumberTFC> MAP = new HashMap<>();
  public final Tree wood;

  public ItemLumberTFC(Tree wood) {
    this.wood = wood;
    if (MAP.put(wood, this) != null) {throw new IllegalStateException("There can only be one.");}
    setMaxDamage(0);
    OreDictionaryHelper.register(this, "lumber");
    //noinspection ConstantConditions
    OreDictionaryHelper.register(this, "lumber", wood.getRegistryName().getPath());
  }

  public static ItemLumberTFC get(Tree wood) {
    return MAP.get(wood);
  }

  public static ItemStack get(Tree wood, int amount) {
    return new ItemStack(MAP.get(wood), amount);
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.SMALL;
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.VERY_LIGHT;
  }
}
