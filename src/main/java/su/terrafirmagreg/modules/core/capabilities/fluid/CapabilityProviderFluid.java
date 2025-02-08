package su.terrafirmagreg.modules.core.capabilities.fluid;

import su.terrafirmagreg.framework.registry.api.provider.IProviderItemCapability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

import net.dries007.tfc.objects.fluids.capability.IFluidHandlerSidedCallback;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public class CapabilityProviderFluid implements IProviderItemCapability {

  public final boolean allowPartialFill;
  public final int capacity;
  public final Set<Fluid> whitelist;

  public CapabilityProviderFluid(int capacity, boolean allowPartialFill, Set<Fluid> whitelist) {
    this.capacity = capacity;
    this.allowPartialFill = allowPartialFill;
    this.whitelist = whitelist;
  }

  public static CapabilityProviderFluid of(int capacity, boolean allowPartialFill, String[] fluidNames) {
    return new CapabilityProviderFluid(capacity, allowPartialFill, Arrays.stream(fluidNames).map(FluidRegistry::getFluid).filter(Objects::nonNull)
      .collect(Collectors.toSet()));
  }

  public static CapabilityProviderFluid of(int capacity, boolean allowPartialFill, Set<Fluid> whitelist) {
    return new CapabilityProviderFluid(capacity, allowPartialFill, whitelist);
  }

  @Override
  public ICapabilityProvider createProvider(ItemStack itemStack) {
    return allowPartialFill ?
           new WhitelistComplex(itemStack, capacity, whitelist) :
           new Whitelist(itemStack, capacity, whitelist);
  }

  /**
   * Lavishly copied from tfc FluidWhitelistHandler. This Handler just inherits from FluidHandlerItemStack instead of FluidHandlerItemStackSimple, which allows
   * it to be partially full.
   */
  public static class WhitelistComplex extends FluidHandlerItemStack {

    private final Set<Fluid> whitelist;

    public WhitelistComplex(@Nonnull ItemStack container, int capacity, String[] fluidNames) {
      this(container, capacity, Arrays.stream(fluidNames).map(FluidRegistry::getFluid).filter(Objects::nonNull).collect(Collectors.toSet()));
    }

    public WhitelistComplex(@Nonnull ItemStack container, int capacity, Set<Fluid> whitelist) {
      super(container, capacity);
      this.whitelist = whitelist;
    }


    @Override
    public boolean canFillFluidType(FluidStack fluid) {
      return whitelist.contains(fluid.getFluid());
    }


  }

  public static class Whitelist extends FluidHandlerItemStackSimple {

    private final Set<Fluid> whitelist;

    public Whitelist(@Nonnull ItemStack container, int capacity, String[] fluidNames) {
      this(container, capacity, Arrays.stream(fluidNames).map(FluidRegistry::getFluid).filter(Objects::nonNull).collect(Collectors.toSet()));
    }

    public Whitelist(@Nonnull ItemStack container, int capacity, Set<Fluid> whitelist) {
      super(container, capacity);
      this.whitelist = whitelist;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
      return whitelist.contains(fluid.getFluid());
    }

    @Override
    protected void setContainerToEmpty() {
      super.setContainerToEmpty();
      if (container.getTagCompound() != null && container.getTagCompound().isEmpty()) {
        container.setTagCompound(null);
      }
    }

  }

  public static class Sided implements IFluidHandler {

    private final IFluidHandlerSidedCallback callback;
    private final IFluidHandler handler;
    private final EnumFacing side;

    public Sided(IFluidHandlerSidedCallback callback, IFluidHandler handler, EnumFacing side) {
      this.callback = callback;
      this.handler = handler;
      this.side = side;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
      return handler.getTankProperties();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
      if (callback.canFill(resource, side)) {
        return handler.fill(resource, doFill);
      }

      return 0;
    }

    @Override
    @Nullable
    public FluidStack drain(FluidStack resource, boolean doDrain) {
      if (callback.canDrain(side)) {
        return handler.drain(resource, doDrain);
      }

      return null;
    }

    @Override
    @Nullable
    public FluidStack drain(int maxDrain, boolean doDrain) {
      if (callback.canDrain(side)) {
        return handler.drain(maxDrain, doDrain);
      }

      return null;
    }

  }
}
