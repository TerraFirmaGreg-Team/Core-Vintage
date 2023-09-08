package net.dries007.tfc.util.fuel;

import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.LOG;

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

    public static void postInit() {
        for (var type : TreeType.getTreeTypes()) {
            var log = TFCBlocks.getWoodBlock(LOG, type.getWood());
            FUELS.add(new Fuel(IIngredient.of(new ItemStack(log)), type.getWood().getBurnTicks(), type.getWood().getBurnTemp()));
        }

        // Coals
        FUELS.add(new Fuel(IIngredient.of("gemCoal"), 2200, 1415f, true, false));
        FUELS.add(new Fuel(IIngredient.of("gemLignite"), 2000, 1350f, true, false));

        // Charcoal
        FUELS.add(new Fuel(IIngredient.of("charcoal"), 1800, 1350f, true, true));

        // Peat
        FUELS.add(new Fuel(IIngredient.of("peat"), 2500, 680));

        // Stick Bundle
        FUELS.add(new Fuel(IIngredient.of("stickBundle"), 600, 900));
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
