package su.terrafirmagreg.modules.core.capabilities.metal;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

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

}
