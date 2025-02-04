package su.terrafirmagreg.modules.core.capabilities.sharpness;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.metal.ItemMetalSword;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import net.dries007.tfcthings.items.ItemRopeJavelin;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CapabilityHandlerSharpness {

  //Used inside CT, set custom IItemHeat for items outside TFC
  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();

  public static void init() {
  }

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    for (var entry : CUSTOM_ITEMS.entrySet()) {
      if (entry.getKey().testIgnoreCount(stack)) {
        return entry.getValue().get();
      }
    }
    if (stack.getItem() instanceof ItemMetalTool) {
      return new CapabilityProviderSharpness(stack);
    }
    if (stack.getItem() instanceof ItemMetalSword) {
      return new CapabilityProviderSharpness(stack);
    }
    if (stack.getItem() instanceof ItemRopeJavelin) {
      return new CapabilityProviderSharpness(stack);
    }
    return null;
  }
}
