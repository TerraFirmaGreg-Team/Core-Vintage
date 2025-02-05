package su.terrafirmagreg.modules.core.capabilities.metal;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public final class CapabilityMetal {

  public static final ResourceLocation KEY = ModUtils.resource("metal_capability");


  @CapabilityInject(ICapabilityMetal.class)
  public static final Capability<ICapabilityMetal> CAPABILITY = ModUtils.getNull();

  public static void register() {
    CapabilityManager.INSTANCE.register(ICapabilityMetal.class, new CapabilityStorageMetal(), CapabilityProviderMetal::new);
  }

  public static ICapabilityMetal get(ItemStack itemStack) {
    return itemStack.getCapability(CAPABILITY, null);
  }

  public static boolean has(ItemStack itemStack) {
    return itemStack.hasCapability(CAPABILITY, null);
  }

  /**
   * Gets the IMetalItem instance from an itemstack, either via capability or via interface
   *
   * @param stack The stack
   * @return The IMetalItem if it exists, or null if it doesn't
   */
  @Nullable
  public static ICapabilityMetal getMetalItem(ItemStack stack) {
    if (!stack.isEmpty()) {
      ICapabilityMetal metal = CapabilityMetal.get(stack);
      if (metal != null) {
        return metal;
      } else if (stack.getItem() instanceof ICapabilityMetal metalItem) {
        return metalItem;
      } else if (stack.getItem() instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof ICapabilityMetal metalItem) {
        return metalItem;
      }
    }
    return null;
  }


}
