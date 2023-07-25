/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.recipes;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableMap;
import gregtech.api.unification.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.util.Alloy;

/**
 * todo: in 1.13+ move this to a json recipe type
 */
public class AlloyRecipe extends IForgeRegistryEntry.Impl<AlloyRecipe>
{
    private final ImmutableMap<Material, DoubleRange> metalMap;
    private final Material result;

    private AlloyRecipe(@Nonnull Material result, ImmutableMap<Material, DoubleRange> alloyMap) {
        this.metalMap = alloyMap;
        this.result = result;

        setRegistryName(result.getUnlocalizedName());
    }

    public Material getResult() {
        return result;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String toString()
    {
        return getRegistryName().getPath();
    }

    public ImmutableMap<Material, DoubleRange> getMetals()
    {
        return metalMap;
    }

    public static class Builder {
        private final Material result;
        private final ImmutableMap.Builder<Material, DoubleRange> builder;

        public Builder(@Nonnull Material result) {
            this.result = result;
            this.builder = new ImmutableMap.Builder<>();
        }

        public Builder add(@Nonnull Material metal, double min, double max) {
            return add(metal, new DoubleRange(min, max));
        }

        public Builder add(@Nonnull Material metal, @Nonnull DoubleRange condition) {
            builder.put(metal, condition);
            return this;
        }

        public AlloyRecipe build() {
            return new AlloyRecipe(result, builder.build());
        }
    }

    /**
     * A range that an alloy must fall within
     * Values are to be interpreted as percentages, and are compared with an accuracy of epsilon
     *
     * @see Alloy#EPSILON
     */
    public static final class DoubleRange {
        private final double min;
        private final double max;

        DoubleRange(double min, double max) {
            this.min = min;
            this.max = max;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }

        public boolean test(double value) {
            return value >= min - Alloy.EPSILON && value <= max + Alloy.EPSILON;
        }
    }
}
