package net.dries007.tfc.objects.items.food;

import su.terrafirmagreg.api.base.object.item.spi.BaseItemFood;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityProviderFood;
import su.terrafirmagreg.modules.core.capabilities.food.FoodHeatHandler;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.IItemFoodTFC;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.agriculture.Food;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class ItemFoodTFC extends BaseItemFood implements ICapabilitySize, IItemFoodTFC {

  private static final Map<Food, ItemFoodTFC> MAP = new HashMap<>();
  protected final Food food;

  public ItemFoodTFC(@Nonnull Food food) {
    super(0, 0, food.getCategory() == Food.Category.MEAT || food.getCategory() == Food.Category.COOKED_MEAT);
    this.food = food;
    if (MAP.put(food, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }

    // Use "category" here as to not conflict with actual items, i.e. grain
    OreDictionaryHelper.register(this, "category", food.getCategory());
    if (food.getOreDictNames() != null) {
      for (Object name : food.getOreDictNames()) {
        OreDictionaryHelper.register(this, name);
      }
    }
  }

  public static ItemFoodTFC get(Food food) {
    return MAP.get(food);
  }

  public static ItemStack get(Food food, int amount) {
    return new ItemStack(MAP.get(food), amount);
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (this.isInCreativeTab(tab)) {
      // Makes creative items not decay (like JEI)
      ItemStack stack = new ItemStack(this);
      ICapabilityFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
      if (cap != null) {
        cap.setNonDecaying();
      }
      items.add(stack);
    }
  }

  @Override
  public int getItemStackLimit(ItemStack stack) {
    return getStackSize(stack);
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack stack) {
    return Size.SMALL;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack stack) {
    return Weight.VERY_LIGHT;
  }

  @Override
  public ICapabilityProvider getCustomFoodHandler() {
    return food.isHeatable() ? new FoodHeatHandler(null, food) : new CapabilityProviderFood(null, food);
  }
}
