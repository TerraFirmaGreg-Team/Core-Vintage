package su.terrafirmagreg.modules.device.feature.sharpness.capability;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.data.ingredient.IIngredient;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.objects.items.ItemRopeJavelin;
import net.dries007.tfc.objects.items.metal.ItemMetalSword;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class CapabilitySharpness {

  public static final ResourceLocation KEY = ModUtils.resource("sharpness_capability");

  @CapabilityInject(ICapabilitySharpness.class)
  public static final Capability<ICapabilitySharpness> CAPABILITY = ModUtils.getNull();

  public static void register() {
    CapabilityManager.INSTANCE.register(ICapabilitySharpness.class, new CapabilityStorageSharpness(), CapabilityProviderSharpness::new);

  }

  public static ICapabilitySharpness get(ItemStack itemStack) {
    return itemStack.getCapability(CAPABILITY, null);
  }

  public static boolean has(ItemStack itemStack) {
    return itemStack.hasCapability(CAPABILITY, null);
  }

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    for (var entry : Handler.CUSTOM_ITEMS.entrySet()) {
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

  public static class Handler {

    //Used inside CT, set custom IItemHeat for items outside TFC
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();

    public static void init() {
    }

  }
}
