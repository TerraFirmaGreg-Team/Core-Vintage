package net.dries007.tfc.api.recipes;

import su.terrafirmagreg.data.lib.DoubleRange;

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

  private final ImmutableMap<Metal, DoubleRange> metalMap;
  @Getter
  private final Metal result;

  private AlloyRecipe(@NotNull Metal result, ImmutableMap<Metal, DoubleRange> alloyMap) {
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

  public ImmutableMap<Metal, DoubleRange> getMetals() {
    return metalMap;
  }

  public static class Builder {

    private final Metal result;
    private final ImmutableMap.Builder<Metal, DoubleRange> builder;

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
      return add(loc, new DoubleRange(min, max));
    }

    public Builder add(@NotNull ResourceLocation loc, @NotNull DoubleRange condition) {
      Metal metal = TFCRegistries.METALS.getValue(loc);
      if (metal == null) {
        throw new IllegalArgumentException("Result metal is not allowed to be null. Missing metal for key: " + loc);
      }
      return add(metal, condition);
    }

    public Builder add(@NotNull Metal metal, @NotNull DoubleRange condition) {
      builder.put(metal, condition);
      return this;
    }

    public Builder add(@NotNull Metal metal, double min, double max) {
      return add(metal, new DoubleRange(min, max));
    }

    public AlloyRecipe build() {
      return new AlloyRecipe(result, builder.build());
    }
  }

}
