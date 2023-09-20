package net.dries007.tfc.api.recipes;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.module.core.common.objects.inventory.ingredient.IIngredient;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChiselRecipe extends IForgeRegistryEntry.Impl<ChiselRecipe> {
    private final IIngredient<IBlockState> ingredient;
    private final IBlockState stateOut;

    public ChiselRecipe(Block blockIn, IBlockState stateOut) {
        this(state -> state.getBlock() == blockIn, stateOut);
    }

    public ChiselRecipe(IIngredient<IBlockState> ingredient, IBlockState stateOut) {
        this.ingredient = ingredient;
        this.stateOut = stateOut;
    }

    @Nullable
    public static ChiselRecipe get(IBlockState state) {
        return TFCRegistries.CHISEL.getValuesCollection().stream().filter(r -> r.matches(state)).findFirst().orElse(null);
    }

    public IBlockState getOutputState() {
        return stateOut;
    }

    public boolean matches(IBlockState stateIn) {
        return ingredient.test(stateIn);
    }

    public enum Mode {
        SMOOTH,
        STAIR,
        SLAB;

        private static final Mode[] VALUES = values();

        @Nonnull
        public static Mode valueOf(int i) {
            return i >= 0 && i < VALUES.length ? VALUES[i] : SMOOTH;
        }

        @Nonnull
        public Mode next() {
            return VALUES[(ordinal() + 1) % VALUES.length];
        }
    }
}
