package su.terrafirmagreg.modules.core.capabilities.fluid;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.IFluidHandler;


import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public final class CapabilityFluid {

  public static final ResourceLocation KEY = ModUtils.resource("fluid_capability");


  public static IFluidHandler get(ItemStack stack) {
    return stack.getCapability(FLUID_HANDLER_CAPABILITY, null);
  }

  public static boolean has(ItemStack stack) {
    return stack.hasCapability(FLUID_HANDLER_CAPABILITY, null);
  }

}
