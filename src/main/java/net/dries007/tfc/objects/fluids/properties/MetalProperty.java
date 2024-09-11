package net.dries007.tfc.objects.fluids.properties;

import net.dries007.tfc.api.types.Metal;

import org.jetbrains.annotations.NotNull;

public class MetalProperty {

  public static final FluidProperty<MetalProperty> METAL = new FluidProperty<>("metal");

  private final Metal metal;

  public MetalProperty(@NotNull Metal metal) {
    this.metal = metal;
  }

  @NotNull
  public Metal getMetal() {
    return metal;
  }
}
