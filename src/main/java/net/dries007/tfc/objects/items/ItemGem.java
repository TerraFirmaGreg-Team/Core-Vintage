package net.dries007.tfc.objects.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import mcp.MethodsReturnNonnullByDefault;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.util.OreDictionaryHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemGem extends ItemTFC {

  private static final EnumMap<Gem, ItemGem> MAP = new EnumMap<>(Gem.class);
  public final Gem gem;

  public ItemGem(Gem gem) {
    this.gem = gem;
    if (MAP.put(gem, this) != null) {throw new IllegalStateException("There can only be one.");}
    setMaxDamage(0);
    setHasSubtypes(true);
    for (Gem.Grade grade : Gem.Grade.values()) {
      if (grade == Gem.Grade.NORMAL) {
        OreDictionaryHelper.registerMeta(this, grade.ordinal(), "gem", gem);
      } else {
        OreDictionaryHelper.registerMeta(this, grade.ordinal(), "gem", grade, gem);
      }
      OreDictionaryHelper.registerMeta(this, grade.ordinal(), "gem", grade);
    }
  }

  public static ItemGem get(Gem gem) {
    return MAP.get(gem);
  }

  public static ItemStack get(Gem ore, Gem.Grade grade, int amount) {
    return new ItemStack(MAP.get(ore), amount, grade.ordinal());
  }

  @Override
  public String getTranslationKey(ItemStack stack) {
    Gem.Grade grade = getGradeFromStack(stack);
    if (grade != null) {
      return super.getTranslationKey(stack) + "." + grade.name().toLowerCase();
    }
    return super.getTranslationKey(stack);
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (isInCreativeTab(tab)) {
      for (Gem.Grade grade : Gem.Grade.values()) {
        items.add(new ItemStack(this, 1, grade.ordinal()));
      }
    }
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.SMALL; // Stored anywhere
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.VERY_LIGHT; // Stacksize = 64
  }

  @Nullable
  private Gem.Grade getGradeFromStack(ItemStack stack) {
    return Gem.Grade.valueOf(stack.getItemDamage());
  }
}
