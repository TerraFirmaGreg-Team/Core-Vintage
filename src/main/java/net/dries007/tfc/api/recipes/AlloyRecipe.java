package net.dries007.tfc.api.recipes;

import net.dries007.tfc.util.Alloy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.common.collect.ImmutableMap;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

/**
 * todo: in 1.13+ move this to a json recipe type
 */
public class AlloyRecipe extends IForgeRegistryEntry.Impl<AlloyRecipe> {

  private final ImmutableMap<Metal, AlloyRange> metalMap;
  @Getter
  private final Metal result;

  private AlloyRecipe(@NotNull Metal result, ImmutableMap<Metal, AlloyRange> alloyMap) {
    this.metalMap = alloyMap;
    this.result = result;

    // This ensures that no metal result has more than one alloy recipe
    // Required so that we can search for alloys by result registry name
    //noinspection ConstantConditions
    setRegistryName(result.getRegistryName());
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public String toString() {
    return getRegistryName().getPath();
  }

  public ImmutableMap<Metal, AlloyRange> getMetals() {
    return metalMap;
  }

  public static class Builder {

    private final Metal result;
    private final ImmutableMap.Builder<Metal, AlloyRange> builder;

    public Builder(@NotNull Metal result) {
      this.result = result;
      this.builder = new ImmutableMap.Builder<>();
    }

    public Builder(@NotNull ResourceLocation loc) {
      this.result = TFCRegistries.METALS.getValue(loc);
      if (result == null) {
        throw new IllegalArgumentException("Result metal is not allowed to be null. Missing metal for key: " + loc);
      }
      this.builder = new ImmutableMap.Builder<>();
    }

    public Builder add(@NotNull ResourceLocation loc, double min, double max) {
      return add(loc, new AlloyRange(min, max));
    }

    public Builder add(@NotNull ResourceLocation loc, @NotNull AlloyRange condition) {
      Metal metal = TFCRegistries.METALS.getValue(loc);
      if (metal == null) {
        throw new IllegalArgumentException("Result metal is not allowed to be null. Missing metal for key: " + loc);
      }
      return add(metal, condition);
    }

    public Builder add(@NotNull Metal metal, @NotNull AlloyRange condition) {
      builder.put(metal, condition);
      return this;
    }

    public Builder add(@NotNull Metal metal, double min, double max) {
      return add(metal, new AlloyRange(min, max));
    }

    public AlloyRecipe build() {
      return new AlloyRecipe(result, builder.build());
    }
  }

  @Getter
  public static class AlloyRange {

    private final double min;
    private final double max;

    public AlloyRange(double min, double max) {
      this.min = min;
      this.max = max;
    }

    public boolean test(double value) {
      return value >= min - Alloy.EPSILON && value <= max + Alloy.EPSILON;
    }
  }
}
