package su.terrafirmagreg.modules.core.capabilities.sharpness;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

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

}
