package net.dries007.tfc.api.recipes;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.common.collect.ImmutableMap;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.util.Alloy;

import lombok.Getter;

import javax.annotation.Nonnull;

/**
 * todo: in 1.13+ move this to a json recipe type
 */
public class AlloyRecipe extends IForgeRegistryEntry.Impl<AlloyRecipe> {

  private final ImmutableMap<Metal, AlloyRange> metalMap;
  private final Metal result;

  private AlloyRecipe(@Nonnull Metal result, ImmutableMap<Metal, AlloyRange> alloyMap) {
    this.metalMap = alloyMap;
    this.result = result;

    // This ensures that no metal result has more than one alloy recipe
    // Required so that we can search for alloys by result registry name
    //noinspection ConstantConditions
    setRegistryName(result.getRegistryName());
  }

  public Metal getResult() {
    return result;
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

    public Builder(@Nonnull Metal result) {
      this.result = result;
      this.builder = new ImmutableMap.Builder<>();
    }

    public Builder(@Nonnull ResourceLocation loc) {
      this.result = TFCRegistries.METALS.getValue(loc);
      if (result == null) {throw new IllegalArgumentException("Result metal is not allowed to be null. Missing metal for key: " + loc.toString());}
      this.builder = new ImmutableMap.Builder<>();
    }

    public Builder add(@Nonnull ResourceLocation loc, double min, double max) {
      return add(loc, new AlloyRange(min, max));
    }

    public Builder add(@Nonnull ResourceLocation loc, @Nonnull AlloyRange condition) {
      Metal metal = TFCRegistries.METALS.getValue(loc);
      if (metal == null) {throw new IllegalArgumentException("Result metal is not allowed to be null. Missing metal for key: " + loc.toString());}
      return add(metal, condition);
    }

    public Builder add(@Nonnull Metal metal, double min, double max) {
      return add(metal, new AlloyRange(min, max));
    }

    public Builder add(@Nonnull Metal metal, @Nonnull AlloyRange condition) {
      builder.put(metal, condition);
      return this;
    }

    public AlloyRecipe build() {
      return new AlloyRecipe(result, builder.build());
    }
  }

  /**
   * A range that an alloy must fall within Values are to be interpreted as percentages, and are compared with an accuracy of epsilon
   *
   * @see Alloy#EPSILON
   */
  @Getter
  public static final class AlloyRange {

    private final double min;
    private final double max;

    AlloyRange(double min, double max) {
      this.min = min;
      this.max = max;
    }

    public boolean test(double value) {
      return value >= min - Alloy.EPSILON && value <= max + Alloy.EPSILON;
    }
  }
}
