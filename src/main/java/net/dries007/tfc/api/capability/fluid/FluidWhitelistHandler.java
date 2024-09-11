package net.dries007.tfc.api.capability.fluid;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class FluidWhitelistHandler extends FluidHandlerItemStackSimple {

  private final Set<Fluid> whitelist;

  public FluidWhitelistHandler(@NotNull ItemStack container, int capacity, String[] fluidNames) {
    this(container, capacity, Arrays.stream(fluidNames)
            .map(FluidRegistry::getFluid)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet()));
  }

  public FluidWhitelistHandler(@NotNull ItemStack container, int capacity, Set<Fluid> whitelist) {
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
