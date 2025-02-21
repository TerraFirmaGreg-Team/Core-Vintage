package su.terrafirmagreg.modules.core.capabilities.size;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public final class CapabilitySize {

  public static final ResourceLocation KEY = ModUtils.resource("size_capability");

  @CapabilityInject(ICapabilitySize.class)
  public static final Capability<ICapabilitySize> CAPABILITY = ModUtils.getNull();

  public static void register() {
    CapabilityManager.INSTANCE.register(ICapabilitySize.class, new CapabilityStorageSize(), CapabilityProviderSize::new);
  }

  public static ICapabilitySize get(ItemStack itemStack) {
    return itemStack.getCapability(CAPABILITY, null);
  }

  public static boolean has(ItemStack itemStack) {
    return itemStack.hasCapability(CAPABILITY, null);
  }

  /**
   * Checks if an item is of a given size and weight
   */
  public static boolean checkItemSize(ItemStack stack, Size size, Weight weight) {
    ICapabilitySize cap = get(stack);
    if (cap != null) {
      return cap.getWeight(stack) == weight && cap.getSize(stack) == size;
    }
    return false;
  }

}
