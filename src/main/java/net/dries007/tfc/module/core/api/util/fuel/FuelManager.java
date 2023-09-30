package net.dries007.tfc.module.core.api.util.fuel;

import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class FuelManager {
    private static final List<Fuel> FUELS = new ArrayList<>();
    private static final Fuel EMPTY = new Fuel(IIngredient.empty(), 0, 0);

    @Nonnull
    public static Fuel getFuel(ItemStack stack) {
        return FUELS.stream().filter(x -> x.matchesInput(stack)).findFirst().orElse(EMPTY);
    }

    public static boolean isItemFuel(ItemStack stack) {
        return getFuel(stack) != EMPTY;
    }

    public static boolean isItemForgeFuel(ItemStack stack) {
        Fuel fuel = getFuel(stack);
        return fuel != EMPTY && fuel.isForgeFuel();
    }

    public static boolean isItemBloomeryFuel(ItemStack stack) {
        Fuel fuel = getFuel(stack);
        return fuel != EMPTY && fuel.isBloomeryFuel();
    }

    /**
     * Register a new fuel only if the fuel is unique
     *
     * @param fuel the fuel obj to register
     */
    public static void addFuel(Fuel fuel) {
        if (canRegister(fuel)) {
            FUELS.add(fuel);
        }
    }

    /**
     * Checks if this fuel can be registered
     *
     * @param fuel the fuel obj to register
     * @return true if the new fuel is unique (eg: don't have at least one itemstack that is equal to another already registered fuel)
     */
    public static boolean canRegister(Fuel fuel) {
        return FUELS.stream().noneMatch(x -> x.matchesInput(fuel));
    }
}
