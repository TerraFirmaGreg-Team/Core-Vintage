package su.terrafirmagreg.modules.core.capabilities.forge;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class CapabilityHandlerForge {

  //Used inside CT, set custom IForgeable for items outside TFC
  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();


  public static void init() {

  }

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    Set<IIngredient<ItemStack>> itemItemSet = CUSTOM_ITEMS.keySet();
    for (IIngredient<ItemStack> ingredient : itemItemSet) {
      if (ingredient.testIgnoreCount(stack)) {
        return CUSTOM_ITEMS.get(ingredient).get();
      }
    }
    return null;
  }
}
