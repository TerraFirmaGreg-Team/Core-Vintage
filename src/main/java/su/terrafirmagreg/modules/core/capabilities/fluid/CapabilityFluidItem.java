package su.terrafirmagreg.modules.core.capabilities.fluid;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;


import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;

public final class CapabilityFluidItem {

  public static final ResourceLocation KEY = ModUtils.resource("fluid_item_capability");


  public static IFluidHandlerItem get(ItemStack stack) {
    return stack.getCapability(FLUID_HANDLER_ITEM_CAPABILITY, null);
  }

  public static boolean has(ItemStack stack) {
    return stack.hasCapability(FLUID_HANDLER_ITEM_CAPABILITY, null);
  }

}

