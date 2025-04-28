package su.terrafirmagreg.modules.core.capabilities.fluid;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidHandlerSidedCallback {

  boolean canFill(FluidStack resource, EnumFacing side);

  boolean canDrain(EnumFacing side);
}
